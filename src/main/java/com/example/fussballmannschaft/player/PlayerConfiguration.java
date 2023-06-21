package com.example.fussballmannschaft.player;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
    public class PlayerConfiguration {
        @Bean
        PlayerDao playerDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
            return new PlayerDao(namedParameterJdbcTemplate);
        }

    }

