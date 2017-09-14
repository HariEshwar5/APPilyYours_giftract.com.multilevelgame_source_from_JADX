package giftract.com.multilevelgame.basketGame;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.gameView = new GameView(this, size.x, size.y);
        setContentView(this.gameView);
    }

    protected void onPause() {
        super.onPause();
        this.gameView.pause();
    }

    protected void onResume() {
        super.onResume();
        this.gameView.resume();
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }
}
