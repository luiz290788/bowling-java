package com.luizguilherme.bowling.game;

import static org.junit.Assert.assertEquals;

import com.luizguilherme.bowling.score.Roll;

import org.junit.Test;

public class GameTest {

  @Test
  public void shouldAddRollToPlayer() {
    Game game = new Game();
    String name = "John";
    Player player = game.addPlay(new Play(name, new Roll(10)));

    assertEquals(name, player.getName());
    assertEquals(10, player.getScore().getScore());
  }

  @Test
  public void shouldAddRollsToDifferentPlayers() {
    Game game = new Game();
    String johnName = "John";
    Player john = game.addPlay(new Play(johnName, new Roll(5)));
    String jeffName = "Jeff";
    Player jeff = game.addPlay(new Play(jeffName, new Roll(7)));

    assertEquals(johnName, john.getName());
    assertEquals(5, john.getScore().getScore());
    assertEquals(jeffName, jeff.getName());
    assertEquals(7, jeff.getScore().getScore());
    assertEquals(2, game.getPlayers().size());
  }
}