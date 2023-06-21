package com.example.fussballmannschaft;

import ch.ubs.m295.generated.v1.dto.Player;
import com.example.fussballmannschaft.player.PlayerDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FussballMannschaftApplicationTests {

        @Mock
        private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

        private PlayerDao playerDao;

    Player player = new Player()
        .playerId(1)
            .firstname("Daria")
            .secondname("Heusser")
            .age(1)
            .teamId(3);
    @BeforeEach
        void setup() {
            this.playerDao = new PlayerDao(this.namedParameterJdbcTemplate);
        }

    @Test
    void getPlayer(){
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);

        List<Player> expectedPLayer = Arrays.asList(player);

        when(namedParameterJdbcTemplate.query(eq("SELECT * FROM Player"), argumentCaptor.capture(), any(RowMapper.class)
        )).thenReturn(expectedPLayer);

        Optional<List<Player>> players = playerDao.getAllPlayers();

        assertEquals(expectedPLayer, players.get());
    }

    @Test
    void testInsertPlayer(){
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);

        PlayerDao playerDao = new PlayerDao(this.namedParameterJdbcTemplate);

        playerDao.insertPlayer(player);

        verify(this.namedParameterJdbcTemplate).update(
                eq("""
                INSERT INTO player (playerId, firstname, secondname, age, teamId) VALUES (:playerId, :firstname, :secondname, :age, :teamId)"""),
                argumentCaptor.capture(),
                any(GeneratedKeyHolder.class)
        );
        MapSqlParameterSource mapSqlParameterSource = argumentCaptor.getValue();
        assertThat(mapSqlParameterSource.getValue("playerId")).isEqualTo(1);
        assertThat(mapSqlParameterSource.getValue("firstname")).isEqualTo("Daria");
    }

    @Test
    void testUpdatePlayer() {
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
                ArgumentCaptor.forClass(MapSqlParameterSource.class);


        playerDao.updatePlayer(player);

        verify(namedParameterJdbcTemplate, times(1)).update(
                eq("UPDATE player SET firstname = :firstname, secondname = :secondname, age = :age, teamId = :teamId  WHERE playerId = :playerId"),
                argumentCaptor.capture()
        );


        MapSqlParameterSource mapSqlParameterSource = argumentCaptor.getValue();
        assertThat(mapSqlParameterSource.getValue("firstname")).isEqualTo("Daria");
    }


    @Test
    void TestDeletePlayer(){

    ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
            ArgumentCaptor.forClass(MapSqlParameterSource.class);

        playerDao.deletePlayer(1);

        verify(namedParameterJdbcTemplate, times(1)).update(
                eq("DELETE FROM player WHERE playerId = :playerId"),
                argumentCaptor.capture(),
                any(GeneratedKeyHolder.class)
        );


        MapSqlParameterSource mapSqlParameterSource = argumentCaptor.getValue();
    assertThat(mapSqlParameterSource.getValue("playerId")).isEqualTo(1);
}

}
