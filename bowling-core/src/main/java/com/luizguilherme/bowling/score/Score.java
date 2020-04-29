package com.luizguilherme.bowling.score;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * A series of frames representing the score of a player in a bowling game.
 */
public class Score {

  private final int frameCount;
  private final List<Frame> frames;
  private Frame currentFrame;

  public Score() {
    this(10);
  }

  /**
   * Creates a score.
   * 
   * @param frameCount the number of frames allowed in this game.
   */
  public Score(int frameCount) {
    if (frameCount <= 0) {
      throw new IllegalFrameCount(frameCount);
    }

    this.frameCount = frameCount;
    this.frames = new ArrayList<>(this.frameCount);
    createFrame();
  }

  private void createFrame() {
    this.currentFrame = new Frame();
    this.frames.add(this.currentFrame);
  }

  private boolean addBonuses(int start, Roll roll) {
    int i = start;
    boolean bonusAdded = false;
    while (i >= 0 && this.frames.get(i).acceptBonus()) {
      this.frames.get(i).addRoll(roll);
      bonusAdded = true;
      i--;
    }
    return bonusAdded;
  }

  /**
   * Adds a roll to the score. Either adding to the current frame or creating a
   * new one.
   */
  public Score addRoll(Roll roll) {
    if (!this.currentFrame.acceptRoll()) {
      if (this.frames.size() == this.frameCount) {
        if (!addBonuses(this.frameCount - 1, roll)) {
          throw new RollNotAllowedInScore();
        }
        return this;
      }
      createFrame();
    }

    this.currentFrame.addRoll(roll);
    addBonuses(this.frames.size() - 2, roll);

    return this;
  }

  /**
   * Calculates the total score of the player.
   */
  public int getScore() {
    return ScoreHolder.sum(this.frames);
  }

  private boolean acceptBonus() {
    int index = this.frames.size() - 1;
    int limit = Integer.max(this.frames.size() - 2, 0);
    while (index >= limit) {
      if (this.frames.get(index).acceptBonus()) {
        return true;
      }
      limit--;
    }
    return false;
  }

  /**
   * Checks if the game has finished according to the number of frames.
   * 
   * @return true if the player has performed all the plays, false otherwise.
   */
  public boolean hasFinished() {
    return this.frames.size() == this.frameCount && !this.currentFrame.acceptRoll() && !this.acceptBonus();
  }

  /**
   * Gets the scores of the player after each frame.
   * 
   * @return a stream of integers representing the total score after each frame.
   */
  public Stream<Integer> getScoresByFrames() {
    AtomicInteger score = new AtomicInteger(0);
    return this.frames.stream().map(ScoreHolder::getScore).map(score::addAndGet);
  }

  /**
   * Gets the played frames.
   * 
   * @return a list of frames in order they were played.
   */
  public List<Frame> getFrames() {
    return frames;
  }

  /**
   * Thrown to indicate that frame count provided is illegal.
   * 
   * Frame count needs to be greater than 0.
   */
  public static final class IllegalFrameCount extends IllegalArgumentException {
    private static final long serialVersionUID = 2031162654812422052L;

    private IllegalFrameCount(int frameCount) {
      super(String.format("Frame count must be greater than 0. Provided frame count %d", frameCount));
    }
  }

  /**
   * Thrown to indicate that a new roll is not allowed in the score. A new roll
   * won't be allowed when the game has already finished.
   */
  static public final class RollNotAllowedInScore extends IllegalStateException {
    private static final long serialVersionUID = 8415564367333013683L;

    private RollNotAllowedInScore() {
      super("Game has finished, a new roll is not allowed.");
    }
  }
}