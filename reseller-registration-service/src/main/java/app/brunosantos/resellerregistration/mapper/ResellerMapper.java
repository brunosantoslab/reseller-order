package app.brunosantos.resellerregistration.mapper;

import app.brunosantos.resellerregistration.dto.AddressDTO;
import app.brunosantos.resellerregistration.dto.ResellerDTO;
import app.brunosantos.resellerregistration.model.Address;
import app.brunosantos.resellerregistration.model.Reseller;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * A mapper for reseller and address DTOs/ Entities
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */
@Mapper(componentModel = "spring")
public interface ResellerMapper {

    @Mapping(target = "deliveryAddresses", source = "deliveryAddresses", qualifiedByName = "mapAddresses")
    Reseller toEntity(ResellerDTO dto);

    @Named("mapAddresses")
    default List<Address> mapAddresses(List<AddressDTO> dtos) {
        return dtos.stream()
            .map(this::toAddressEntity)
            .toList();
    }

    @Mapping(target = "reseller", ignore = true)
    Address toAddressEntity(AddressDTO dto);
}