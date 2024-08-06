package com.example.a0806;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

class Dice extends View {
    private Bitmap[] diceImages;
    private Bitmap currentDice;
    private Random random;

    public Dice(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);

        diceImages = new Bitmap[]{
                BitmapFactory.decodeResource(getResources(), R.drawable.dice1),
                BitmapFactory.decodeResource(getResources(), R.drawable.dice2),
                BitmapFactory.decodeResource(getResources(), R.drawable.dice3),
                BitmapFactory.decodeResource(getResources(), R.drawable.dice4),
                BitmapFactory.decodeResource(getResources(), R.drawable.dice5),
                BitmapFactory.decodeResource(getResources(), R.drawable.dice6)
        };

        random = new Random();
        currentDice = diceImages[random.nextInt(diceImages.length)];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        // Center the dice image on the canvas
        int x = (canvas.getWidth() - currentDice.getWidth()) / 2;
        int y = (canvas.getHeight() - currentDice.getHeight()) / 2;
        canvas.drawBitmap(currentDice, x, y, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Change the dice image on touch
            currentDice = diceImages[random.nextInt(diceImages.length)];
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }
}

public class RandomDice extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Dice(this));
    }
}
