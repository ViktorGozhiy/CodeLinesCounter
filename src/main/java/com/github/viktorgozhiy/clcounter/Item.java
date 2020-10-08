package com.github.viktorgozhiy.clcounter;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private boolean isFolder = true;
    private List<Item> itemList = new ArrayList<>();
    private int linesCounter = 0;
    private String name = "";

    public Item() {}

    public Item(String name) {
        this.name = name;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }

    public List<Item> getItemList() {
        itemList.sort((item, t1) -> (item.isFolder() == t1.isFolder() ? 1 : 0));
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public int getLinesCounter() {
        return linesCounter;
    }

    public void setLinesCounter(int linesCounter) {
        this.linesCounter = linesCounter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
