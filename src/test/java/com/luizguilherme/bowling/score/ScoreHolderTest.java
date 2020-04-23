package com.luizguilherme.bowling.score;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class ScoreHolderTest {

  @Test
  public void shouldSumScores() {
    int score = ScoreHolder.sum(Arrays.asList(new Roll(10), new Roll(5), new Roll(10)));
    assertEquals(25, score);
  }

}