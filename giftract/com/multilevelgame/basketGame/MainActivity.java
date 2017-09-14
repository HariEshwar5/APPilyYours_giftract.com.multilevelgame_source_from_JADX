package giftract.com.multilevelgame.basketGame;

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

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private ImageButton buttonPlay;
    private ImageButton buttonScore;
    String colour;
    MediaPlayer ring;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_main);
        ((TextView) findViewById(C0185R.id.text_view_about_basket)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/strawberrymuffins.ttf"));
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Gift", 0);
        Editor edit = sp.edit();
        if (sp.getInt("page", 0) == 4) {
            this.buttonPlay = (ImageButton) findViewById(C0185R.id.buttonPlay);
            this.buttonPlay.setOnClickListener(this);
        } else {
            this.buttonPlay = (ImageButton) findViewById(C0185R.id.buttonPlay);
            this.buttonPlay.setOnClickListener(this);
        }
    }

    public void onClick(View v) {
        if (v == this.buttonPlay) {
            startActivity(new Intent(this, GameActivity.class));
            finish();
        }
        if (v != this.buttonScore) {
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }
}
