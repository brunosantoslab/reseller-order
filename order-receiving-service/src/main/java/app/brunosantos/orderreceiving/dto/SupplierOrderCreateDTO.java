package app.brunosantos.orderreceiving.dto;

import java.util.List;

public record SupplierOrderCreateDTO(
    String resellerCnpj,
    List<SupplierOrderItemDTO> items
) {

    public SupplierOrderCreateDTO(String resellerCnpj, List<SupplierOrderItemDTO> items) {
        this.resellerCnpj = resellerCnpj;
        this.items = items;
    }
}