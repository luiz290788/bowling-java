package com.luizguilherme.bowling.game;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.luizguilherme.bowling.score.Score;

/**
 * This class represents a game of bowling accepting multiple players.
 */
public class Game {

  private final Map<String, Player> players;

  public Game() {
    this.players = new LinkedHashMap<>();
  }

  /**
   * Returns the list of players of this game.
   * 
   * @return The list of players in the order they were added to the game.
   */
  public List<Player> getPlayers() {
    return List.copyOf(players.values());
  }

  private Player createPlayer(String playerName) {
    return new Player(playerName, new Score());
  }

  private Player getPlayer(String playerName) {
    this.players.computeIfAbsent(playerName, this::createPlayer);
    return this.players.get(playerName);
  }

  /**
   * Add a Play to the game. If the player is unknown, they will be added to the
   * pool of players.
   */
  public Player addPlay(Play play) {
    Player player = this.getPlayer(play.getPlayerName());
    player.getScore().addRoll(play.getRoll());
    return player;
  }

}