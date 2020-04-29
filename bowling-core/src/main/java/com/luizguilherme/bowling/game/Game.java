package com.luizguilherme.bowling.game;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.luizguilherme.bowling.score.Score;

public class Game {

  private final Map<String, Player> players;

  public Game() {
    this.players = new LinkedHashMap<>();
  }

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

  public Player addPlay(Play play) {
    Player player = this.getPlayer(play.getPlayerName());
    player.getScore().addRoll(play.getRoll());
    return player;
  }

}