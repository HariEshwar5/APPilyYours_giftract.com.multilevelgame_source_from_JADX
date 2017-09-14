package giftract.com.multilevelgame.Voucher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import giftract.com.multilevelgame.C0185R;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;

public class About_voucher extends AppCompatActivity implements OnClickListener {
    private ImageButton buttonPlay;
    String colour;
    MediaPlayer mp;
    MediaPlayer ring;

    class C01921 implements OnCompletionListener {
        C01921() {
        }

        public void onCompletion(MediaPlayer mp) {
            About_voucher.this.buttonPlay.setVisibility(0);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_about_voucher);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Gift", 0);
        Editor edit = sp.edit();
        int check = sp.getInt("page", 0);
        ((TextView) findViewById(C0185R.id.text_view_about_voucher)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/strawberrymuffins.ttf"));
        this.buttonPlay = (ImageButton) findViewById(C0185R.id.button_redeem_prize);
        this.buttonPlay.setVisibility(4);
        this.mp = MediaPlayer.create(this, C0185R.raw.end_music);
        this.mp.setOnCompletionListener(new C01921());
        this.mp.start();
        this.buttonPlay.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == this.buttonPlay) {
            startActivity(new Intent(this, Message_Grid.class));
            finish();
        }
    }

    public void onBackPressed() {
        if (this.mp != null) {
            this.mp.stop();
        }
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }
}
