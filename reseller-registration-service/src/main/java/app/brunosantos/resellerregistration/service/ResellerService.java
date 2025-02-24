package app.brunosantos.resellerregistration.service;

import app.brunosantos.resellerregistration.model.Reseller;
import app.brunosantos.resellerregistration.repository.ResellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
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
public class ResellerService {

    private final ResellerRepository resellerRepository;

    @Autowired
    public ResellerService(ResellerRepository resellerRepository) {
        this.resellerRepository = resellerRepository;
    }

    public Reseller registerReseller(@Valid Reseller reseller) {
        return resellerRepository.save(reseller);
    }

    public List<Reseller> getAllResellers() {
        return resellerRepository.findAll();
    }

    public Optional<Reseller> getResellerById(Long id) {
        return resellerRepository.findById(id);
    }

    public Reseller updateReseller(Long id, @Valid Reseller reseller) {
        if (!resellerRepository.existsById(id)) {
            throw new RuntimeException("Reseller not found");
        }
        reseller.setId(id);
        return resellerRepository.save(reseller);
    }

    public void deleteReseller(Long id) {
        resellerRepository.deleteById(id);
    }
}

