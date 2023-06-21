package com.example.fussballmannschaft.Team;

import ch.ubs.m295.generated.v1.controller.TeamsApi;
import ch.ubs.m295.generated.v1.dto.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TeamController extends TeamRestController implements TeamsApi {

        public TeamDao teamDao;

        Logger logger = LoggerFactory.getLogger(com.example.fussballmannschaft.player.PlayerController.class);


        public TeamController(TeamRestController teamRestController){
            this.teamDao = teamDao;
        }

        @Override
        public ResponseEntity<List<Team>> teamsGet() {

            Optional<List<Team>> teams = teamDao.getAllTeams();
            if (teams.isEmpty()){
                logger.error("the teams arent selected");
                return ResponseEntity.notFound().build();

            }else {
                logger.info("the players are selected");
                return getRespond(teams.get());

            }

        }

        @Override
        public ResponseEntity<Void> teamsPost(Team team) {
            try {
                teamDao.insertTeam(team);
                logger.info("Team inserted successfully.");
            } catch (Exception e) {
                logger.warn("the team is maybe a duplicate");
                logger.error("Failed to insert team: " + e.getMessage());
            }
            return postRespond();
        }

        @Override
        public ResponseEntity<Void> teamsPut(Team team) {
            try {
                teamDao.updateTeam(team);
                logger.info("Team updated successfully.");
            } catch (Exception e) {
                logger.error("Failed to update team: " + e.getMessage());
            }
            return updateRespond();
        }

        @Override
        public ResponseEntity<Void> teamsTeamIdDelete(Integer teamId) {
            try {
                teamDao.deleteTeam(teamId);
                logger.info("Player with ID " + teamId + " is deleted");
                return deleteRespond();
            } catch (Exception e) {
                logger.error("Failed to delete team with ID " + teamId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }










    }





