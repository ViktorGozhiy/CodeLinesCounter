package com.github.viktorgozhiy.clcounter;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class LinesCounterTest {

    @Test
    public void countLines() {
        File file = new File("src/main/java/com/github/viktorgozhiy/clcounter/Application.java");
        int countLines = 0;
        try {
            countLines = LinesCounter.countLines(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        assert countLines == 70;
    }
}