package com.example.testproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;


public class SnowFallView extends View {
    private static final int SNOWFLAKES_NUMBER = 150;
    private final ArrayList<Snowflake> snowflakes = new ArrayList<>();
    private final Paint paint = new Paint();
    private final Random random = new Random();

    private class Snowflake {
        private float x;
        private float y;
        private float size;
        private float speed;

        public Snowflake() {
            reset();
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getSize() {
            return size;
        }

        public void move() {
            y += speed;
            x += Math.sin(y / 50) * 2;

            if (this.y > getHeight()) {
                this.reset();
            }
        }

        public void reset() {
            y = -random.nextFloat() * getHeight() / 8;
            x = random.nextFloat() * getWidth();
            size = random.nextFloat() * 10 + 10;
            speed = random.nextFloat() * 5 + 2;
        }
    }

    public SnowFallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(180);

        this.addSnowflakes();
    }

    private void addSnowflakes() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                snowflakes.add(new Snowflake());
                invalidate();

                if (snowflakes.size() < SNOWFLAKES_NUMBER) {
                    postDelayed(this, 100);
                }
            }
        }, 100);
    }

    private void moveSnowflakes() {
        for (Snowflake snowflake : snowflakes) {
            snowflake.move();
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        for (Snowflake snowflake : snowflakes) {
            canvas.drawCircle(snowflake.getX(), snowflake.getY(), snowflake.getSize(), paint);
        }

        moveSnowflakes();
        invalidate();
    }
}
