package app.brunosantos.orderreceiving.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {
    @NotBlank
    private String resellerCnpj;

    @NotBlank
    private String customerIdentification;

    @NotNull
    @Valid
    private List<OrderItemCreateDTO> items;
}



