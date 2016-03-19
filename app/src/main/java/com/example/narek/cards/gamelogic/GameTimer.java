package com.example.narek.cards.gamelogic;

public interface GameTimer {
    public void startCountdown(GameTimerCompleted timerCompleted);
    public void reset();
    public void setTimeTickInterval(long timeTickIntervalInMillis);
}
