package com.luizguilherme.bowling.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FrameTest {

  @Test
  public void shouldAcceptRoll() {
    Frame frame = new Frame();
    assertTrue(frame.acceptRoll());
  }

  @Test
  public void shouldNotAcceptRollAfterAStrike() {
    Frame frame = new Frame();
    assertTrue(frame.acceptRoll());

    frame.addRoll(new Roll(10, RollState.PLAYED));

    assertFalse(frame.acceptRoll());
  }

  @Test
  public void shouldNotAcceptRollAfterSecondPlay() {
    Frame frame = new Frame();
    assertTrue(frame.acceptRoll());

    frame.addRoll(new Roll(3, RollState.PLAYED));
    frame.addRoll(new Roll(5, RollState.PLAYED));

    assertFalse(frame.acceptRoll());
  }

  @Test
  public void shouldNotAcceptRollAfterSpare() {
    Frame frame = new Frame();
    assertTrue(frame.acceptRoll());

    frame.addRoll(new Roll(3, RollState.PLAYED));
    frame.addRoll(new Roll(7, RollState.PLAYED));

    assertFalse(frame.acceptRoll());
  }

  @Test
  public void shouldReturnTrueForIsStrike() {
    Frame frame = new Frame();
    frame.addRoll(new Roll(10, RollState.PLAYED));

    assertTrue(frame.isStrike());
    assertFalse(frame.isSpare());
  }

  @Test
  public void shouldReturnTrueForIsSpare() {
    Frame frame = new Frame();
    frame.addRoll(new Roll(8, RollState.PLAYED));
    frame.addRoll(new Roll(2, RollState.PLAYED));

    assertFalse(frame.isStrike());
    assertTrue(frame.isSpare());
  }

  @Test
  public void shouldReturnScoreAfterFirstPlay() {
    Frame frame = new Frame();

    frame.addRoll(new Roll(4, RollState.PLAYED));
    assertEquals(4, frame.getScore());
  }

  @Test
  public void shouldNotAcceptBonusesOnUnplayed() {
    Frame frame = new Frame();
    assertFalse(frame.acceptBonus());
  }

  @Test
  public void shouldNotAcceptBonusesAfterFirstPlay() {
    Frame frame = new Frame();
    frame.addRoll(new Roll(5, RollState.PLAYED));
    assertFalse(frame.acceptBonus());
  }

  @Test
  public void shouldNotAcceptBonusesAfterSecondPlay() {
    Frame frame = new Frame();
    frame.addRoll(new Roll(5, RollState.PLAYED));
    frame.addRoll(new Roll(3, RollState.PLAYED));
    assertFalse(frame.acceptBonus());
  }

  @Test
  public void shouldAcceptBonusesAfterSpare() {
    Frame frame = new Frame();
    frame.addRoll(new Roll(5, RollState.PLAYED));
    frame.addRoll(new Roll(5, RollState.PLAYED));
    assertTrue(frame.acceptBonus());
  }

  @Test
  public void shouldAcceptBonusesAfterStrike() {
    Frame frame = new Frame();
    frame.addRoll(new Roll(10, RollState.PLAYED));
    assertTrue(frame.acceptBonus());
  }

  @Test
  public void shouldStopAcceptingBonuses() {
    Frame strikeFrame = new Frame();
    strikeFrame.addRoll(new Roll(10, RollState.PLAYED));
    assertTrue(strikeFrame.acceptBonus());
    strikeFrame.addRoll(new Roll(10, RollState.PLAYED));
    strikeFrame.addRoll(new Roll(10, RollState.PLAYED));
    assertFalse(strikeFrame.acceptBonus());

    Frame spareFrame = new Frame();
    spareFrame.addRoll(new Roll(5, RollState.PLAYED));
    spareFrame.addRoll(new Roll(5, RollState.PLAYED));
    assertTrue(spareFrame.acceptBonus());
    spareFrame.addRoll(new Roll(5, RollState.PLAYED));
    assertFalse(spareFrame.acceptBonus());
  }

  @Test(expected = Frame.ScoreOverflowException.class)
  public void shouldThrowScoreOverflowException() {
    Frame frame = new Frame();
    frame.addRoll(new Roll(8));
    frame.addRoll(new Roll(8));
  }

  @Test(expected = Frame.RollNotAllowedInFrame.class)
  public void shouldThrowRollNotAllowedInFrame() {
    Frame frame = new Frame();
    frame.addRoll(new Roll(10));
    frame.addRoll(new Roll(1));
    frame.addRoll(new Roll(8));
    frame.addRoll(new Roll(8));
  }
}