package app.brunosantos.orderreceiving;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderReceivingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderReceivingServiceApplication.class, args);
	}

}
