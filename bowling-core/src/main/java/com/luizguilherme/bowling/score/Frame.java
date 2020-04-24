package com.luizguilherme.bowling.score;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Frame implements ScoreHolder {

  private FrameState state;
  private List<Roll> rolls;
  private List<Roll> bonuses;

  public Frame() {
    this.state = FrameState.NOT_PLAYED;
    this.rolls = new ArrayList<>(2);
    this.bonuses = new ArrayList<>(2);
  }

  public boolean acceptRoll() {
    return this.rolls.size() < this.state.getAllowedRolls();
  }

  private boolean acceptRoll(Roll roll) {
    return (this.state == FrameState.FIRST_ROLL_PLAYED && this.getFirstRoll().getScore() + roll.getScore() <= 10
        || this.state != FrameState.FIRST_ROLL_PLAYED);
  }

  public Frame addRoll(Roll roll) {
    if (this.acceptRoll()) {
      if (!this.acceptRoll(roll)) {
        throw new ScoreOverflowException(roll);
      }
      this.rolls.add(roll);
      updateState();
    } else if (this.acceptBonus()) {
      this.bonuses.add(roll);
    } else {
      throw new RollNotAllowedInFrame();
    }
    return this;
  }

  private Roll getFirstRoll() {
    return this.rolls.get(0);
  }

  private void updateState() {
    if (this.state == FrameState.NOT_PLAYED) {
      if (this.getFirstRoll().getScore() == 10) {
        this.state = FrameState.STRIKE;
      } else {
        this.state = FrameState.FIRST_ROLL_PLAYED;
      }
    } else if (this.state == FrameState.FIRST_ROLL_PLAYED) {
      if (this.getRollsScore() == 10) {
        this.state = FrameState.SPARE;
      } else {
        this.state = FrameState.BOTH_ROLLS_PLAYED;
      }
    }
  }

  public boolean isStrike() {
    return this.state == FrameState.STRIKE;
  }

  public boolean isSpare() {
    return this.state == FrameState.SPARE;
  }

  public int getRollsScore() {
    return sumRollsScore(this.rolls.stream());
  }

  private Integer sumRollsScore(Stream<Roll> rolls) {
    return ScoreHolder.sum(this.rolls);
  }

  @Override
  public int getScore() {
    return ScoreHolder.sum(this.rolls) + ScoreHolder.sum(this.bonuses);
  }

  public List<Roll> getRolls() {
    return rolls;
  }

  public List<Roll> getBonuses() {
    return bonuses;
  }

  public boolean acceptBonus() {
    return this.state.getBonusCount() - this.bonuses.size() > 0;
  }

  static public final class ScoreOverflowException extends IllegalArgumentException {
    private static final long serialVersionUID = -4478721646571934301L;

    private ScoreOverflowException(Roll roll) {
      super(String.format("Roll %s not allowed because it overflows frame.", roll));
    }
  }

  static public final class RollNotAllowedInFrame extends IllegalStateException {
    private static final long serialVersionUID = 9252926006679146L;

    private RollNotAllowedInFrame() {
      super("Frame is closed, a new roll is not allowed.");
    }
  }
}