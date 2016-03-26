package com.example.narek.cards;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class CardView extends View {
    float someOtherVariable;
    int[][] backgroundColors = new int[4][4];
    private int rowCount = 4;
    private List<ShapeDrawable> shapeDrawables = new ArrayList<>();
    private boolean needUpdate = true;
//    List<Integer> colors;
    OnSelTapListener onSelTapListener;

    public void setOnSelTapListener(OnSelTapListener onSelTapListener) {
        this.onSelTapListener = onSelTapListener;
    }

    public static final String TAG = "CARDVIEW: ";

    private GestureDetectorCompat gestureDetector;
    private int padding = 10;


    public interface OnSelTapListener {
        public void onSelTapped(int rowIndex, int colIndex);
    }


    public void setBackgroundColors(@NonNull int[][] backgroundColors) {
        this.rowCount = backgroundColors.length;

        for (int i = 0; i < backgroundColors[0].length; i++) {
//            backgroundColors = new int[backgroundColors[0].length][backgroundColors.length];
            System.arraycopy(backgroundColors[i], 0, this.backgroundColors[i], 0, backgroundColors.length);
        }


        invalidateCellDrawables();

        invalidate();
    }

    private void invalidateCellDrawables() {
        needUpdate = true;
    }





    public CardView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes

        gestureDetector = new GestureDetectorCompat(getContext(), new TapRecognizer());
//        gestureDetector = new GestureDetectorCompat(getContext(), this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        System.out.println("OnDraw");

        if (needUpdate) {
            recreateBoard();
            needUpdate = false;
        }

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                ShapeDrawable cur = shapeDrawables.get(i * rowCount + j);

                cur.draw(canvas);

            }
        }


    }



    private int contentWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int contentHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    private int startX() {
        int min = Math.min(contentWidth(), contentHeight());
        return  (int) ((contentWidth() - min) * 0.5f) + getPaddingLeft();
    }

    private int startY() {
        int min = Math.min(contentWidth(), contentHeight());
        return  (int) ((contentHeight() - min) * 0.5f) + getPaddingTop();
    }

    private void recreateBoard() {
        Log.d(TAG, "start x: " + startX() + " start y: " + startY());


        int cellWidth = cellWidth();
        shapeDrawables.clear();
//        makeBackgrounds();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
                shapeDrawable.getPaint().setColor(backgroundColors[i][j]);

                int x = startX() + (i + 1) * padding + cellWidth * i;
                int y = startY() + (j + 1) * padding + cellWidth * j;
                shapeDrawable.setBounds(x, y, x + cellWidth, y + cellWidth);
                shapeDrawables.add(shapeDrawable);
            }
        }
    }

    private int cellWidth() {
        int min = Math.min(contentWidth(), contentHeight());
        return (min - (rowCount + 1) * padding) / rowCount;
    }

    Pair<Integer, Integer> cellIndexAt(int x, int y) {
        int startX = (int) (startX() - padding * 0.5f);
        int startY = (int) (startY() - padding * 0.5f);
        int rowIndex = (x - startX) / (cellWidth() + padding);
        int colIndex = (y - startY) / (cellWidth() + padding);
        Log.d(TAG, "getCellIndexAt: (rowIndex, colIndex), (" + rowIndex + ", " + colIndex + ")");

        return rowIndex >=4 || rowIndex < 0 || colIndex >= 4 || colIndex < 0 ? null : new Pair<>(rowIndex, colIndex);
    }
    private class TapRecognizer extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp: onSingleTapUp");
            int touchX = (int) e.getX();
            int touchY = (int) e.getY();
            Log.d(TAG, "onSingleTapUp: (x, y) " + touchX + " " + touchY);

            int rowX = (int) e.getRawX();
            int rowY = (int) e.getRawY();

            Log.d(TAG, "onSingleTapUp: raw (x, y) " + rowX + " " + rowY);

            Pair<Integer,Integer> cur = cellIndexAt(touchX, touchY);

            if (cur != null && onSelTapListener != null) {
                onSelTapListener.onSelTapped(cur.first,cur.second);
            }

            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return true;
        }


    }

}
