package com.example.fussballmannschaft.Team;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class TeamConfiguration {

        @Bean
        TeamDao teamDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
            return new TeamDao(namedParameterJdbcTemplate);
        }

}
