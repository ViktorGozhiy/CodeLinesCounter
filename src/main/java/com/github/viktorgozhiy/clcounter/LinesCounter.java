package com.github.viktorgozhiy.clcounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LinesCounter {

    public static int countLines(File file) throws FileNotFoundException {
        int lineCount = 0;
        boolean commentBegan = false;
        String line;

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();
            if (line.isEmpty() || (line.startsWith("//") && !commentBegan)) continue;
            if (commentBegan) {
                if (commentEnded(line)) {
                    line = line.substring(line.indexOf("*/") + 2).trim();
                    commentBegan = false;
                    if (line.isEmpty() || line.startsWith("//")) continue;
                } else continue;
            }
            if (isSourceCode(line))
                lineCount ++;
            if (commentBegan(line)) commentBegan = true;
        }
        scanner.close();
        return lineCount;
    }

    private static boolean commentBegan(String line) {
        int index = line.indexOf("/*");
        if (index < 0) return false;
        int quoteStartIndex = line.indexOf("\"");
        if (quoteStartIndex != -1 && quoteStartIndex < index) {
            while (quoteStartIndex > -1) {
                line = line.substring(quoteStartIndex + 1);
                int quoteEndIndex = line.indexOf("\"");
                line = line.substring(quoteEndIndex + 1);
                quoteStartIndex = line.indexOf("\"");
            }
            return commentBegan(line);
        }
        return !commentEnded(line.substring(index + 2));
    }

    private static boolean commentEnded(String line) {
        int index = line.indexOf("*/");
        if (index < 0) return false;
        else {
            String subString = line.substring(index + 2).trim();
            if (subString.isEmpty() || subString.startsWith("//")) return true;
            return !commentBegan(subString);
        }
    }

    private static boolean isSourceCode(String line) {
        line = line.trim();
        if (line.isEmpty() || line.startsWith("//")) return false;
        if (line.length() == 1) return true;
        int index = line.indexOf("/*");
        if (index != 0) return true;
        else {
            while (line.length() > 0) {
                line = line.substring(index + 2);
                int endCommentPosition = line.indexOf("*/");
                if (endCommentPosition < 0) return false;
                if (endCommentPosition == line.length() - 2) return false;
                else {
                    String subString = line.substring(endCommentPosition + 2).trim();
                    if (subString.isEmpty() || subString.indexOf("//") == 0) return false;
                    else {
                        if (subString.startsWith("/*")) {
                            line = subString;
                            continue;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
