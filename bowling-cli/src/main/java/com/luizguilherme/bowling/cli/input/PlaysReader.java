package com.luizguilherme.bowling.cli.input;

import java.util.stream.Stream;

import com.luizguilherme.bowling.game.Play;
import com.luizguilherme.bowling.score.Roll;

public interface PlaysReader {

  static final String FAIL_STRING = "F";

  Stream<Play> read();

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

  static final class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
      super(message);
    }
  }

}