package com.backend.backend.repository;

import com.backend.backend.model.GameRoom;
import com.backend.backend.model.Player;
import com.backend.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByGameRoomId(Long roomId);

    Player findByPlayerName(String host);

    Player findByUser(User user);

    Player findByPlayerNameAndGameRoomId(String playerToQuitName, Long roomId);

    long countByGameRoomAndTeam(GameRoom gameRoom, int newTeam);
}
