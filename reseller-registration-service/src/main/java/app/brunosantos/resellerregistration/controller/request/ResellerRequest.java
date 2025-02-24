package app.brunosantos.resellerregistration.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for creating or updating a reseller.
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */
@Setter
@Getter
public class ResellerRequest {

    @NotBlank(message = "CNPJ is required")
    private String cnpj;

    @NotBlank(message = "Corporate Name is required")
    private String corporateName;

    @NotBlank(message = "Trade Name is required")
    private String tradeName;

    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Phone numbers are required")
    private List<@Size(max = 15, message = "Phone number can't exceed 15 characters") String> phoneNumbers;

    @NotEmpty(message = "Contact names are required")
    private List<@NotBlank(message = "Contact name can't be blank") String> contactNames;

    @NotEmpty(message = "Delivery addresses are required")
    private List<@NotBlank(message = "Delivery address can't be blank") String> deliveryAddresses;
}