package com.example.databasapracticeSPRING.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public int[] columnWidthsForColumns() {
        return new int[] {38, 12, 10, 10, 3, 8, 20};
    }

    @Bean
    public int[] columnWidthsForTables() {
        return new int[] {10, 50, 25};
    }
}
