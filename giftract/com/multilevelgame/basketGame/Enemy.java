package giftract.com.multilevelgame.basketGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import giftract.com.multilevelgame.C0185R;
import java.util.Random;

public class Enemy {
    private Bitmap bitmap;
    private Rect detectCollision;
    private int maxX;
    private int maxY;
    private int minX;
    private int minY;
    private int speed = 0;
    private int f11x;
    private int f12y;

    public Enemy(Context context, int screenX, int screenY) {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), C0185R.drawable.enemy);
        this.maxX = screenX;
        this.maxY = screenY;
        this.minX = 0;
        this.minY = 0;
        Random generator = new Random();
        this.speed = generator.nextInt(10) + 5;
        this.f12y = 0;
        this.f11x = generator.nextInt(this.maxX - this.bitmap.getWidth());
        this.detectCollision = new Rect(this.f11x, this.f12y, this.bitmap.getWidth(), this.bitmap.getHeight());
    }

    public void update(boolean flag) {
        this.f12y += this.speed;
        if (flag) {
            Random gen = new Random();
            this.speed = gen.nextInt(10) + 5;
            this.f12y = 0;
            this.f11x = gen.nextInt(this.maxX - this.bitmap.getWidth());
        }
        this.detectCollision.left = this.f11x;
        this.detectCollision.top = this.f12y;
        this.detectCollision.right = this.f11x + this.bitmap.getWidth();
        this.detectCollision.bottom = this.f12y + this.bitmap.getHeight();
    }

    public void setX(int x) {
        this.f11x = x;
    }

    public void setY(int y) {
        this.f12y = y;
    }

    public Rect getDetectCollision() {
        return this.detectCollision;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public int getX() {
        return this.f11x;
    }

    public int getY() {
        return this.f12y;
    }

    public int getSpeed() {
        return this.speed;
    }
}
