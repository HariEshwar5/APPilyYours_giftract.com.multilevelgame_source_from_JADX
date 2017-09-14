package giftract.com.multilevelgame.basketGame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import giftract.com.multilevelgame.C0185R;

public class GameView extends SurfaceView implements Runnable {
    int THRESHOLD_SCORE = 10;
    private Activity activity;
    private Boom boom;
    private Canvas canvas;
    Context context;
    private Enemy[] enemies;
    private int enemyCount = 3;
    boolean flag;
    int flag_starting = 0;
    private Thread gameThread = null;
    int[] highScore = new int[4];
    private boolean isGameOver;
    boolean low_score;
    MediaPlayer mp;
    private Paint paint;
    private Player player;
    boolean playing = false;
    boolean restart_game = false;
    int score;
    int screenX;
    int screenY;
    SharedPreferences sharedPreferences;
    private SurfaceHolder surfaceHolder;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.activity = (Activity) context;
        this.screenX = screenX;
        this.screenY = screenY;
        this.context = context;
        this.isGameOver = false;
        this.player = new Player(context, screenX, screenY);
        this.surfaceHolder = getHolder();
        this.paint = new Paint();
        this.paint.setTypeface(Typeface.createFromAsset(this.activity.getAssets(), "fonts/strawberrymuffins.ttf"));
        this.low_score = false;
        this.score = 0;
        this.playing = false;
        if (!this.restart_game) {
            this.sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME", 0);
            this.highScore[0] = this.sharedPreferences.getInt("score1", 0);
            this.highScore[1] = this.sharedPreferences.getInt("score2", 0);
            this.highScore[2] = this.sharedPreferences.getInt("score3", 0);
            this.highScore[3] = this.sharedPreferences.getInt("score4", 0);
        }
        this.enemies = new Enemy[this.enemyCount];
        for (int i = 0; i < this.enemyCount; i++) {
            this.enemies[i] = new Enemy(context, screenX, screenY);
        }
        this.boom = new Boom(context);
    }

    public void run() {
        while (this.playing) {
            Log.d("inside run", this.playing + "");
            update();
            draw();
            control();
        }
    }

    private void update() {
        this.player.update();
        this.boom.setX(-250);
        this.boom.setY(-250);
        int i = 0;
        while (i < this.enemyCount) {
            if (this.enemies[i].getY() == 0) {
                this.flag = true;
            }
            this.enemies[i].update(false);
            if (Rect.intersects(this.player.getDetectCollision(), this.enemies[i].getDetectCollision())) {
                this.mp = MediaPlayer.create(this.context, C0185R.raw.boom);
                this.mp.start();
                this.score++;
                this.boom.setX(this.enemies[i].getX());
                this.boom.setY(this.enemies[i].getY());
                this.enemies[i].setY(-200);
                this.enemies[i].update(true);
            } else if (this.flag && this.player.getDetectCollision().exactCenterY() <= this.enemies[i].getDetectCollision().exactCenterY()) {
                this.flag = false;
                this.playing = false;
                if (this.score < this.THRESHOLD_SCORE) {
                    this.low_score = true;
                } else {
                    this.isGameOver = true;
                }
                if (!this.low_score && this.isGameOver) {
                    int m;
                    for (m = 0; m < 4; m++) {
                        if (this.highScore[m] < this.score) {
                            int finalI = m;
                            this.highScore[m] = this.score;
                            break;
                        }
                    }
                    Editor e = this.sharedPreferences.edit();
                    for (m = 0; m < 4; m++) {
                        e.putInt("score" + (m + 1), this.highScore[m]);
                    }
                    e.apply();
                }
            }
            i++;
        }
    }

    private void draw() {
        if (this.surfaceHolder.getSurface().isValid()) {
            this.canvas = this.surfaceHolder.lockCanvas();
            this.canvas.drawRGB(247, 234, 218);
            int yPos;
            if (this.flag_starting == 0) {
                this.playing = false;
                this.paint.setTextSize(70.0f);
                this.paint.setColor(ViewCompat.MEASURED_STATE_MASK);
                this.paint.setTextAlign(Align.CENTER);
                yPos = (int) (((float) (this.canvas.getHeight() / 2)) - ((this.paint.descent() + this.paint.ascent()) / 2.0f));
                this.canvas.drawText("Tap on the ", (float) (this.canvas.getWidth() / 2), (float) (yPos - 150), this.paint);
                this.canvas.drawText("left/right side of the ", (float) (this.canvas.getWidth() / 2), (float) (yPos - 75), this.paint);
                this.canvas.drawText("screen to make", (float) (this.canvas.getWidth() / 2), (float) yPos, this.paint);
                this.canvas.drawText("basket move.", (float) (this.canvas.getWidth() / 2), (float) (yPos + 75), this.paint);
                Log.d("inside draw", this.playing + " ");
                this.surfaceHolder.unlockCanvasAndPost(this.canvas);
                return;
            }
            this.paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.paint.setTextSize(40.0f);
            this.canvas.drawText("Score:" + this.score, 100.0f, 50.0f, this.paint);
            this.canvas.drawBitmap(this.player.getBitmap(), (float) this.player.getX(), (float) this.player.getY(), this.paint);
            for (int i = 0; i < this.enemyCount; i++) {
                this.canvas.drawBitmap(this.enemies[i].getBitmap(), (float) this.enemies[i].getX(), (float) this.enemies[i].getY(), this.paint);
            }
            this.canvas.drawBitmap(this.boom.getBitmap(), (float) this.boom.getX(), (float) this.boom.getY(), this.paint);
            if (this.isGameOver) {
                this.paint.setTextSize(70.0f);
                this.paint.setTextAlign(Align.CENTER);
                yPos = (int) (((float) (this.canvas.getHeight() / 2)) - ((this.paint.descent() + this.paint.ascent()) / 2.0f));
                this.canvas.drawText("Level Complete", (float) (this.canvas.getWidth() / 2), (float) (yPos - 75), this.paint);
                this.canvas.drawText("Whatsapp Ritika", (float) (this.canvas.getWidth() / 2), (float) (yPos + 75), this.paint);
                this.canvas.drawText("to proceed", (float) (this.canvas.getWidth() / 2), (float) (yPos + 150), this.paint);
            }
            if (this.low_score) {
                this.paint.setTextSize(75.0f);
                this.paint.setTextAlign(Align.CENTER);
                yPos = (int) (((float) (this.canvas.getHeight() / 2)) - ((this.paint.descent() + this.paint.ascent()) / 2.0f));
                this.canvas.drawText("Low Score...", (float) (this.canvas.getWidth() / 2), (float) (yPos - 75), this.paint);
                this.canvas.drawText("Play Again", (float) (this.canvas.getWidth() / 2), (float) (yPos + 75), this.paint);
            }
            this.surfaceHolder.unlockCanvasAndPost(this.canvas);
        }
    }

    private void control() {
        try {
            Thread thread = this.gameThread;
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        this.playing = false;
        try {
            this.gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        this.playing = true;
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        int y = (int) motionEvent.getY();
        this.player.touchUpdate((int) motionEvent.getX(), y);
        if (this.flag_starting == 0) {
            Log.d("inside on touch event", "flag_starting is zero");
            this.flag_starting = 1;
            this.flag = true;
            this.playing = true;
            this.low_score = false;
            this.isGameOver = false;
            this.restart_game = true;
            this.score = 0;
            for (i = 0; i < this.enemyCount; i++) {
                this.enemies[i].setY(0);
            }
            resume();
        }
        if (this.low_score && (this.screenY / 2) - 150 <= y && y <= (this.screenY / 2) + 150) {
            this.flag = true;
            this.playing = true;
            this.low_score = false;
            this.isGameOver = false;
            this.restart_game = true;
            this.score = 0;
            for (i = 0; i < this.enemyCount; i++) {
                this.enemies[i].setY(0);
            }
            resume();
        }
        if (this.isGameOver && (this.screenY / 2) - 150 <= y && y <= (this.screenY / 2) + 150) {
            Intent n = new Intent(this.context, NextActivity.class);
            n.putExtra("score", this.score);
            this.context.startActivity(n);
            this.activity.finish();
            System.exit(0);
        }
        return true;
    }
}
