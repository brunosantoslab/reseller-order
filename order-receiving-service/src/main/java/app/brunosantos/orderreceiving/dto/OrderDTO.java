package app.brunosantos.orderreceiving.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String orderId;
    private String resellerCnpj;
    private String customerIdentification;
    private String status;
    private String supplierOrderId;
    private List<OrderItemDTO> items;
}