package app.brunosantos.resellerregistration.repository;

import app.brunosantos.resellerregistration.model.Reseller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class ResellerRepositoryTest {

    @Autowired
    private ResellerRepository resellerRepository;

    @Test
    void testSaveReseller() {
        Reseller reseller = new Reseller();
        reseller.setCnpj("12345678901234");
        reseller.setCorporateName("Corp Test");
        reseller.setTradeName("Trade Test");
        reseller.setEmail("test@company.com");

        Reseller savedReseller = resellerRepository.save(reseller);
        assertNotNull(savedReseller.getId());
    }

    @Test
    void testFindResellerByCnpj() {
        Reseller reseller = new Reseller();
        reseller.setCnpj("12345678901234");
        reseller.setCorporateName("Corp Test");
        reseller.setTradeName("Trade Test");
        reseller.setEmail("test@company.com");

        resellerRepository.save(reseller);

        Reseller foundReseller = resellerRepository.findByCnpj("12345678901234");
        assertNotNull(foundReseller);
        assertEquals("12345678901234", foundReseller.getCnpj());
    }

    @Test
    void testFindResellerById() {
        Reseller reseller = new Reseller();
        reseller.setCnpj("12345678901234");
        reseller.setCorporateName("Corp Test");
        reseller.setTradeName("Trade Test");
        reseller.setEmail("test@company.com");

        Reseller savedReseller = resellerRepository.save(reseller);

        Reseller foundReseller = resellerRepository.findById(savedReseller.getId()).orElse(null);
        assertNotNull(foundReseller);
        assertEquals(savedReseller.getId(), foundReseller.getId());
    }
}
