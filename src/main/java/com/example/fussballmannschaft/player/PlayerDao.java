package com.example.fussballmannschaft.player;

import ch.ubs.m295.generated.v1.dto.Player;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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
                    String secondname = rs.getString("secondname");
                    int age = rs.getInt("age");
                    int teamId = rs.getInt("teamId");
                    return new Player()
                            .playerId(playerId)
                            .firstname(firstName)
                            .secondname(secondname)
                            .age(age)
                            .teamId(teamId);
                }
        );
        if (result.isEmpty()){
            return Optional.empty();
        } else {
            return Optional.of(result);
        }
    }


    public void insertPlayer(Player player) {
        String sql = "INSERT INTO player (playerId, firstname, secondname, age, teamId) VALUES (:playerId, :firstname, :secondname, :age, :teamId)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("playerId", player.getPlayerId())
                .addValue("firstname", player.getFirstname())
                .addValue("secondname", player.getSecondname())
                .addValue("age", player.getAge().toString())
                .addValue("teamId", player.getTeamId());
        int rowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);
        if(rowsAffected == 1){
            System.out.println("Player inserted");
        }
        else{
            System.out.println("Player not inserted");
        }
    }

    public void updatePlayer(Player player) {
        String sql = "UPDATE player SET firstname = :firstname, secondname = :secondname, age = :age, teamId = :teamId  WHERE playerId = :playerId";
        //KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("playerId", player.getPlayerId())
                .addValue("firstname", player.getFirstname())
                .addValue("secondname", player.getSecondname())
                .addValue("age", player.getAge())
                .addValue("teamId", player.getTeamId());
        int rowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters/*, keyHolder*/);
        if(rowsAffected == 1){
            System.out.println("Player updated");
        }
        else{
            System.out.println("Player not updated");
        }
    }

    public void deletePlayer(int playerId) {
        String sql = "DELETE FROM player WHERE playerId = :playerId";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("playerId", playerId);
        int rowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);
        if(rowsAffected == 1){
            System.out.println("Player deleted");
        }
        else{
            System.out.println("Player not deleted");
        }
    }
}
