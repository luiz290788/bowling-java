package com.luizguilherme.bowling.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class ScoreTest {

  @Test
  public void shouldCalculateJeffScore() {
    Score score = new Score();
    Roll[] rolls = new Roll[] { Roll.STRIKE, new Roll(7), new Roll(3), new Roll(9), new Roll(0), Roll.STRIKE,
        new Roll(0), new Roll(8), new Roll(8), new Roll(2), Roll.FAILED_ROLL, new Roll(6), Roll.STRIKE, Roll.STRIKE,
        Roll.STRIKE, new Roll(8), new Roll(1) };
    Arrays.stream(rolls).forEach(score::addRoll);

    assertEquals(167, score.getScore());
  }

  @Test
  public void shouldCalculateJohnScore() {
    Score score = new Score();
    Roll[] rolls = new Roll[] { new Roll(3), new Roll(7), new Roll(6), new Roll(3), Roll.STRIKE, new Roll(8),
        new Roll(1), Roll.STRIKE, Roll.STRIKE, new Roll(9), new Roll(0), new Roll(7), new Roll(3), new Roll(4),
        new Roll(4), Roll.STRIKE, new Roll(9), new Roll(0) };
    Arrays.stream(rolls).forEach(score::addRoll);

    assertEquals(151, score.getScore());
  }

  @Test
  public void shouldCalculatePerfectGameScore() {
    Score score = new Score();
    IntStream.range(0, 12).forEach(i -> score.addRoll(Roll.STRIKE));

    assertEquals(300, score.getScore());
  }

  @Test
  public void shouldCalculateScoreOfAllFailed() {
    Score score = new Score();
    IntStream.range(0, 10).forEach(i -> score.addRoll(Roll.FAILED_ROLL));

    assertEquals(0, score.getScore());
  }

  @Test
  public void shouldCalculateScoreOfAllZeros() {
    Score score = new Score();
    Roll zero = new Roll(0);
    IntStream.range(0, 10).forEach(i -> score.addRoll(zero));

    assertEquals(0, score.getScore());
  }

  @Test
  public void shouldCalculateScoreOfAllOfSame() {
    Roll zero = new Roll(0);
    IntStream.range(1, 9).mapToObj(i -> new Roll(i)).forEach(roll -> {
      Score score = new Score();
      IntStream.range(0, 10).forEach(i -> {
        score.addRoll(roll);
        score.addRoll(zero);
      });
      assertEquals(roll.getScore() * 10, score.getScore());
    });
  }

  @Test
  public void shouldConsiderNotFinishedWhenThereIsFramesLeft() {
    Score score = new Score(3);
    score.addRoll(new Roll(3));
    score.addRoll(new Roll(3));

    assertFalse(score.hasFinished());
  }

  @Test
  public void shouldConsiderNotFinishedWhenThereIs2BonusesLeft() {
    Score score = new Score(3);
    IntStream.range(0, 3).forEach(i -> score.addRoll(Roll.STRIKE));

    assertFalse(score.hasFinished());
  }

  @Test
  public void shouldConsiderNotFinishedWhenThereIsBonusLeft() {
    Score score = new Score(3);
    IntStream.range(0, 4).forEach(i -> score.addRoll(Roll.STRIKE));

    assertFalse(score.hasFinished());
  }

  @Test
  public void shouldConsiderFinishedWhenNoMoreRollsAreAllowed() {
    Score score = new Score(3);
    IntStream.range(0, 6).forEach(i -> score.addRoll(new Roll(1)));
    assertTrue(score.hasFinished());
  }

  @Test
  public void shouldReturnScoresByFrame() {
    List<Integer> expected = Arrays.asList(30, 60, 90);
    Score score = new Score(3);
    IntStream.range(0, 5).forEach(i -> score.addRoll(Roll.STRIKE));
    List<Integer> actual = score.getScoresByFrames().collect(Collectors.toList());
    assertEquals(expected, actual);
  }

  @Test(expected = Score.IllegalFrameCount.class)
  public void shouldThrowIllegalFrameCountForZeroFrameCount() {
    new Score(0);
  }

  @Test(expected = Score.IllegalFrameCount.class)
  public void shouldThrowIllegalFrameCountForNegativeFrameCount() {
    new Score(-1);
  }

  @Test(expected = Score.RollNotAllowedInScore.class)
  public void shouldThrowRollNotAllowedInScore() {
    Score score = new Score(1);
    score.addRoll(new Roll(1));
    score.addRoll(new Roll(1));
    score.addRoll(new Roll(1));
  }
}