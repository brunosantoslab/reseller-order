package app.brunosantos.orderreceiving.dto;


public record SupplierOrderItemDTO(
    Long productId,
    int quantity
) {}