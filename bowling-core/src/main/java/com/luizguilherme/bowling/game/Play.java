package com.luizguilherme.bowling.game;

import com.luizguilherme.bowling.score.Roll;

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