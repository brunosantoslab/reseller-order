package app.brunosantos.resellerregistration.controller;

import app.brunosantos.resellerregistration.dto.ResellerDTO;
import app.brunosantos.resellerregistration.model.Reseller;
import app.brunosantos.resellerregistration.mapper.ResellerMapper;
import app.brunosantos.resellerregistration.service.ResellerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for handling HTTP requests related to resellers.
 *
 * @author Bruno da Silva Santos
 * @version 2025.02.24
 * @email contact@brunosantos.app
 * @since 2025-02-24
 */
@RestController
@RequestMapping("/api/resellers")
public class ResellerController {

    private final ResellerService resellerService;
    private final ResellerMapper resellerMapper;

    public ResellerController(ResellerService resellerService, ResellerMapper resellerMapper) {
        this.resellerService = resellerService;
        this.resellerMapper = resellerMapper;
    }

    @PostMapping
    public ResponseEntity<Reseller> createReseller(@Valid @RequestBody ResellerDTO resellerDTO) {
        Reseller reseller = resellerMapper.toEntity(resellerDTO);
        return new ResponseEntity<>(resellerService.registerReseller(reseller), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reseller> updateReseller(
        @PathVariable Long id,
        @Valid @RequestBody ResellerDTO resellerDTO
    ) {
        Reseller reseller = resellerMapper.toEntity(resellerDTO);
        return ResponseEntity.ok(resellerService.updateReseller(id, reseller));
    }

    @GetMapping
    public ResponseEntity<List<Reseller>> getAllResellers() {
        return ResponseEntity.ok(resellerService.getAllResellers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reseller> getResellerById(@PathVariable Long id) {
        Optional<Reseller> reseller = resellerService.getResellerById(id);
        return reseller.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReseller(@PathVariable Long id) {
        resellerService.deleteReseller(id);
        return ResponseEntity.noContent().build();
    }
}