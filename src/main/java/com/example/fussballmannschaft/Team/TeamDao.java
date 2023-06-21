package com.example.fussballmannschaft.Team;


import ch.ubs.m295.generated.v1.dto.Team;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Optional;

public class TeamDao {


        private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

        private final static String SELECT_ALL_QUERY = "SELECT * FROM Team";

        public TeamDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
            this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        }
        public Optional<List<Team>> getAllTeams(){
            SqlParameterSource namedParameters = new MapSqlParameterSource();
            List<Team> result = namedParameterJdbcTemplate.query(
                    SELECT_ALL_QUERY,
                    namedParameters,
                    (rs, rowNum) ->{
                        int teamId = rs.getInt("teamId");
                        String teamName = rs.getString("teamName");
                        return new Team()
                                .teamId(teamId)
                                .teamName(teamName);
                    }
            );
            if (result.isEmpty()){
                return Optional.empty();
            } else {
                return Optional.of(result);
            }
        }


        public void insertTeam(Team team) {
            String sql = "INSERT INTO team (teamId, teamName) VALUES (:teamId, :teamName)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("teamId", team.getTeamId())
                    .addValue("teamName", team.getTeamName());
            int rowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);
            if(rowsAffected == 1){
                System.out.println("Team inserted");
            }
            else{
                System.out.println("Team not inserted");
            }
        }

        public void updateTeam(Team team) {
            String sql = "UPDATE player SET teamName = :teamName WHERE teamId = :teamId";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("teamId", team.getTeamId())
                    .addValue("teamName", team.getTeamName());
            int rowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);
            if(rowsAffected == 1){
                System.out.println("Team updated");
            }
            else{
                System.out.println("Team not updated");
            }
        }

        public void deleteTeam(int teamId) {
            String sql = "DELETE FROM team WHERE teamId = :teamId";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("teamId", teamId);
            int rowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);
            if(rowsAffected == 1){
                System.out.println("Team deleted");
            }
            else{
                System.out.println("Team not deleted");
            }
        }
    }


