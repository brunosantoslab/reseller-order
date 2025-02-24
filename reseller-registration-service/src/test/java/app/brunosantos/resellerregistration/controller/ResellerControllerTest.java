package app.brunosantos.resellerregistration.controller;

import app.brunosantos.resellerregistration.dto.AddressDTO;
import app.brunosantos.resellerregistration.dto.ResellerDTO;
import app.brunosantos.resellerregistration.mapper.ResellerMapper;
import app.brunosantos.resellerregistration.model.Reseller;
import app.brunosantos.resellerregistration.service.ResellerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResellerController.class)
class ResellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResellerService resellerService;

    @MockBean
    private ResellerMapper resellerMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateReseller() throws Exception {
        ResellerDTO dto = validResellerDTO();
        Reseller reseller = new Reseller();
        reseller.setId(1L);

        Mockito.when(resellerMapper.toEntity(any())).thenReturn(reseller);
        Mockito.when(resellerService.registerReseller(any())).thenReturn(reseller);

        mockMvc.perform(post("/api/resellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void shouldReturnBadRequestForInvalidData() throws Exception {
        ResellerDTO invalidDto = new ResellerDTO(
            0L,
            "invalid-cnpj",
            "",
            "",
            "invalid-email",
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList()
        );

        mockMvc.perform(post("/api/resellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasSize(greaterThan(0))));
    }

    @Test
    void shouldGetResellerById() throws Exception {
        Reseller reseller = new Reseller();
        reseller.setId(1L);
        
        Mockito.when(resellerService.getResellerById(1L)).thenReturn(Optional.of(reseller));

        mockMvc.perform(get("/api/resellers/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void shouldReturnNotFoundForInvalidId() throws Exception {
        Mockito.when(resellerService.getResellerById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/resellers/999"))
            .andExpect(status().isNotFound());
    }

    private ResellerDTO validResellerDTO() {
        return new ResellerDTO(
            0L,
            "90.158.914/0001-78",
            "Corporate Name LTDA",
            "Trade Name",
            "valid@email.com",
            List.of("Contact Name"),
            List.of("(11) 99999-9999"),
            List.of(new AddressDTO(
                0L,
                "Street",
                "123",
                "City",
                "SP",
                "12345-678",
                "Complement"
            ))
        );
    }
}