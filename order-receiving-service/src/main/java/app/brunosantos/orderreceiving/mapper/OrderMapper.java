package app.brunosantos.orderreceiving.mapper;

import app.brunosantos.orderreceiving.dto.OrderCreateDTO;
import app.brunosantos.orderreceiving.dto.OrderDTO;
import app.brunosantos.orderreceiving.dto.OrderItemDTO;
import app.brunosantos.orderreceiving.model.Order;
import app.brunosantos.orderreceiving.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = java.time.LocalDateTime.class)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "items", source = "items")
    Order toEntity(OrderCreateDTO dto);

    @Mapping(target = "orderId", source = "id")
    OrderDTO toDTO(Order entity);

    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem(OrderItemDTO dto);

    List<OrderItem> toOrderItems(List<OrderItemDTO> dtos);
}