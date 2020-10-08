package com.github.viktorgozhiy.clcounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Scanner;

public class Application {

    private static final FilenameFilter visibleFilesFilter = (file, s) -> !file.isHidden();

    public static void main(String[] args) {
        System.out.println("Enter path to *.java files:");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File or directory does not exists");
            return;
        }
        try {
            countLinesInFiles(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private static void countLinesInFiles(File file) throws FileNotFoundException {
        if (!file.isDirectory()) {
            if (!file.getName().endsWith(".java")) {
                System.out.println("Java source file must ends with .java");
                return;
            }
            System.out.println(file.getName() + ": " + LinesCounter.countLines(file));
        } else {
            Item rootItem = parseDirectoryAndCountLines(file);
            printItem(rootItem, 0);
        }
    }

    private static Item parseDirectoryAndCountLines(File file) throws FileNotFoundException {
        Item currentItem = new Item(file.getName());
        int lines = 0;
        File[] files = file.listFiles(visibleFilesFilter);
        if (files == null) return currentItem; // This check needs because sometimes file.listFile() can produce NPE
        for (File subFile : files) {
            if (subFile.isDirectory()) {
                Item folderItem = parseDirectoryAndCountLines(subFile);
                currentItem.getItemList().add(folderItem);
                lines += folderItem.getLinesCounter();
            } else {
                if (!subFile.getName().endsWith(".java")) continue;
                Item javaFileItem = new Item(subFile.getName());
                javaFileItem.setFolder(false);
                int linesCounter = LinesCounter.countLines(subFile);
                lines += linesCounter;
                javaFileItem.setLinesCounter(linesCounter);
                currentItem.getItemList().add(javaFileItem);
            }
        }
        currentItem.setLinesCounter(lines);
        return currentItem;
    }

    private static void printItem(Item item, int indent) {
        StringBuilder indentSb = new StringBuilder();
        for (int i = 0; i < indent; i++) indentSb.append(" ");
        System.out.println(indentSb.toString() + item.getName() + ": " + item.getLinesCounter());
        indentSb.append(" ");
        for (Item subItem : item.getItemList()) {
            if (subItem.isFolder()) printItem(subItem, indent + 1);
            else {
                System.out.println(indentSb.toString() + subItem.getName() + ": " + subItem.getLinesCounter());
            }
        }
    }
}
