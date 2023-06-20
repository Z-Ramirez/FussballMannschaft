package com.example.fussballmannschaft;

import ch.ubs.m295.generated.v1.controller.PlayersApi;
import ch.ubs.m295.generated.v1.dto.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController extends AbstractRestController implements PlayersApi {
    public PlayerDao playerDao;

    private final List<Player> players = new ArrayList<>();

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
        players.add(player);
        return postRespond();
    }
}

