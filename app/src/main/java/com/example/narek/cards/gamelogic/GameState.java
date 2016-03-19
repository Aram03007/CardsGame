package com.example.narek.cards.gamelogic;

/**
 * Created by Narek on 1/31/16.
 */
public class GameState {
    private int tryCount;
    Card[][] cards;


    public int getOpenedCardCount() {
        return openedCardCount;
    }

    public void setOpenedCardCount(int openedCardCount) {
        this.openedCardCount = openedCardCount;
    }

    private int openedCardCount;
    private long timePassed;
    private Card curTempVisibleCard;

    private long gameStartTime;

    public Card[][] getCards() {
        return cards;
    }


    public GameState() {
        gameStartTime = System.currentTimeMillis();
    }

    public long getTimePassed(){
        return System.currentTimeMillis() - gameStartTime;
    }

    public Card getCurTempVisibleCard() {
        return curTempVisibleCard;
    }

    public void setCurTempVisibleCard(Card curTempVisibleCard) {
        this.curTempVisibleCard = curTempVisibleCard;
    }

    public int getTryCount() {
        return tryCount;
    }

    public void setTryCount(int tryCount) {
        this.tryCount = tryCount;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "tryCount=" + tryCount +
                ", openedCardCount=" + openedCardCount +
                ", timePassed=" + timePassed +
                ", curTempVisibleCard=" + curTempVisibleCard +
                ", gameStartTime=" + gameStartTime +
                '}';
    }
}
