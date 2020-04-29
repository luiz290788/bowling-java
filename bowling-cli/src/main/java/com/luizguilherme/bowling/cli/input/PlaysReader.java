package com.luizguilherme.bowling.cli.input;

import java.util.stream.Stream;

import com.luizguilherme.bowling.game.Play;
import com.luizguilherme.bowling.score.Roll;

/**
 * A reader of plays.
 */
public interface PlaysReader {

  static final String FAIL_STRING = "F";

  /**
   * Reads the plays and emits then in the returned stream.
   * 
   * @return a stream of plays.
   */
  Stream<Play> read();

  /**
   * Parses a line of the input into a play.
   * 
   * A line of the input must be the player name and the roll score separated by a
   * tab.
   * 
   * Example: "Jeff\t10"
   * 
   * @param line a line of the input
   * @return a play with a roll the name of the player that performed that roll.
   */
  default Play parseLine(String line) {
    String[] parts = line.split("\t");

    if (parts.length != 2) {
      throw new InvalidInputException(String.format("Line \"%s\" has more parts than expected", line));
    }

    Roll roll;

    if (FAIL_STRING.equals(parts[1])) {
      roll = Roll.FAILED_ROLL;
    } else {
      roll = new Roll(Integer.parseInt(parts[1]));
    }

    return new Play(parts[0], roll);
  }

  /**
   * Thrown when a line is invalid.
   */
  static final class InvalidInputException extends RuntimeException {
    private static final long serialVersionUID = -6800722888248181755L;

    public InvalidInputException(String message) {
      super(message);
    }
  }

}