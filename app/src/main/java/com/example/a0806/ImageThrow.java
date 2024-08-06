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

class Image extends View {
    private Bitmap imageBitmap;
    private float currentX, currentY;
    private float offsetX, offsetY;
    private boolean isDragging;

    public Image(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);

        // Load the image
        imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dice1); // Replace with your image resource

        // Initialize position
        currentX = 100;
        currentY = 100;
        isDragging = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        // Draw the image at the current position
        canvas.drawBitmap(imageBitmap, currentX, currentY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Check if the touch is within the bounds of the image
                float touchX = event.getX();
                float touchY = event.getY();
                if (touchX >= currentX && touchX <= currentX + imageBitmap.getWidth() &&
                        touchY >= currentY && touchY <= currentY + imageBitmap.getHeight()) {
                    isDragging = true;
                    offsetX = touchX - currentX;
                    offsetY = touchY - currentY;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    // Update the position of the image
                    currentX = event.getX() - offsetX;
                    currentY = event.getY() - offsetY;
                    invalidate();
                }
                return true;
            case MotionEvent.ACTION_UP:
                isDragging = false;
                return true;
        }
        return super.onTouchEvent(event);
    }
}

public class ImageThrow extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Image(this));
    }
}
