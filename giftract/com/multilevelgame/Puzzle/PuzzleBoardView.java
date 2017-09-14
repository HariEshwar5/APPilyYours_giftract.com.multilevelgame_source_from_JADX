package giftract.com.multilevelgame.Puzzle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

public class PuzzleBoardView extends View {
    public static final int NUM_SHUFFLE_STEPS = 40;
    private Activity activity;
    private ArrayList<PuzzleBoard> animation;
    private boolean isTouchable = true;
    private PuzzleBoard puzzleBoard;
    private Random random = new Random();
    SharedPreferences sp;

    class MyAsyncTask extends AsyncTask<String, String, String> {

        class C01821 implements Comparator<PuzzleBoard> {
            C01821() {
            }

            public int compare(PuzzleBoard lhs, PuzzleBoard rhs) {
                return lhs.priority() - rhs.priority();
            }
        }

        MyAsyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            if (PuzzleBoardView.this.puzzleBoard == null) {
                return null;
            }
            PriorityQueue<PuzzleBoard> pq = new NoDuplicates(11, new C01821());
            pq.add(PuzzleBoardView.this.puzzleBoard);
            int count_pq = 0;
            while (!pq.isEmpty()) {
                PuzzleBoard temp = (PuzzleBoard) pq.peek();
                pq.remove();
                count_pq++;
                Log.d("Inside PQ", count_pq + "");
                if (count_pq > 7000) {
                    break;
                } else if (temp.resolved()) {
                    ArrayList<PuzzleBoard> resultList = new ArrayList();
                    while (temp != null) {
                        resultList.add(temp);
                        temp = temp.getPreviousBoard();
                    }
                    Collections.reverse(resultList);
                    PuzzleBoardView.this.animation = resultList;
                    pq.clear();
                } else {
                    Iterator<PuzzleBoard> it = temp.neighbours().iterator();
                    while (it.hasNext()) {
                        PuzzleBoard tp = (PuzzleBoard) it.next();
                        if (!tp.equals(tp.getPreviousBoard())) {
                            pq.add(tp);
                        }
                    }
                }
            }
            if (count_pq > 7000) {
                return "not";
            }
            return "yes";
        }

        protected void onPostExecute(String s) {
            if (s == null) {
                PuzzleBoardView.this.invalidate();
            }
            if (s.equals("not")) {
                PuzzleBoardView.this.sp = PuzzleBoardView.this.activity.getSharedPreferences("Gift", 0);
                Editor edit = PuzzleBoardView.this.sp.edit();
                if (!PuzzleBoardView.this.sp.getBoolean("flag_once_puzzle", false)) {
                    int level = PuzzleBoardView.this.sp.getInt("Highest Level", 1);
                    edit.putInt("Highest Level", 3);
                    edit.putBoolean("flag_once_puzzle", true);
                    edit.commit();
                    Log.d("done writing", 3 + "");
                }
                Intent mainIntent = new Intent(PuzzleBoardView.this.activity, PuzzleNextActivity.class);
                mainIntent.putExtra("autosolve", 0);
                PuzzleBoardView.this.activity.startActivity(mainIntent);
                PuzzleBoardView.this.activity.finish();
            }
            PuzzleBoardView.this.invalidate();
        }
    }

    public class NoDuplicates<E> extends PriorityQueue<E> {
        public NoDuplicates(int i, Comparator<E> comparator) {
            super(i, comparator);
        }

        public boolean offer(E e) {
            if (super.contains(e)) {
                return false;
            }
            return super.offer(e);
        }
    }

    public boolean isTouchable() {
        return this.isTouchable;
    }

    public void setTouchable(boolean isTouchable) {
        this.isTouchable = isTouchable;
    }

    public PuzzleBoardView(Context context) {
        super(context);
        this.activity = (Activity) context;
        this.animation = null;
    }

    public void initialize(Bitmap imageBitmap, View parent) {
        this.puzzleBoard = new PuzzleBoard(imageBitmap, parent.getWidth());
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.puzzleBoard == null) {
            return;
        }
        if (this.animation == null || this.animation.size() <= 0) {
            this.puzzleBoard.draw(canvas);
            return;
        }
        this.puzzleBoard = (PuzzleBoard) this.animation.remove(0);
        this.puzzleBoard.draw(canvas);
        if (this.animation.size() == 0) {
            this.animation = null;
            this.puzzleBoard.reset();
            this.sp = this.activity.getSharedPreferences("Gift", 0);
            Editor edit = this.sp.edit();
            if (!this.sp.getBoolean("flag_once_puzzle", false)) {
                int level = this.sp.getInt("Highest Level", 1);
                edit.putInt("Highest Level", 3);
                edit.putBoolean("flag_once_puzzle", true);
                edit.commit();
                Log.d("done writing", 3 + "");
            }
            Intent mainIntent = new Intent(this.activity, PuzzleNextActivity.class);
            mainIntent.putExtra("autosolve", 0);
            this.activity.startActivity(mainIntent);
            this.activity.finish();
            return;
        }
        postInvalidateDelayed(500);
    }

    public void shuffle() {
        if (this.animation == null && this.puzzleBoard != null) {
            int random = new Random().nextInt(5) + 50;
            for (int i = 0; i < random; i++) {
                ArrayList<PuzzleBoard> arrayListBoards = this.puzzleBoard.neighbours();
                this.puzzleBoard = (PuzzleBoard) arrayListBoards.get(new Random().nextInt(arrayListBoards.size()));
            }
            invalidate();
            this.puzzleBoard.reset();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isTouchable) {
            return super.onTouchEvent(event);
        }
        if (this.animation == null && this.puzzleBoard != null) {
            switch (event.getAction()) {
                case 0:
                    if (this.puzzleBoard.click(event.getX(), event.getY())) {
                        invalidate();
                        if (!this.puzzleBoard.resolved()) {
                            return true;
                        }
                        Toast.makeText(this.activity, "Congratulations!", 1).show();
                        this.sp = this.activity.getSharedPreferences("Gift", 0);
                        Editor edit = this.sp.edit();
                        if (!this.sp.getBoolean("flag_once_puzzle", false)) {
                            int level = this.sp.getInt("Highest Level", 1);
                            edit.putInt("Highest Level", 3);
                            edit.putBoolean("flag_once_puzzle", true);
                            edit.commit();
                            Log.d("done writing", 3 + "");
                        }
                        Intent mainIntent = new Intent(this.activity, PuzzleNextActivity.class);
                        mainIntent.putExtra("autosolve", 1);
                        this.activity.startActivity(mainIntent);
                        this.activity.finish();
                        return true;
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    public void solve() {
        new MyAsyncTask().execute(new String[]{""});
    }
}
