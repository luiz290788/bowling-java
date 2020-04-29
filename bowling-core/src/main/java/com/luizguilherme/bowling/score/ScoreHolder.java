package com.luizguilherme.bowling.score;

import java.util.Collection;

/**
 * A container that has a score associated with it. Example of containers Roll,
 * Frame, Score.
 * 
 * @see Roll
 * @see Frame
 * @see Score
 */
public interface ScoreHolder {

  int getScore();

  /**
   * Calculates the sum of a collection of ScoreHolder.
   */
  public static <T extends ScoreHolder> int sum(Collection<T> scoreHolders) {
    return scoreHolders.stream().map(ScoreHolder::getScore).reduce(0, Integer::sum);
  }
}