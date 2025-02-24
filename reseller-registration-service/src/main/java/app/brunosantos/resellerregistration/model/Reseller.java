package app.brunosantos.resellerregistration.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
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

    @Column(unique = true, nullable = false)
    private String cnpj;

    @Column(nullable = false, length = 100)
    private String corporateName;

    @Column(nullable = false, length = 50)
    private String tradeName;

    @Column(nullable = false)
    private String email;

    @ElementCollection
    @CollectionTable(name = "reseller_contacts", joinColumns = @JoinColumn(name = "reseller_id"))
    private List<String> contactNames = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "reseller_phones", joinColumns = @JoinColumn(name = "reseller_id"))
    private List<String> phoneNumbers = new ArrayList<>();

    @OneToMany(
        mappedBy = "reseller",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Address> deliveryAddresses = new ArrayList<>();

    public Reseller() {}

    public Reseller(String cnpj, String corporateName, String tradeName) {
        this.cnpj = cnpj;
        this.corporateName = corporateName;
        this.tradeName = tradeName;
    }

    public void addAddress(Address address) {
        deliveryAddresses.add(address);
        address.setReseller(this);
    }

    public void removeAddress(Address address) {
        deliveryAddresses.remove(address);
        address.setReseller(null);
    }
}