package com.luizguilherme.bowling.score;

public class Roll {

  private final int score;
  private final RollState state;

  public static final Roll FAILED_ROLL = new Roll(0, RollState.FAILED);
  public static final Roll STRIKE = new Roll(10);

  public Roll(final int score) {
    this(score, RollState.PLAYED);
  }

  public Roll(final int score, final RollState state) {
    // TODO check if score is in the limits.
    this.score = score;
    this.state = state;
  }

  public int getScore() {
    return this.score;
  }

  public boolean isFailed() {
    return this.state == RollState.FAILED;
  }
}