package com.luizguilherme.bowling.cli;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class IntegrationTest {

  @Test
  public void shouldOutputScoreForAllFails() throws IOException {
    executeTest("all-fails.txt", "all-fails-expected.txt");
  }

  @Test
  public void shouldOutputScoreForSample() throws IOException {
    executeTest("sample-input.txt", "sample-expected.txt");
  }

  @Test
  public void shouldOutputScoreForPerfectGame() throws IOException {
    executeTest("perfect.txt", "perfect-expected.txt");
  }

  private void executeTest(String inputFilename, String expectedFilename) throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    String inputFile = classLoader.getResource(inputFilename).getFile();
    File output = File.createTempFile("output", "txt");

    App.main(new String[] { inputFile, output.getAbsolutePath() });

    File expected = new File(classLoader.getResource(expectedFilename).getFile());
    assertTrue(FileUtils.contentEquals(expected, output));
  }

}