package giftract.com.multilevelgame.Puzzle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import giftract.com.multilevelgame.C0185R;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;

public class MainActivityPuzzle extends AppCompatActivity implements OnClickListener {
    private ImageButton buttonPlay;
    String colour;
    MediaPlayer ring;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_main_puzzle);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Gift", 0);
        Editor edit = sp.edit();
        int check = sp.getInt("page", 0);
        ((TextView) findViewById(C0185R.id.text_view_about_puzzle)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/strawberrymuffins.ttf"));
        this.buttonPlay = (ImageButton) findViewById(C0185R.id.buttonPlay_puzzle);
        this.buttonPlay.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == this.buttonPlay) {
            startActivity(new Intent(this, PuzzleActivity.class));
            finish();
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }
}
