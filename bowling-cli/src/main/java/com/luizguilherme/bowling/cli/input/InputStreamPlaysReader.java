package com.luizguilherme.bowling.cli.input;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import com.luizguilherme.bowling.game.Play;

/**
 * Reads plays from a {@link InputStream}.
 */
public class InputStreamPlaysReader implements PlaysReader {

  private InputStream inputStream;

  public InputStreamPlaysReader(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  @Override
  public Stream<Play> read() {
    return new BufferedReader(new InputStreamReader(inputStream)).lines().map(this::parseLine);
  }

}
