package com.luizguilherme.bowling.score;

public class Roll implements ScoreHolder {

  private final int score;
  private final RollState state;

  public static final Roll FAILED_ROLL = new Roll(0, RollState.FAILED);
  public static final Roll STRIKE = new Roll(10);

  public Roll(final int score) {
    this(score, RollState.PLAYED);
  }

  public Roll(final int score, final RollState state) {
    checkScore(score);
    if (state == RollState.FAILED) {
      this.score = 0;
    } else {
      this.score = score;
    }
    this.state = state;
  }

  private void checkScore(final int score) {
    if (score > 10 || score < 0) {
      throw new IllegalArgumentException(
          String.format("%d is invalid. Only numbers between 0 and 10 (inclusive) are allowed", score));
    }
  }

  @Override
  public int getScore() {
    return this.score;
  }

  public boolean isFailed() {
    return this.state == RollState.FAILED;
  }
}