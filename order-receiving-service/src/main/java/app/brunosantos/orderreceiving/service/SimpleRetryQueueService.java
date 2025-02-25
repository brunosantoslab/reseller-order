package app.brunosantos.orderreceiving.service;

import app.brunosantos.orderreceiving.client.SupplierClient;
import app.brunosantos.orderreceiving.dto.OrderCreateDTO;
import app.brunosantos.orderreceiving.dto.SupplierOrderCreateDTO;
import app.brunosantos.orderreceiving.dto.SupplierOrderDTO;
import app.brunosantos.orderreceiving.dto.SupplierOrderItemDTO;
import app.brunosantos.orderreceiving.mapper.OrderMapper;
import app.brunosantos.orderreceiving.model.Order;
import app.brunosantos.orderreceiving.model.OrderStatus;
import app.brunosantos.orderreceiving.repository.OrderRepository;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleRetryQueueService implements RetryQueueService {
    
    private final OrderRepository orderRepository;
    private final SupplierClient supplierClient;
    private final Queue<UUID> retryQueue = new ConcurrentLinkedQueue<>();
    private final OrderMapper orderMapper;

    @Autowired
    public SimpleRetryQueueService(OrderMapper orderMapper,
        OrderRepository orderRepository,
        SupplierClient supplierClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.supplierClient = supplierClient;
    }

    @Scheduled(fixedDelay = 30000)
    public void processRetries() {
        while (!retryQueue.isEmpty()) {
            UUID orderId = retryQueue.poll();
            orderRepository.findById(orderId).ifPresent(order -> {
                try {
                    SupplierOrderDTO response = supplierClient.createOrder(convertOrder(order));
                    order.setStatus(OrderStatus.PROCESSED);
                    order.setSupplierOrderId(response.supplierOrderId());
                    orderRepository.save(order);
                } catch (Exception e) {
                    order.setStatus(OrderStatus.RETRY_PENDING);
                    orderRepository.save(order);
                    retryQueue.add(orderId);
                    log.error("Retry failed for order {}", orderId, e);
                }
            });
        }
    }

    private SupplierOrderCreateDTO convertOrder(Order order) {
        return new SupplierOrderCreateDTO(
            order.getResellerCnpj(),
            order.getItems().stream()
                .map(item -> new SupplierOrderItemDTO(
                    item.getProductId(),
                    item.getQuantity()))
                .toList()
        );
    }
    
    @Override
    public void enqueue(UUID orderId) {
        retryQueue.add(orderId);
    }
}