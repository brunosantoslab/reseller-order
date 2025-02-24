package app.brunosantos.resellerregistration.controller;

import app.brunosantos.resellerregistration.model.Reseller;
import app.brunosantos.resellerregistration.controller.request.ResellerRequest;
import app.brunosantos.resellerregistration.mapper.ResellerMapper;
import app.brunosantos.resellerregistration.service.ResellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ResellerController(ResellerService resellerService, ResellerMapper resellerMapper) {
        this.resellerService = resellerService;
        this.resellerMapper = resellerMapper;
    }

    @PostMapping
    public ResponseEntity<Reseller> registerReseller(@Valid @RequestBody ResellerRequest resellerRequest) {
        Reseller reseller = resellerMapper.toReseller(resellerRequest);
        Reseller createdReseller = resellerService.registerReseller(reseller);
        return new ResponseEntity<>(createdReseller, HttpStatus.CREATED);
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

    @PutMapping("/{id}")
    public ResponseEntity<Reseller> updateReseller(@PathVariable Long id, @Valid @RequestBody ResellerRequest resellerRequest) {
        Reseller reseller = resellerMapper.toReseller(resellerRequest);
        Reseller updatedReseller = resellerService.updateReseller(id, reseller);
        return ResponseEntity.ok(updatedReseller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReseller(@PathVariable Long id) {
        resellerService.deleteReseller(id);
        return ResponseEntity.noContent().build();
    }
}