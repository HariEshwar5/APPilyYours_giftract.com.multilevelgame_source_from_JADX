package giftract.com.multilevelgame.basketGame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import giftract.com.multilevelgame.C0185R;

public class HighScore extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_high_score);
        this.textView = (TextView) findViewById(C0185R.id.textView);
        this.textView2 = (TextView) findViewById(C0185R.id.textView2);
        this.textView3 = (TextView) findViewById(C0185R.id.textView3);
        this.textView4 = (TextView) findViewById(C0185R.id.textView4);
        this.sharedPreferences = getSharedPreferences("SHAR_PREF_NAME", 0);
        this.textView.setText("1." + this.sharedPreferences.getInt("score1", 0));
        this.textView2.setText("2." + this.sharedPreferences.getInt("score2", 0));
        this.textView3.setText("3." + this.sharedPreferences.getInt("score3", 0));
        this.textView4.setText("4." + this.sharedPreferences.getInt("score4", 0));
    }
}
