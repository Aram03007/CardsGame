package com.example.narek.cards.gamelogic;

/**
 * Created by Narek on 1/31/16.
 */
public enum Complexity {
    EASY(5, 2, 2, 2),
    NORMAL(10, 4, 4, 8),
    HARD(1, 4, 4, 8);

    private int visionTime;
    private int colCount;
    private int rowCount;
    private int uniqueCardCount;

    Complexity(int versionTime, int colCount, int rowCount, int uniqueCardCount) {
        this.visionTime = versionTime;
        this.colCount = colCount;
        this.rowCount = rowCount;
        this.uniqueCardCount = uniqueCardCount;
    }

    public int getVersionTime() {
        return visionTime;
    }

    public int getColCount() {
        return colCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getUniqueCardCount() {
        return uniqueCardCount;
    }
}
