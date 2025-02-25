package app.brunosantos.orderreceiving.dto;

import app.brunosantos.orderreceiving.validation.CnpjValid;
import app.brunosantos.orderreceiving.validation.PhoneNumberValid;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * A DTO for the reseller entity.
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */
public record ResellerDTO(

    Long id,

    @NotBlank(message = "CNPJ is required")
    @CnpjValid(message = "Invalid CNPJ")
    String cnpj,
    
    @NotBlank(message = "Corporate Name is required")
    @Size(max = 100)
    String corporateName,
    
    @NotBlank(message = "Trade Name is required")
    @Size(max = 50)
    String tradeName,
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email,
    
    @NotEmpty(message = "At least one contact name is required")
    List<@NotBlank String> contactNames,
    
    @Size(min = 1, message = "At least one phone number is required")
    List<@NotBlank
    @PhoneNumberValid(message = "Invalid phone number")
        String> phoneNumbers,
    
    @NotEmpty(message = "At least one delivery address is required")
    List<AddressDTO> deliveryAddresses
) {}