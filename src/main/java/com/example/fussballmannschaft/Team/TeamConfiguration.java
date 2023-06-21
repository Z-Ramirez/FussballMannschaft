package com.example.fussballmannschaft.Team;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class TeamConfiguration {

        @Bean
        TeamDao teamDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
            return new TeamDao(namedParameterJdbcTemplate);
        }

}
