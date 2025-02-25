package app.brunosantos.orderreceiving.service;

import app.brunosantos.orderreceiving.client.SupplierClient;
import app.brunosantos.orderreceiving.client.ResellerClient;
import app.brunosantos.orderreceiving.dto.*;
import app.brunosantos.orderreceiving.exception.ResellerNotFoundException;
import app.brunosantos.orderreceiving.model.Order;
import app.brunosantos.orderreceiving.model.OrderStatus;
import app.brunosantos.orderreceiving.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderServiceTest {

    @Mock
    private SupplierClient supplierClient;

    @Mock
    private ResellerClient resellerClient;

    @Mock
    private RetryQueueService retryQueueService;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private OrderCreateDTO orderCreateDTO;

    @BeforeEach
    public void setUp() {
        orderCreateDTO = new OrderCreateDTO();
        orderCreateDTO.setResellerCnpj("79554131000170");
        orderCreateDTO.setItems(List.of(new OrderItemCreateDTO(1L, 1000)));
    }

    @Test
    public void shouldProcessOrderWhenSupplierIsAvailable() {
        // Configuração do mock do revendedor
        ResellerDTO resellerDTO = new ResellerDTO(
            1L,
            "79554131000170",
            "Reseller Corp",
            "Reseller Trade",
            "contact@reseller.com",
            List.of("John Doe"), // Lista de nomes de contato
            List.of("11999999999"), // Lista de números de telefone
            List.of(new AddressDTO(
                1L, // ID do endereço
                "Street 123",
                "123", // Número
                "City",
                "SP", // Estado
                "12345678", // CEP
                "Near park" // Complemento
            ))
        );
        ResponseEntity<ResellerDTO> resellerResponse = ResponseEntity.ok(resellerDTO);
        when(resellerClient.findResellerByCnpj(any(String.class))).thenReturn(resellerResponse);

        // Mock do SupplierClient
        // Criando os itens do pedido (SupplierOrderItemDTO)
        SupplierOrderItemDTO item1 = new SupplierOrderItemDTO(1L, 1000);  // 1º item, quantidade 1000
        SupplierOrderItemDTO item2 = new SupplierOrderItemDTO(2L, 500);   // 2º item, quantidade 500

        // Criando a lista de itens
        List<SupplierOrderItemDTO> items = List.of(item1, item2);

        SupplierOrderDTO supplierOrderDTO = new SupplierOrderDTO("supplierOrderId123",                      // supplierOrderId fictício
            LocalDateTime.now(),                        // processedAt com a data/hora atual
            items);
            when(supplierClient.createOrder(any(SupplierOrderCreateDTO.class))).thenReturn(supplierOrderDTO);

        // Mock do repositório de pedidos
        Order savedOrder = new Order();
        savedOrder.setStatus(OrderStatus.PENDING);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Chamada ao método
        OrderDTO orderDTO = orderService.createOrder(orderCreateDTO);

        // Verificação
        verify(supplierClient, times(1)).createOrder(any(SupplierOrderCreateDTO.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(OrderStatus.PROCESSED.name(), orderDTO.getStatus());
        assertEquals("supplierOrderId123", orderDTO.getSupplierOrderId());
    }

    @Test
    public void shouldRetryOrderWhenSupplierIsNotAvailable() {
        // Configuração do mock do revendedor
        ResellerDTO resellerDTO = new ResellerDTO(
            1L,
            "79554131000170",
            "Reseller Corp",
            "Reseller Trade",
            "contact@reseller.com",
            List.of("John Doe"), // Lista de nomes de contato
            List.of("11999999999"), // Lista de números de telefone
            List.of(new AddressDTO(
                1L, // ID do endereço
                "Street 123",
                "123", // Número
                "City",
                "SP", // Estado
                "12345678", // CEP
                "Near park" // Complemento
            ))
        );
        ResponseEntity<ResellerDTO> resellerResponse = ResponseEntity.ok(resellerDTO);
        when(resellerClient.findResellerByCnpj(any(String.class))).thenReturn(resellerResponse);

        // Simulação de falha do fornecedor
        when(supplierClient.createOrder(any(SupplierOrderCreateDTO.class)))
            .thenThrow(new RuntimeException("Supplier API unavailable"));

        // Simulação do comportamento de persistência do pedido
        Order savedOrder = new Order();
        savedOrder.setStatus(OrderStatus.PENDING);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Chamada ao método
        OrderDTO orderDTO = orderService.createOrder(orderCreateDTO);

        // Verificação
        verify(supplierClient, times(1)).createOrder(any(SupplierOrderCreateDTO.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(OrderStatus.RETRY_PENDING.name(), orderDTO.getStatus());
        verify(retryQueueService, times(1)).enqueue(any(UUID.class));
    }

    @Test
    public void shouldValidateResellerSuccessfully() {
        // Configuração do mock do revendedor
        ResellerDTO resellerDTO = new ResellerDTO(
            1L,
            "79554131000170",
            "Reseller Corp",
            "Reseller Trade",
            "contact@reseller.com",
            List.of("John Doe"), // Lista de nomes de contato
            List.of("11999999999"), // Lista de números de telefone
            List.of(new AddressDTO(
                1L, // ID do endereço
                "Street 123",
                "123", // Número
                "City",
                "SP", // Estado
                "12345678", // CEP
                "Near park" // Complemento
            ))
        );
        ResponseEntity<ResellerDTO> resellerResponse = ResponseEntity.ok(resellerDTO);
        when(resellerClient.findResellerByCnpj(any(String.class))).thenReturn(resellerResponse);

        // Chamada para validar o revendedor
        orderService.createOrder(orderCreateDTO);

        // Verificação
        verify(resellerClient, times(1)).findResellerByCnpj(any(String.class));
    }

    @Test
    public void shouldThrowExceptionWhenResellerNotFound() {
        // Configuração do mock do revendedor (não encontrado)
        when(resellerClient.findResellerByCnpj(any(String.class)))
            .thenReturn(ResponseEntity.notFound().build());

        // Chamada para validar o revendedor e verificar exceção
        assertThrows(ResellerNotFoundException.class, () -> {
            orderService.createOrder(orderCreateDTO);
        });
    }
}
