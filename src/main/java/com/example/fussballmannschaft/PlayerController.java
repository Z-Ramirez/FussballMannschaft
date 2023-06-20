package com.example.fussballmannschaft;

import ch.ubs.m295.generated.v1.controller.PlayersApi;
import ch.ubs.m295.generated.v1.dto.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;


import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController extends PlayerRestController implements PlayersApi {
    public PlayerDao playerDao;

    public PlayerController(PlayerDao playerDao){
        this.playerDao = playerDao;
    }

    @Override
    public ResponseEntity<List<Player>> playersGet() {
        Optional<List<Player>> players = playerDao.getAllPlayers();
        if (players.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            return getRespond(players.get());
        }
    }

    @Override
    public ResponseEntity<Void> playersPost(Player player) {
        playerDao.insertPlayer(player);
        return postRespond();
    }

    @Override
    public ResponseEntity<Void> playersPut(Player player) {
        playerDao.updatePlayer(player);
        return updateRespond();
    }

    @Override
    public ResponseEntity<Void> playersPlayerIdDelete(Integer playerId) {
        playerDao.deletePlayer(playerId);
        return deleteRespond();
    }
}

