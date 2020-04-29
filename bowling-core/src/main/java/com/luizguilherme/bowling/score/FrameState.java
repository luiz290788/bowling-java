package com.luizguilherme.bowling.score;

/**
 * Represents a state of a frame.
 */
public enum FrameState {
  NOT_PLAYED(2, 0), FIRST_ROLL_PLAYED(2, 0), BOTH_ROLLS_PLAYED(2, 0), STRIKE(1, 2), SPARE(2, 1);

  private int allowedRolls;
  private int bonusCount;

  FrameState(int allowedRolls, int bonusCount) {
    this.allowedRolls = allowedRolls;
    this.bonusCount = bonusCount;
  }

  /**
   * The amount of bonues allowed in the state.
   */
  public int getBonusCount() {
    return this.bonusCount;
  }

  /**
   * The amount of normal rolls allowed in the state.
   */
  public int getAllowedRolls() {
    return this.allowedRolls;
  }
}