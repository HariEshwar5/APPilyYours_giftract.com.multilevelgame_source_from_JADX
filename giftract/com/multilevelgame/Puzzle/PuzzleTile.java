package giftract.com.multilevelgame.Puzzle;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class PuzzleTile {
    private Bitmap bitmap;
    private int number;

    public PuzzleTile(Bitmap bitmap, int number) {
        this.bitmap = bitmap;
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(this.bitmap, (float) (this.bitmap.getWidth() * x), (float) (this.bitmap.getHeight() * y), null);
    }

    public boolean isClicked(float clickX, float clickY, int tileX, int tileY) {
        return clickX >= ((float) (tileX * this.bitmap.getWidth())) && clickX < ((float) ((tileX + 1) * this.bitmap.getWidth())) && clickY >= ((float) (tileY * this.bitmap.getWidth())) && clickY < ((float) ((tileY + 1) * this.bitmap.getWidth()));
    }
}
