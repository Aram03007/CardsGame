package com.example.narek.cards.gamelogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.example.narek.cards.gamelogic.Card.CardState;

/**
 * Created by Narek on 1/31/16.
 */
public class Game {

    private Board board;
    private Complexity complexity;
    private GameState gameState;
    private TimerFactory timerFactory;
    private GameStateChanged stateChangedListener;
    private GameTimer gameTimer;
//    private GameTimer gameTimer2;




    public interface GameStateChanged {
        public void onStateChanged(GameState newState);
    }

    public interface CardProducer {
        public Object[] newCards(int count);
    }

    public void setStateChangedListener(GameStateChanged stateChangedListener) {
        this.stateChangedListener = stateChangedListener;
    }
    public GameState gameState() {
        if (gameState == null) {
            gameState = new GameState();
        }
        return gameState;
    }

    public Game(Complexity complexity, TimerFactory timerFactory, CardProducer cardProducer) {
        this.complexity = complexity;
        Object[] uniqueCards = cardProducer.newCards(complexity.getUniqueCardCount());

        List<Object> cardsDataList = new ArrayList<Object>(complexity.getRowCount() * complexity.getColCount());
        for (int i = 0, k = 0; i < complexity.getColCount() * complexity.getRowCount() * 0.5; i++, k++) {
            if (k >= uniqueCards.length) {
                k = 0;
            }
            Object curCard = uniqueCards[k];

            cardsDataList.add(curCard);
            cardsDataList.add(curCard);
        }

        Collections.shuffle(cardsDataList);

        Object[] cardsDataArray = new Object[cardsDataList.size()];
        cardsDataList.toArray(cardsDataArray);
        board = new Board(this.complexity.getRowCount(),
                this.complexity.getColCount(),
                cardsDataArray);

        this.timerFactory = timerFactory;
        this.gameTimer = timerFactory.create();
//        this.gameTimer2 = timerFactory.create();

        this.gameTimer.setTimeTickInterval(this.complexity.getVersionTime() * 1000);

        gameState().cards = board.getCards();
    }

    public CardState openCard(int rowIndex, int colIndex) {
        if (gameState().cards[rowIndex][colIndex].getCardState() == CardState.OPENSOLVED) {
            return gameState().cards[rowIndex][colIndex].getCardState();
        }



        gameTimer.reset();
        CardState cardState = board.setTempOpenCard(rowIndex, colIndex);

        if (cardState == CardState.OPENTEMP) {
            gameTimer.startCountdown(new GameTimerCompleted() {
                @Override
                public void onTimerCompleted() {
                    board.closeTempOpenCard();
                    gameState.cards = board.getCards();
                    if (stateChangedListener != null)
                        stateChangedListener.onStateChanged(gameState());
                }
            });
        }
        gameState().setTryCount(gameState().getTryCount() + 1);
        gameState().setCurTempVisibleCard(null);
        if (cardState == CardState.OPENTEMP) {
            gameState().setCurTempVisibleCard(board.tempCard());
        }
        if (cardState == CardState.OPENSOLVED) {
            gameState().setOpenedCardCount(gameState().getOpenedCardCount() + 2);
        }
        gameState().cards = board.getCards();
        return cardState;
    }

}
