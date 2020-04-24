package com.luizguilherme.bowling.cli.writer;

import com.luizguilherme.bowling.game.Game;

public interface ScoreWriter {

  void write(Game game);
}