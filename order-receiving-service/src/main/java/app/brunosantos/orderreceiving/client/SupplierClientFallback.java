package app.brunosantos.orderreceiving.client;

import app.brunosantos.orderreceiving.dto.SupplierOrderCreateDTO;
import app.brunosantos.orderreceiving.dto.SupplierOrderDTO;
import app.brunosantos.orderreceiving.dto.SupplierOrderItemDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupplierClientFallback implements SupplierClient {
    @Override
    public SupplierOrderDTO createOrder(SupplierOrderCreateDTO request) {
        String supplierOrderId = UUID.randomUUID().toString();

        List<SupplierOrderItemDTO> items = request.items().stream()
            .map(item -> new SupplierOrderItemDTO(item.productId(), item.quantity()))
            .collect(Collectors.toList());

        return new SupplierOrderDTO(
            supplierOrderId,
            LocalDateTime.now(),
            items
        );
    }
}