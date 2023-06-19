package com.example.fussballmannschaft;

import ch.ubs.m295.generated.v1.controller.PlayersApi;
import ch.ubs.m295.generated.v1.dto.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
public class AbstractController extends AbstractRestController implements PlayersApi {

    private final List<Player> players = new ArrayList<>();

    @Override
    public ResponseEntity<List<Player>> playersGet() {
        return getRespond(players);
    }

    @Override
    public ResponseEntity<Void> playersPost(Player player) {
        players.add(player);
        return postRespond();
    }
}

