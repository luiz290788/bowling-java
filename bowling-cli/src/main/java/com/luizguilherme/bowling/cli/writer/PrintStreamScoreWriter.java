package com.luizguilherme.bowling.cli.writer;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.IntStream;

import com.luizguilherme.bowling.game.Game;
import com.luizguilherme.bowling.game.Player;
import com.luizguilherme.bowling.score.Frame;
import com.luizguilherme.bowling.score.Roll;
import com.luizguilherme.bowling.score.Score;

public class PrintStreamScoreWriter implements ScoreWriter {

  private final PrintStream printStream;

  public PrintStreamScoreWriter(PrintStream printStream) {
    this.printStream = printStream;
  }

  private void writeHeader(int framesCount) {
    this.printStream.print("Frame\t\t");
    IntStream.range(1, framesCount + 1).forEach(frameNumber -> this.printStream.printf("%d\t\t", frameNumber));
    this.printStream.println();
  }

  private void printRollScore(Roll roll) {
    if (roll.isFailed()) {
      this.printStream.print("F\t");
    } else if (roll.getScore() == 10) {
      this.printStream.print("X\t");
    } else {
      this.printStream.printf("%d\t", roll.getScore());
    }
  }

  private void printSpare(Frame frame) {
    Roll firstRoll = frame.getRolls().get(0);
    printRollScore(firstRoll);
    this.printStream.print("/\t");
  }

  private void printFrame(Frame frame, boolean printBonus) {
    if (printBonus) {
      if (frame.isStrike()) {
        this.printStream.print("X\t");
      } else if (frame.isSpare()) {
        this.printSpare(frame);
      } else {
        frame.getRolls().forEach(this::printRollScore);
      }
      frame.getBonuses().forEach(this::printRollScore);
    } else if (frame.isStrike()) {
      this.printStream.print("\tX\t");
    } else if (frame.isSpare()) {
      this.printSpare(frame);
    } else {
      frame.getRolls().forEach(this::printRollScore);
    }
  }

  private void writePlayerScore(Player player) {
    this.printStream.println(player.getName());
    Score score = player.getScore();

    this.printStream.print("Pinfalls\t");
    List<Frame> frames = score.getFrames();
    IntStream.range(0, frames.size()).forEach(index -> printFrame(frames.get(index), index == frames.size() - 1));
    this.printStream.println();

    this.printStream.print("Score\t\t");
    score.getScoresByFrames().forEach(frameScore -> this.printStream.printf("%d\t\t", frameScore));
    this.printStream.println();
  }

  @Override
  public void write(Game game) {
    writeHeader(10);
    game.getPlayers().forEach(this::writePlayerScore);
  }

}