package com.llwantedll.webhearts.models.gameapi.games.hearts.standardpack.services;

import com.llwantedll.webhearts.models.entities.GameRoom;
import com.llwantedll.webhearts.models.entities.User;
import com.llwantedll.webhearts.models.gameapi.exceptions.NoPlayerFoundException;
import com.llwantedll.webhearts.models.gameapi.cards.Card;
import com.llwantedll.webhearts.models.gameapi.cards.standardpack.StandardHideCard;
import com.llwantedll.webhearts.models.gameapi.games.hearts.standardpack.StandardHeartsGame;
import com.llwantedll.webhearts.models.gameapi.games.hearts.standardpack.StandardHeartsGamePlayer;
import com.llwantedll.webhearts.models.services.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameFlowServiceImpl implements GameFlowService {

    private final GameRoomService gameRoomService;

    @Autowired
    public GameFlowServiceImpl(GameRoomService gameRoomService) {
        this.gameRoomService = gameRoomService;
    }

    @Override
    public void hideOtherUserCards(StandardHeartsGame currentGame, StandardHeartsGamePlayer remotePlayer) {
        List<StandardHeartsGamePlayer> players = currentGame.getHeartsPlayers();

        for (StandardHeartsGamePlayer player : players) {
            if (!player.getUsername().equals(remotePlayer.getUsername())) {

                List<Card> cards = new ArrayList<>();

                for (int i = 0; i < player.getCards().size(); i++) {
                    cards.add(new StandardHideCard());
                }

                player.setCards(cards);
            }
        }
    }

    @Override
    public StandardHeartsGamePlayer getPlayerByUser(StandardHeartsGame currentGame, User user) throws NoPlayerFoundException {
        List<StandardHeartsGamePlayer> players = currentGame.getHeartsPlayers();

        for (StandardHeartsGamePlayer player : players) {
            if (player.getUsername().equals(user.getLogin())) {
                return player;
            }
        }

        throw new NoPlayerFoundException();
    }

    @Override
    public GameRoom readyPlayer(GameRoom gameRoom, StandardHeartsGame standardHeartsGame, StandardHeartsGamePlayer player, boolean ready) {
        standardHeartsGame.setPlayerReady(player, ready);

        return gameRoomService.saveGame(gameRoom, standardHeartsGame);
    }
}
