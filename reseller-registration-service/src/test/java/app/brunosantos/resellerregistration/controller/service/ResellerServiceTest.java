package app.brunosantos.resellerregistration.controller.service;

import app.brunosantos.resellerregistration.exception.ResourceNotFoundException;
import app.brunosantos.resellerregistration.model.Address;
import app.brunosantos.resellerregistration.model.Reseller;
import app.brunosantos.resellerregistration.repository.ResellerRepository;
import app.brunosantos.resellerregistration.service.ResellerService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResellerServiceTest {

    @Mock
    private ResellerRepository repository;

    @InjectMocks
    private ResellerService service;

    @Test
    void shouldSaveResellerWithAddresses() {
        Reseller reseller = new Reseller();
        reseller.setDeliveryAddresses(List.of(new Address()));

        when(repository.save(any(Reseller.class))).thenReturn(reseller);

        Reseller saved = service.registerReseller(reseller);
        assertNotNull(saved);
        assertEquals(1, saved.getDeliveryAddresses().size());
        verify(repository, times(1)).save(reseller);
    }

    @Test
    void shouldUpdateExistingReseller() {
        Reseller existing = new Reseller();
        existing.setId(1L);
        Reseller updated = new Reseller();
        updated.setCorporateName("New Name");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Reseller result = service.updateReseller(1L, updated);
        assertEquals("New Name", result.getCorporateName());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingReseller() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            service.updateReseller(999L, new Reseller()));
    }
}