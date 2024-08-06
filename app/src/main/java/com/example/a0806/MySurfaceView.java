package com.example.a0806;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Snowflake {
    int x, y, yInc;
    int diameter;
    static int WIDTH = 1300, HEIGHT = 2300;

    public Snowflake(int d) {
        this.diameter = d;
        x = (int) (Math.random() * (WIDTH - d));
        y = (int) (Math.random() * HEIGHT - HEIGHT);
        yInc = (int) (Math.random() * 10 + 1);
    }

    public void paint(Canvas g) {
        Paint paint = new Paint();

        if (y > HEIGHT) {
            y = 0;
            x = (int) (Math.random() * (WIDTH - diameter));
        }

        y += yInc;

        paint.setColor(Color.WHITE);
        g.drawCircle(x, y, diameter, paint);
    }
}

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public Snowflake[] snowflakes = new Snowflake[50];
    private MyThread thread;
    private Bitmap background;
    private Bitmap scaledBackground;

    public MySurfaceView(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);


        background = BitmapFactory.decodeResource(getResources(), R.drawable.snowback);

        thread = new MyThread(holder);

        for (int i = 0; i < snowflakes.length; i++) {

            int size = (int) (Math.random() * 15 + 5);
            snowflakes[i] = new Snowflake(size);
        }
    }

    public MyThread getThread() {
        return thread;
    }

    public void surfaceCreated(SurfaceHolder holder) {

        int width = getWidth();
        int height = getHeight();
        scaledBackground = Bitmap.createScaledBitmap(background, width, height, true);

        thread.setRunning(true);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        scaledBackground = Bitmap.createScaledBitmap(background, width, height, true);
    }

    public class MyThread extends Thread {
        private boolean mRun = false;
        private SurfaceHolder mSurfaceHolder;

        public MyThread(SurfaceHolder surfaceHolder) {
            mSurfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    if (c != null) {

                        c.drawBitmap(scaledBackground, 0, 0, null);
                        synchronized (mSurfaceHolder) {
                            for (Snowflake s : snowflakes) {
                                s.paint(c);
                            }
                        }
                    }
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }

            }
        }

        public void setRunning(boolean b) {
            mRun = b;
        }
    }
}
