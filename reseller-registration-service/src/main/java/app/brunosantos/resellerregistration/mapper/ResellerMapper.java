package app.brunosantos.resellerregistration.mapper;

import app.brunosantos.resellerregistration.controller.request.ResellerRequest;
import app.brunosantos.resellerregistration.model.Reseller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between ResellerRequest DTO and Reseller entity.
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */

 @Mapper(componentModel = "spring")
public interface ResellerMapper {

    // Instance of the Mapper
    ResellerMapper INSTANCE = Mappers.getMapper(ResellerMapper.class);

    // Define the mapping between ResellerRequest and Reseller
    @Mapping(source = "cnpj", target = "cnpj")
    @Mapping(source = "corporateName", target = "corporateName")
    @Mapping(source = "tradeName", target = "tradeName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "contactNames", target = "contactNames")
    @Mapping(source = "phoneNumbers", target = "phoneNumbers")
    @Mapping(source = "deliveryAddresses", target = "deliveryAddresses")
    Reseller toReseller(ResellerRequest resellerRequest);
}
