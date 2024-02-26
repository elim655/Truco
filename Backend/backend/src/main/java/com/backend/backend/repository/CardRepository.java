package com.backend.backend.repository;

import com.backend.backend.model.Card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CardRepository extends JpaRepository<Card, Long> {

    //returns players cards in hand
    @Query("SELECT c FROM Card c WHERE c.player.id = :playerId AND c.game.id = :gameId")
    List<Card> findCardsByPlayerIdAndGameId(@Param("playerId") Long playerId, @Param("gameId") Long gameId);

    //returns which cards have been played
    @Query("SELECT c FROM Card c WHERE c.cardPlayed = true AND c.game.id = :gameId")
    List<Card> cardsPlayed(@Param("gameId") Long gameId);

    @Query("SELECT c FROM Card c WHERE c.cardPlayed = true AND c.game.id = :gameId AND c.player = :player")
    Card cardPlayedby(@Param("gameId") Long gameId, @Param("player") Long player);
}


