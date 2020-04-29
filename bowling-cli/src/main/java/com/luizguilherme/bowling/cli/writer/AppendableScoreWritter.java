package com.luizguilherme.bowling.cli.writer;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import com.luizguilherme.bowling.game.Game;
import com.luizguilherme.bowling.game.Player;
import com.luizguilherme.bowling.score.Frame;
import com.luizguilherme.bowling.score.Roll;
import com.luizguilherme.bowling.score.Score;

/**
 * Writes the score into an {@link Appendable}
 */
public class AppendableScoreWritter implements ScoreWriter {

  private final Appendable appendable;

  public AppendableScoreWritter(Appendable appendable) {
    this.appendable = appendable;
  }

  private AppendableScoreWritter append(CharSequence cs) {
    try {
      appendable.append(cs);
    } catch (IOException ioException) {
      throw new RuntimeException(ioException);
    }
    return this;
  }

  private AppendableScoreWritter appendLine(String line) {
    append(line.trim());
    append(System.lineSeparator());
    return this;
  }

  private String writeHeader(int framesCount) {
    StringBuilder builder = new StringBuilder();
    builder.append("Frame\t\t");
    IntStream.range(1, framesCount + 1).mapToObj(frameNumber -> String.format("%d\t\t", frameNumber))
        .forEach(builder::append);
    return builder.toString();
  }

  private String printRollScore(Roll roll) {
    if (roll.isFailed()) {
      return ("F\t");
    }
    if (roll.getScore() == 10) {
      return ("X\t");
    }
    return String.format("%d\t", roll.getScore());
  }

  private String printSpare(Frame frame) {
    Roll firstRoll = frame.getRolls().get(0);
    return printRollScore(firstRoll) + "/\t";
  }

  private String printFrame(Frame frame, boolean printBonus) {
    StringBuilder builder = new StringBuilder();
    if (printBonus) {
      if (frame.isStrike()) {
        builder.append("X\t");
      } else if (frame.isSpare()) {
        builder.append(printSpare(frame));
      } else {
        frame.getRolls().stream().map(this::printRollScore).forEach(builder::append);
      }
      frame.getBonuses().stream().map(this::printRollScore).forEach(builder::append);
    } else if (frame.isStrike()) {
      builder.append("\tX\t");
    } else if (frame.isSpare()) {
      builder.append(printSpare(frame));
    } else {
      frame.getRolls().stream().map(this::printRollScore).forEach(builder::append);
    }
    return builder.toString();
  }

  private String frameLine(Score score) {
    StringBuilder builder = new StringBuilder();

    builder.append("Pinfalls\t");
    List<Frame> frames = score.getFrames();
    IntStream.range(0, frames.size()).mapToObj(index -> printFrame(frames.get(index), index == frames.size() - 1))
        .forEach(builder::append);

    return builder.toString();
  }

  private String scoreLine(Score score) {
    StringBuilder builder = new StringBuilder();

    builder.append("Score\t\t");
    score.getScoresByFrames().map(frameScore -> String.format("%d\t\t", frameScore)).forEach(builder::append);
    return builder.toString();
  }

  private void writePlayerScore(Player player) {
    Score score = player.getScore();

    appendLine(player.getName());
    appendLine(frameLine(score));
    appendLine(scoreLine(score));
  }

  /**
   * Writes the score into the {@link Appendable} provided in the constructor.
   */
  @Override
  public void write(Game game) {
    appendLine(writeHeader(10));
    game.getPlayers().forEach(this::writePlayerScore);
  }

}