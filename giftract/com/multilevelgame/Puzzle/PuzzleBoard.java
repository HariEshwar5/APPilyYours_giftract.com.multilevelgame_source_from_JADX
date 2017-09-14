package giftract.com.multilevelgame.Puzzle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

public class PuzzleBoard {
    private static final int[][] NEIGHBOUR_COORDS = new int[][]{new int[]{-1, 0}, new int[]{1, 0}, new int[]{0, -1}, new int[]{0, 1}};
    private static final int NUM_TILES = 4;
    private static final int UPPER_LIMIT = 15;
    private int parentWidth;
    private PuzzleBoard previousBoard;
    private int steps;
    private ArrayList<PuzzleTile> tiles;

    PuzzleBoard(Bitmap bitmap, int parentWidth) {
        int count = 0;
        this.tiles = new ArrayList();
        this.steps = 0;
        this.previousBoard = null;
        this.parentWidth = parentWidth;
        this.tiles.ensureCapacity(16);
        bitmap = Bitmap.createScaledBitmap(bitmap, parentWidth, parentWidth, true);
        for (int i = 0; i < 4; i++) {
            int j = 0;
            while (j < 4) {
                if (i == 3 && j == 3) {
                    this.tiles.add(null);
                } else {
                    this.tiles.add(new PuzzleTile(Bitmap.createBitmap(bitmap, (parentWidth / 4) * j, (parentWidth / 4) * i, parentWidth / 4, parentWidth / 4), count));
                    count++;
                }
                j++;
            }
        }
        Log.d("puzzleBoard:", "here");
    }

    PuzzleBoard(PuzzleBoard otherBoard) {
        this.tiles = (ArrayList) otherBoard.tiles.clone();
        this.steps = otherBoard.steps + 1;
        this.previousBoard = otherBoard;
    }

    public void reset() {
        this.steps = 0;
        this.previousBoard = null;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        return this.tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (this.tiles != null) {
            for (int i = 0; i < 16; i++) {
                PuzzleTile tile = (PuzzleTile) this.tiles.get(i);
                if (tile != null) {
                    tile.draw(canvas, i % 4, i / 4);
                }
            }
        }
    }

    public boolean click(float x, float y) {
        int i = 0;
        while (i < 16) {
            PuzzleTile tile = (PuzzleTile) this.tiles.get(i);
            if (tile != null && tile.isClicked(x, y, i % 4, i / 4)) {
                return tryMoving(i % 4, i / 4);
            }
            i++;
        }
        return false;
    }

    public boolean resolved() {
        int i = 0;
        while (i < 15) {
            PuzzleTile tile = (PuzzleTile) this.tiles.get(i);
            if (tile == null || tile.getNumber() != i) {
                return false;
            }
            i++;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return (y * 4) + x;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = (PuzzleTile) this.tiles.get(i);
        this.tiles.set(i, this.tiles.get(j));
        this.tiles.set(j, temp);
    }

    private boolean tryMoving(int tileX, int tileY) {
        int[][] iArr = NEIGHBOUR_COORDS;
        int length = iArr.length;
        int i = 0;
        while (i < length) {
            int[] delta = iArr[i];
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX < 0 || nullX >= 4 || nullY < 0 || nullY >= 4 || this.tiles.get(XYtoIndex(nullX, nullY)) != null) {
                i++;
            } else {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }
        }
        return false;
    }

    public int manhattan() {
        int dist = 0;
        int count = 0;
        Iterator<PuzzleTile> it = this.tiles.iterator();
        while (it.hasNext()) {
            PuzzleTile pt = (PuzzleTile) it.next();
            if (pt != null) {
                dist += Math.abs((pt.getNumber() % 4) - (count % 4)) + Math.abs((pt.getNumber() / 4) - (count / 4));
            }
            count++;
        }
        return dist;
    }

    public PuzzleBoard getPreviousBoard() {
        return this.previousBoard;
    }

    public ArrayList<PuzzleBoard> neighbours() {
        int indexOfNull = this.tiles.indexOf(null);
        ArrayList<PuzzleBoard> returnList = new ArrayList();
        int up = indexOfNull - 4;
        int down = indexOfNull + 4;
        int left = indexOfNull - 1;
        int right = indexOfNull + 1;
        if (up >= 0 && up <= 15) {
            PuzzleBoard copy = new PuzzleBoard(this);
            copy.swapTiles(up, indexOfNull);
            returnList.add(copy);
        }
        if (down <= 15 && down >= 0) {
            copy = new PuzzleBoard(this);
            copy.swapTiles(down, indexOfNull);
            returnList.add(copy);
        }
        if (left >= 0 && left <= 15 && indexOfNull % 4 != 0) {
            copy = new PuzzleBoard(this);
            copy.swapTiles(left, indexOfNull);
            returnList.add(copy);
        }
        if (right <= 15 && right >= 0 && (indexOfNull + 1) % 4 != 0) {
            copy = new PuzzleBoard(this);
            copy.swapTiles(right, indexOfNull);
            returnList.add(copy);
        }
        return returnList;
    }

    public int priority() {
        return this.steps + manhattan();
    }
}
