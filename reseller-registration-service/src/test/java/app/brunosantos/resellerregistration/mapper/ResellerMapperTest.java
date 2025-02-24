package app.brunosantos.resellerregistration.mapper;

import app.brunosantos.resellerregistration.dto.AddressDTO;
import app.brunosantos.resellerregistration.dto.ResellerDTO;
import app.brunosantos.resellerregistration.model.Address;
import app.brunosantos.resellerregistration.model.Reseller;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResellerMapperTest {

    private final ResellerMapper mapper = Mappers.getMapper(ResellerMapper.class);

    @Test
    void shouldMapResellerDTOToEntity() {
        ResellerDTO dto = new ResellerDTO(
            0L,
            "33.222.111/0001-80",
            "Corporate",
            "Trade",
            "email@test.com",
            List.of("Contact"),
            List.of("(11) 99999-9999"),
            List.of(new AddressDTO(
                0L, "Street", "123", "City", "SP", "12345-678", "Comp"
            ))
        );

        Reseller entity = mapper.toEntity(dto);

        assertAll(
            () -> assertEquals(dto.cnpj(), entity.getCnpj()),
            () -> assertEquals(1, entity.getDeliveryAddresses().size()),
            () -> assertEquals("Street", entity.getDeliveryAddresses().get(0).getStreet())
        );
    }

    @Test
    void shouldMapAddressDTOToEntity() {
        AddressDTO dto = new AddressDTO(
            0L,"Street", "123", "City", "SP", "12345-678", "Comp"
        );

        Address entity = mapper.toAddressEntity(dto);

        assertAll(
            () -> assertEquals(dto.street(), entity.getStreet()),
            () -> assertEquals(dto.postalCode(), entity.getPostalCode())
        );
    }
}