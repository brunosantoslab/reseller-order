package app.brunosantos.resellerregistration.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


/**
 * Represents a reseller entity in the system.
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */

@Setter
@Getter
@Entity
public class Reseller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^\\d{14}$", message = "CNPJ must be 14 digits")
    private String cnpj;

    @NotBlank
    private String corporateName;

    @NotBlank
    private String tradeName;

    @NotBlank
    @Email
    private String email;

    @ElementCollection
    private List<String> contactNames;

    @ElementCollection
    private List<String> phoneNumbers;

    @ElementCollection
    private List<String> deliveryAddresses;
}
