package app.brunosantos.orderreceiving.dto;

import app.brunosantos.orderreceiving.validation.CEP;
import jakarta.validation.constraints.*;

/**
 * A DTO for the address entity.
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */
public record AddressDTO(

    Long id,

    @NotBlank(message = "Street is required")
    @Size(max = 100, message = "Street must be up to 100 characters")
    String street,

    @NotBlank(message = "Number is required")
    @Size(max = 10, message = "Number must be up to 10 characters")
    String number,

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City must be up to 50 characters")
    String city,

    @NotBlank(message = "State is required")
    @Size(min = 2, max = 2, message = "State must be 2 characters")
    @Pattern(regexp = "AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO",
        message = "Invalid state code")
    String state,

    @NotBlank(message = "CEP is required")
    @CEP(message = "Invalid CEP format")
    String postalCode,

    @Size(max = 100, message = "Complement must be up to 100 characters")
    String complement
) {}