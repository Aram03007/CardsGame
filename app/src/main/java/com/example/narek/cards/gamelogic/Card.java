package com.example.narek.cards.gamelogic;

import android.support.annotation.NonNull;

/**
 * Created by Narek on 1/31/16.
 */
public class Card {

    public enum CardState {
        CLOSED, OPENTEMP, OPENSOLVED,
    }

    private CardState cardState = CardState.CLOSED;
    private Object data;

    public Object getData() {
        return data;
    }

    public Card(CardState cardState, @NonNull Object data) {
        this.cardState = cardState;
        this.data = data;
    }

    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }

    public CardState getCardState() {
        return cardState;
    }

    public Card copy() {
        return new Card(this.cardState, this.data);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card)) {
            return false;
        }

        return data.equals(((Card) o).getData());
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
