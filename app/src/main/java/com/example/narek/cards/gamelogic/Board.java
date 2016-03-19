package com.example.narek.cards.gamelogic;


/**
 * Created by Narek on 1/31/16.
 */
public class Board {

    private Card[][] cards;

    public Board(int rowCount, int colCount, Object[] cardDataList) {
        this.cards = new Card[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                cards[i][j] = new Card(Card.CardState.CLOSED, cardDataList[i * rowCount + j]);
            }
        }
    }

    public Board(Card[][] cards) {
        this.cards = cards;
    }

    public Card.CardState setTempOpenCard(int row, int col) {
        Card tmpOpenCard = tempOpenCard();
        Card cur = cards[row][col];
        if (tmpOpenCard != null) {
            int[] curIndex = indexOfTempCard();
            assert curIndex != null;
            if (cur.equals(tmpOpenCard) && (curIndex[0] != row || curIndex[1] != col)) {
                cur.setCardState(Card.CardState.OPENSOLVED);
                tmpOpenCard.setCardState(Card.CardState.OPENSOLVED);
                return Card.CardState.OPENSOLVED;
            } else  {
                tmpOpenCard.setCardState(Card.CardState.CLOSED);
                return Card.CardState.CLOSED;
            }
        }else  {
            cur.setCardState(Card.CardState.OPENTEMP);
            return Card.CardState.OPENTEMP;
        }

    }

    private Card tempOpenCard(){
        for (Card[] cardss : cards) {
            for (Card card : cardss) {
                if (card.getCardState() == Card.CardState.OPENTEMP) {
                    return card;
                }
            }
        }
        return null;
    }

    public Card tempCard() {
        Card tmpCard = tempOpenCard();
        if (tmpCard != null) {
            return tmpCard.copy();
        }
        return null;
    }

    private int[] indexOfTempCard() {
        int rowIndex = -1;
        int colIndex = -1;
        for (Card[] cardss : cards) {
            rowIndex += 1;
            for (Card card : cardss) {
                colIndex += 1;
                if (card.getCardState() == Card.CardState.OPENTEMP) {
                    return new int[]{rowIndex, colIndex};
                }
            }
        }
        return null;
    }

    public boolean hasTempOpenCard() {
        return tempOpenCard() != null;
    }


    public void closeTempOpenCard() {
        Card tempOpenCard = tempOpenCard();
        if (tempOpenCard != null) {
            tempOpenCard.setCardState(Card.CardState.CLOSED);
        }
    }

    public Card[][] getCards() {
        Card[][] cardsCopy = new Card[cards.length][];
        for (int i = 0; i < cards.length; i++) {
            cardsCopy[i] = new Card[cards[0].length];
        }
        for (int i = 0; i < cards[0].length; i++) {
            for (int j = 0; j < cards.length; j++) {
                cardsCopy[i][j] = cards[i][j].copy();
            }
        }
        return cardsCopy;
    }
}
