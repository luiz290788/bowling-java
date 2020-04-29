package com.luizguilherme.bowling.game;

import com.luizguilherme.bowling.score.Score;

/**
 * This class represents a player with their score.
 */
public class Player {

  private final String name;
  private final Score score;

  public Player(String name, Score score) {
    this.name = name;
    this.score = score;
  }

  public Score getScore() {
    return score;
  }

  public String getName() {
    return name;
  }

}