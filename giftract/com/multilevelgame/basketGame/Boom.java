package giftract.com.multilevelgame.basketGame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import giftract.com.multilevelgame.C0185R;

public class Boom {
    private Bitmap bitmap;
    private int f9x = -150;
    private int f10y = -150;

    public Boom(Context context) {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), C0185R.drawable.boomicon);
    }

    public void setX(int x) {
        this.f9x = x;
    }

    public void setY(int y) {
        this.f10y = y;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return this.f9x;
    }

    public int getY() {
        return this.f10y;
    }
}
