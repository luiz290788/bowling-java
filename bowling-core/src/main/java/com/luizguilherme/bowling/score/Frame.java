package com.luizguilherme.bowling.score;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class represents a frame in the bolwing score.
 */
public class Frame implements ScoreHolder {

  private FrameState state;
  private List<Roll> rolls;
  private List<Roll> bonuses;

  public Frame() {
    this.state = FrameState.NOT_PLAYED;
    this.rolls = new ArrayList<>(2);
    this.bonuses = new ArrayList<>(2);
  }

  /**
   * Checks if the player can still make plays on this frame.
   * 
   * @return true if the can still make plays on this frame, false otherwise.
   */
  public boolean acceptRoll() {
    return this.rolls.size() < this.state.getAllowedRolls();
  }

  private boolean acceptRoll(Roll roll) {
    return (this.state == FrameState.FIRST_ROLL_PLAYED && this.getFirstRoll().getScore() + roll.getScore() <= 10
        || this.state != FrameState.FIRST_ROLL_PLAYED);
  }

  /**
   * Adds a new roll to the frame.
   * 
   * This method check if the new roll is accepted and if it won't affect any game
   * rules. The frame score for the frame rolls cannot exceed 10.
   * 
   * @return this
   */
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

  /**
   * Checks if the frame is a strike.
   * 
   * @return true if the frame is considered a strke, false otherwise.
   */
  public boolean isStrike() {
    return this.state == FrameState.STRIKE;
  }

  /**
   * Checks if the frame is a spare.
   * 
   * @return true if the frame is considered a spare, false otherwise.
   */
  public boolean isSpare() {
    return this.state == FrameState.SPARE;
  }

  /**
   * Calculates and return the score of the frame rolls. This does not include the
   * score from the bonuses rolls.
   * 
   * @return sum of score of frame rolls. 0 <= score <= 10
   */
  public int getRollsScore() {
    return sumRollsScore(this.rolls.stream());
  }

  private Integer sumRollsScore(Stream<Roll> rolls) {
    return ScoreHolder.sum(this.rolls);
  }

  /**
   * Calculates and return the score of the frame. This method includes the score
   * of the bonuses rolls.
   * 
   * @return sum of score of the frame. 0 <= score <= 30.
   */
  @Override
  public int getScore() {
    return ScoreHolder.sum(this.rolls) + ScoreHolder.sum(this.bonuses);
  }

  /**
   * Returns a list of the rolls in the frame. Does not include the bonuses rolls.
   * 
   * @return list of the rolls of the frame in the order they were added.
   */
  public List<Roll> getRolls() {
    return rolls;
  }

  /**
   * Returns a list of the bonuses rolls in the frame. Does not include the
   * regular frame rolls.
   * 
   * @return list of the bonuess rolls of the frame in the order they were added.
   */
  public List<Roll> getBonuses() {
    return bonuses;
  }

  /**
   * Checks if the player can still make bonuses plays on this frame.
   * 
   * @return true if the can still make bonuses plays on this frame, false
   *         otherwise.
   */
  public boolean acceptBonus() {
    return this.state.getBonusCount() - this.bonuses.size() > 0;
  }

  /**
   * Thrown to indicate that the roll is not allowed in the frame because it would
   * overflow the frame limit of 10.
   */
  static public final class ScoreOverflowException extends IllegalArgumentException {
    private static final long serialVersionUID = -4478721646571934301L;

    private ScoreOverflowException(Roll roll) {
      super(String.format("Roll %s not allowed because it overflows frame.", roll));
    }
  }

  /**
   * Thrown to indicate that the frame does not accept more rolls.
   */
  static public final class RollNotAllowedInFrame extends IllegalStateException {
    private static final long serialVersionUID = 9252926006679146L;

    private RollNotAllowedInFrame() {
      super("Frame is closed, a new roll is not allowed.");
    }
  }
}