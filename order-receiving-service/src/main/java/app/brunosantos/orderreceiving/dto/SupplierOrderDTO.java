package app.brunosantos.orderreceiving.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SupplierOrderDTO(
    String supplierOrderId,
    LocalDateTime processedAt,
    List<SupplierOrderItemDTO> items
) {}