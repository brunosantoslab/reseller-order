package app.brunosantos.orderreceiving.client;

import app.brunosantos.orderreceiving.dto.SupplierOrderCreateDTO;
import app.brunosantos.orderreceiving.dto.SupplierOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
    name = "supplier",
    url = "${supplier.api.url}",
    fallback = SupplierClientFallback.class
)
public interface SupplierClient {
    
    @PostMapping("/orders")
    SupplierOrderDTO createOrder(SupplierOrderCreateDTO request);
}