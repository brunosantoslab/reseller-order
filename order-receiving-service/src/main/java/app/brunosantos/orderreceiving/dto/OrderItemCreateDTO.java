package app.brunosantos.orderreceiving.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCreateDTO {
    @NotBlank
    private Long productId;

    @Min(1)
    private int quantity;
}