package app.brunosantos.orderreceiving.client;

import app.brunosantos.orderreceiving.dto.ResellerDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resellerClient", url = "${reseller.service.url}")
@CircuitBreaker(name = "resellerClient")
@Retry(name = "resellerClient")
public interface ResellerClient {
    @GetMapping("/resellers/{cnpj}")
    ResponseEntity<ResellerDTO> findResellerByCnpj(@PathVariable String cnpj);
}