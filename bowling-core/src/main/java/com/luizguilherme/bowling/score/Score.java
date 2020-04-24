package com.luizguilherme.bowling.score;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Score {

  private final int frameCount;
  private final List<Frame> frames;
  private Frame currentFrame;

  public Score() {
    this(10);
  }

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

  public boolean hasFinished() {
    return this.frames.size() == this.frameCount && !this.currentFrame.acceptRoll() && !this.acceptBonus();
  }

  public Stream<Integer> getScoresByFrames() {
    AtomicInteger score = new AtomicInteger(0);
    return this.frames.stream().map(ScoreHolder::getScore).map(score::addAndGet);
  }

  public List<Frame> getFrames() {
    return frames;
  }

  public static final class IllegalFrameCount extends IllegalArgumentException {
    private static final long serialVersionUID = 2031162654812422052L;

    private IllegalFrameCount(int frameCount) {
      super(String.format("Frame count must be greater than 0. Provided frame count %d", frameCount));
    }
  }

  static public final class RollNotAllowedInScore extends IllegalStateException {
    private static final long serialVersionUID = 8415564367333013683L;

    private RollNotAllowedInScore() {
      super("Game has finished, a new roll is not allowed.");
    }
  }
}