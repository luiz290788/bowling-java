package com.luizguilherme.bowling.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import com.luizguilherme.bowling.cli.input.FilePlaysReader;
import com.luizguilherme.bowling.cli.input.InputStreamPlaysReader;
import com.luizguilherme.bowling.cli.input.PlaysReader;
import com.luizguilherme.bowling.cli.writer.PrintStreamScoreWriter;
import com.luizguilherme.bowling.cli.writer.ScoreWriter;
import com.luizguilherme.bowling.game.Game;

public class App {

  public static PlaysReader getReader(String[] args) throws FileNotFoundException {
    if (args.length == 0) {
      return new InputStreamPlaysReader(System.in);
    }
    return new FilePlaysReader(new File(args[0]));
  }

  public static ScoreWriter getWriter(String[] args) throws IOException {
    if (args.length < 2) {
      return new PrintStreamScoreWriter(System.out);
    }
    File file = new File(args[1]);
    file.createNewFile();
    return new PrintStreamScoreWriter(new PrintStream(file));
  }

  public static void main(String[] args) throws IOException {
    PlaysReader playsReader = getReader(args);
    ScoreWriter scoreWriter = getWriter(args);

    Game game = new Game();
    playsReader.read().forEach(game::addPlay);
    scoreWriter.write(game);
  }
}