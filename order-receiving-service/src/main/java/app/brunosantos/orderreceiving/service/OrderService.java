package app.brunosantos.orderreceiving.service;

import app.brunosantos.orderreceiving.client.ResellerClient;
import app.brunosantos.orderreceiving.client.SupplierClient;
import app.brunosantos.orderreceiving.dto.OrderCreateDTO;
import app.brunosantos.orderreceiving.dto.OrderDTO;
import app.brunosantos.orderreceiving.dto.OrderItemCreateDTO;
import app.brunosantos.orderreceiving.dto.ResellerDTO;
import app.brunosantos.orderreceiving.dto.SupplierOrderCreateDTO;
import app.brunosantos.orderreceiving.dto.SupplierOrderDTO;
import app.brunosantos.orderreceiving.dto.SupplierOrderItemDTO;
import app.brunosantos.orderreceiving.exception.ResellerNotFoundException;
import app.brunosantos.orderreceiving.mapper.OrderMapper;
import app.brunosantos.orderreceiving.model.Order;
import app.brunosantos.orderreceiving.model.OrderStatus;
import app.brunosantos.orderreceiving.repository.OrderRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final ResellerClient resellerClient;
    private final SupplierClient supplierClient;
    private final RetryQueueService retryQueueService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    @Transactional
    public OrderDTO createOrder(OrderCreateDTO request) {
        validateReseller(request.getResellerCnpj());
        log.info("Reseller validated {}", request.getResellerCnpj());
        validateMinimumOrderQuantity(request.getItems());

        Order order = orderMapper.toEntity(request);
        order.setStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);
        log.info("Order saved {}", savedOrder.getId());

        try {
            SupplierOrderDTO response = supplierClient.createOrder(convertToSupplierOrder(savedOrder));
            log.info("Order sent to the supplier {}", savedOrder.getId());
            savedOrder.setStatus(OrderStatus.PROCESSED);
            savedOrder.setSupplierOrderId(response.supplierOrderId());
            orderRepository.save(savedOrder);
        } catch (Exception e) {
            log.error("Failed to process order {}", savedOrder.getId(), e);
            savedOrder.setStatus(OrderStatus.RETRY_PENDING);
            orderRepository.save(savedOrder);
            retryQueueService.enqueue(savedOrder.getId());
        }

        return orderMapper.toDTO(savedOrder);
    }

    private void validateReseller(String cnpj) {
        ResponseEntity<ResellerDTO> response = resellerClient.findResellerByCnpj(cnpj);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ResellerNotFoundException("Reseller not found with CNPJ: " + cnpj);
        }
    }

    private void validateMinimumOrderQuantity(List<OrderItemCreateDTO> items) {
        int total = items.stream().mapToInt(OrderItemCreateDTO::getQuantity).sum();
        if (total < 1000) {
            throw new IllegalArgumentException("Minimum order quantity is 1000 units");
        }
    }

    private SupplierOrderCreateDTO convertToSupplierOrder(Order order) {
        return new SupplierOrderCreateDTO(
            order.getResellerCnpj(),
            order.getItems().stream()
                .map(item -> new SupplierOrderItemDTO(item.getProductId(), item.getQuantity()))
                .toList()
        );
    }


}