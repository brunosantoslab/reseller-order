package app.brunosantos.orderreceiving.dto;

public record OrderItemDTO(
    String productId,
    int quantity
) {}