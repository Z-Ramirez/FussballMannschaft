package com.example.fussballmannschaft;

import ch.ubs.m295.generated.v1.controller.PlayersApi;
import ch.ubs.m295.generated.v1.dto.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController extends PlayerRestController implements PlayersApi {
    public PlayerDao playerDao;

    Logger logger = LoggerFactory.getLogger(PlayerController.class);


    public PlayerController(PlayerDao playerDao){
        this.playerDao = playerDao;
    }

    @Override
    public ResponseEntity<List<Player>> playersGet() {

        Optional<List<Player>> players = playerDao.getAllPlayers();
        if (players.isEmpty()){
            logger.error("the players arent selected");
            return ResponseEntity.notFound().build();

        }else {
            logger.info("the players are selected");
            return getRespond(players.get());

        }

    }

    @Override
    public ResponseEntity<Void> playersPost(Player player) {
        try {
            playerDao.insertPlayer(player);
            logger.info("Player inserted successfully.");
        } catch (Exception e) {
            logger.warn("the player is maybe a duplicate");
            logger.error("Failed to insert player: " + e.getMessage());
        }
        return postRespond();
    }

    @Override
    public ResponseEntity<Void> playersPut(Player player) {
        try {
            playerDao.updatePlayer(player);
            logger.info("Player updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update player: " + e.getMessage());
        }
        return updateRespond();
    }

    @Override
    public ResponseEntity<Void> playersPlayerIdDelete(Integer playerId) {
        try {
            playerDao.deletePlayer(playerId);
            logger.info("Player with ID " + playerId + " is deleted");
            return deleteRespond();
        } catch (Exception e) {
            logger.error("Failed to delete player with ID " + playerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }










}

