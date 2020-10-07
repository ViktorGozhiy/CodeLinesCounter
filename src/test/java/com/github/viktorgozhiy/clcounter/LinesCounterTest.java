package com.github.viktorgozhiy.clcounter;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class LinesCounterTest {

    @Test
    public void countLines() {
        String filePath = System.getProperty("user.dir") + "/src/main/java/com/github/viktorgozhiy/clcounter/LinesCounter.java";
        int expectedLines = 79;
        try {
            int countedLines = LinesCounter.countLines(filePath);
            assert expectedLines == countedLines;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}