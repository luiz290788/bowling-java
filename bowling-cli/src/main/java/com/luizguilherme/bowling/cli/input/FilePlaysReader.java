package com.luizguilherme.bowling.cli.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.stream.Stream;

import com.luizguilherme.bowling.game.Play;

public class FilePlaysReader implements PlaysReader {

  private InputStreamPlaysReader inputStreamPlaysReader;

  public FilePlaysReader(String filePath) throws FileNotFoundException {
    this(new File(filePath));
  }

  public FilePlaysReader(File file) throws FileNotFoundException {
    inputStreamPlaysReader = new InputStreamPlaysReader(new FileInputStream(file));
  }

  @Override
  public Stream<Play> read() {
    return this.inputStreamPlaysReader.read();
  }

}