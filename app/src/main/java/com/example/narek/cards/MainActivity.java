package com.example.narek.cards;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.narek.cards.gamelogic.Card;
import com.example.narek.cards.gamelogic.Complexity;
import com.example.narek.cards.gamelogic.Game;
import com.example.narek.cards.gamelogic.GameState;
import com.example.narek.cards.gamelogic.GameTimeHandlerImpl;
import com.example.narek.cards.gamelogic.GameTimer;
import com.example.narek.cards.gamelogic.TimerFactory;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main.Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_card_view);
        CardView cardView = (CardView) findViewById(R.id.card_view);



        final Game game = new Game(Complexity.NORMAL, new TimerFactory() {
            @Override
            public GameTimer create() {
                return new GameTimeHandlerImpl();
            }
        }, new Game.CardProducer() {
            @Override
            public Object[] newCards(int count) {
//                String[] cards = new String[count];
//                String latters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//                for (int i = 0; i < cards.length; i++) {
//                    cards[i] = latters.substring(i, i + 1);
//                }
//                return cards;
                Integer[] result = new Integer[count];

                int[] colors = new int[]{Color.BLUE, Color.GREEN, Color.GRAY,
                        Color.MAGENTA, Color.WHITE, Color.YELLOW, Color.CYAN,
                        Color.LTGRAY};
                for (int i = 0; i < count; i++) {
                    result[i] = colors[i];
                }
                return result;
            }
        });
        cardView.setOnSelTapListener(new CardView.OnSelTapListener() {
            @Override
            public void onSelTapped(int rowIndex, int colIndex) {
                Log.d(TAG, " taped");
                game.openCard(rowIndex, colIndex);
                redrawView(game.gameState());

            }
        });
        game.setStateChangedListener(new Game.GameStateChanged() {
            @Override
            public void onStateChanged(GameState newState) {
                redrawView(newState);
            }
        });
        GameState state = game.gameState();
        redrawView(state);
    }

    private void redrawView(GameState state) {
        Card[][] res = state.getCards();
        int[][] colors = new int[res[0].length][res.length];
        for (int i = 0; i < res[0].length; i++) {
            for (int j = 0; j < res.length; j++) {
                if (res[i][j].getCardState() == Card.CardState.CLOSED) {
                    colors[i][j] = Color.BLACK;
                } else {
                    colors[i][j] = (Integer) res[i][j].getData();
                }
            }
        }
        CardView cardView = (CardView) findViewById(R.id.card_view);
        cardView.setBackgroundColors(colors);


    }
}
