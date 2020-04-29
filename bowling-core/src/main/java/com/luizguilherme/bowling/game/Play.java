package com.luizguilherme.bowling.game;

import com.luizguilherme.bowling.score.Roll;

/**
 * This class represents a Play made by a player.
 */
public class Play {

  private String playerName;
  private Roll roll;

  public Play(String playerName, Roll roll) {
    this.playerName = playerName;
    this.roll = roll;
  }

  public String getPlayerName() {
    return playerName;
  }

  public Roll getRoll() {
    return roll;
  }

}