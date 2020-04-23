package com.luizguilherme.bowling.score;

import static org.junit.Assert.assertEquals;

import java.util.stream.IntStream;

import org.junit.Test;

public class RollTest {

  @Test
  public void shouldInstantiateRollCorrectly() {
    IntStream.range(0, 11).forEach(i -> {
      Roll roll = new Roll(i);
      assertEquals(i, roll.getScore());
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowForValueBellow0() {
    new Roll(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowForValueAbove10() {
    new Roll(11);
  }

  @Test
  public void shouldUseScore0ForFailed() {
    Roll roll = new Roll(10, RollState.FAILED);
    assertEquals(0, roll.getScore());
  }
}