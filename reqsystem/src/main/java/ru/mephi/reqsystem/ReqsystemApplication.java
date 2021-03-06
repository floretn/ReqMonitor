package ru.mephi.reqsystem;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Точка входа в приложение
 */
@SpringBootApplication
@EnableAsync
@EnableAdminServer
public class ReqsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReqsystemApplication.class, args);
	}

}
