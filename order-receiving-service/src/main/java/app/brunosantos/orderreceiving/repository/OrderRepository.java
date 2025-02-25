package app.brunosantos.orderreceiving.repository;

import app.brunosantos.orderreceiving.model.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {}