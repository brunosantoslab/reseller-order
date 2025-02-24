package app.brunosantos.resellerregistration.service;

import app.brunosantos.resellerregistration.exception.ResourceNotFoundException;
import app.brunosantos.resellerregistration.model.Reseller;
import app.brunosantos.resellerregistration.repository.ResellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling business logic related to resellers.
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */
@Service
@Transactional
public class ResellerService {

    private final ResellerRepository resellerRepository;

    public ResellerService(ResellerRepository resellerRepository) {
        this.resellerRepository = resellerRepository;
    }

    public Reseller registerReseller(Reseller reseller) {

        if (reseller.getDeliveryAddresses() != null) {
            reseller.getDeliveryAddresses().forEach(address -> address.setReseller(reseller));
        }
        return resellerRepository.save(reseller);
    }

    public List<Reseller> getAllResellers() {
        return resellerRepository.findAll();
    }

    public Optional<Reseller> getResellerById(Long id) {
        return resellerRepository.findById(id);
    }

    public Reseller updateReseller(Long id, Reseller reseller) {
        return resellerRepository.findById(id)
            .map(existing -> {
                updateEntity(existing, reseller);
                return resellerRepository.save(existing);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Reseller not found with id: " + id));
    }

    private void updateEntity(Reseller existing, Reseller updated) {
        existing.setCnpj(updated.getCnpj());
        existing.setCorporateName(updated.getCorporateName());
        existing.setTradeName(updated.getTradeName());
        existing.setEmail(updated.getEmail());
        existing.setContactNames(updated.getContactNames());
        existing.setPhoneNumbers(updated.getPhoneNumbers());

        // Atualização segura dos endereços
        existing.getDeliveryAddresses().clear();
        if (updated.getDeliveryAddresses() != null) {
            updated.getDeliveryAddresses().forEach(address -> {
                address.setReseller(existing);
                existing.getDeliveryAddresses().add(address);
            });
        }
    }

    public void deleteReseller(Long id) {
        resellerRepository.deleteById(id);
    }
}

