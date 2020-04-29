package com.luizguilherme.bowling.cli.writer;

import com.luizguilherme.bowling.game.Game;

/**
 * Writer of the score.
 */
public interface ScoreWriter {

  void write(Game game);
}