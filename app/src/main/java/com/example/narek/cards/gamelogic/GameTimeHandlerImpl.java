package com.example.narek.cards.gamelogic;

import android.os.Handler;
import android.os.Looper;


/**
 * Created by Narek on 3/12/16.
 */
public class GameTimeHandlerImpl implements GameTimer {

    private long timeTickIntervalInMillis;

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void startCountdown(final GameTimerCompleted timerCompleted) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (timerCompleted != null) timerCompleted.onTimerCompleted();
                startCountdown(timerCompleted);
            }
        }, timeTickIntervalInMillis);
    }

    @Override
    public void reset() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void setTimeTickInterval(long timeTickIntervalInMillis) {
        this.timeTickIntervalInMillis = timeTickIntervalInMillis;
        reset();
    }
}
