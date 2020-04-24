package com.luizguilherme.bowling.score;

import java.util.Collection;

public interface ScoreHolder {

  int getScore();

  public static <T extends ScoreHolder> int sum(Collection<T> scoreHolders) {
    return scoreHolders.stream().map(ScoreHolder::getScore).reduce(0, Integer::sum);
  }
}