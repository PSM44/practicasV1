package com.ipss.practicas.config;

import com.ipss.practicas.seed.SeedService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    private final SeedService seedService;

    public DataLoader(SeedService seedService) {
        this.seedService = seedService;
    }

    @Bean
    CommandLineRunner init(@Value("${app.seed.enabled:true}") boolean seedEnabled) {
        return args -> {
            if (seedEnabled) {
                seedService.run(); // ← llamada entre beans: sí hay proxy transaccional
            } else {
                System.out.println("[SEED] Deshabilitado por propiedad app.seed.enabled=false");
            }
        };
    }
}
