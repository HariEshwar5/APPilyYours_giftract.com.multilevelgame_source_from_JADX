package giftract.com.multilevelgame.basketGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import giftract.com.multilevelgame.C0185R;

public class Player {
    private Bitmap bitmap;
    private Rect detectCollision;
    private int maxY;
    private int minY;
    private int movement = 0;
    private int pos = 0;
    private int f13x;
    private int x1;
    private int f14y;

    public Player(Context context, int screenX, int screenY) {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), C0185R.drawable.player);
        this.x1 = screenX;
        this.pos = this.x1 / 2;
        this.f13x = (screenX / 2) - (this.bitmap.getWidth() / 2);
        this.f14y = screenY;
        this.maxY = screenY - this.bitmap.getHeight();
        this.minY = 0;
        this.detectCollision = new Rect(this.f13x, this.f14y, this.bitmap.getWidth(), this.bitmap.getHeight());
    }

    public void touchUpdate(int a, int b) {
        if (a > this.x1 / 2) {
            this.movement = 1;
        } else {
            this.movement = 2;
        }
    }

    public void update() {
        if (this.movement == 1) {
            this.pos += 20;
        } else if (this.movement == 2) {
            this.pos -= 20;
        } else {
            this.pos = this.f13x;
        }
        if (this.pos > this.x1 - this.bitmap.getWidth()) {
            this.pos = this.x1 - this.bitmap.getWidth();
        }
        if (this.pos < 1) {
            this.pos = 1;
        }
        this.f13x = this.pos;
        if (this.f14y < this.minY) {
            this.f14y = this.minY;
        }
        if (this.f14y > this.maxY) {
            this.f14y = this.maxY;
        }
        this.detectCollision.left = this.f13x;
        this.detectCollision.top = this.f14y;
        this.detectCollision.right = this.f13x + this.bitmap.getWidth();
        this.detectCollision.bottom = this.f14y + this.bitmap.getHeight();
    }

    public Rect getDetectCollision() {
        return this.detectCollision;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public int getX() {
        return this.f13x;
    }

    public int getY() {
        return this.f14y;
    }
}
