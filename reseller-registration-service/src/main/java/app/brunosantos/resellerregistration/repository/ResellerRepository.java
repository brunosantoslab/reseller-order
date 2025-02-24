package app.brunosantos.resellerregistration.repository;

import app.brunosantos.resellerregistration.model.Reseller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on resellers.
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */
@Repository
public interface ResellerRepository extends JpaRepository<Reseller, Long> {
    Reseller findByCnpj(String cnpj);
}
