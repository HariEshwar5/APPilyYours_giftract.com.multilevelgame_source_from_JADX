package giftract.com.multilevelgame.basketGame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import giftract.com.multilevelgame.C0185R;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;

public class NextActivity extends AppCompatActivity implements OnClickListener {
    ImageButton redeem;
    int score;
    SharedPreferences sp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_next);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/strawberrymuffins.ttf");
        this.redeem = (ImageButton) findViewById(C0185R.id.button_collect_prize);
        this.redeem.setOnClickListener(this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            this.score = b.getInt("score");
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction("android.intent.action.SEND");
        sendIntent.putExtra("android.intent.extra.TEXT", "Hey Ritika! Here's another update. I have completed Stage 4 that is Basket game and my score is " + this.score);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    public void onClick(View v) {
        this.sp = getSharedPreferences("Gift", 0);
        Editor edit = this.sp.edit();
        edit.putInt("page", 5);
        edit.commit();
        if (!this.sp.getBoolean("flag_once_spin_basket_game", false)) {
            int level = this.sp.getInt("Highest Level", 1);
            edit.putInt("Highest Level", 5);
            edit.putBoolean("flag_once_spin_basket_game", true);
            edit.commit();
            Log.d("done writing", 5 + "");
        }
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }
}
