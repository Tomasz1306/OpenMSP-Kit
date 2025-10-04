package com.msp.openmsp_kit;

import com.msp.openmsp_kit.service.scheduler.Scheduler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class OpenmspKitApplication implements CommandLineRunner {

    private final Scheduler scheduler;

    OpenmspKitApplication(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

	public static void main(String[] args) {
		SpringApplication.run(OpenmspKitApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        scheduler.run();
    }
}
