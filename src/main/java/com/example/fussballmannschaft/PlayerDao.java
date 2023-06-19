package com.example.fussballmannschaft;

import ch.ubs.m295.generated.v1.dto.Player;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;
import java.util.Optional;

public class PlayerDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static String SELECT_ALL_QUERY = "SELECT * FROM Player";

    public PlayerDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    public Optional<List<Player>> getAllPlayers(){
        SqlParameterSource namedParameters = new MapSqlParameterSource();
        List<Player> result = namedParameterJdbcTemplate.query(
                SELECT_ALL_QUERY,
                namedParameters,
                (rs, rowNum) ->{
                    int playerId = rs.getInt("playerId");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("secondname");
                    int age = rs.getInt("age");
                    int teamId = rs.getInt("teamId");
                    return new Player()
                            .teamId(teamId)
                            .firstname(firstName)
                            .lastname(lastName)
                            .age(age)
                            .roomId(teamId);
                }
        );
        if (result.isEmpty()){
            return Optional.empty();
        } else {
            return Optional.of(result);
        }
    }
}
