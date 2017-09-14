package giftract.com.multilevelgame.Quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import giftract.com.multilevelgame.C0185R;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;

public class ResultActivity extends AppCompatActivity implements OnClickListener {
    TextView f21c;
    int correct;
    boolean flag = true;
    Button next;
    TextView score;
    SharedPreferences sp;
    TextView f22w;
    int wrong;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_result);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/strawberrymuffins.ttf");
        Intent i = getIntent();
        this.correct = i.getIntExtra("correct", 0);
        this.wrong = i.getIntExtra("wrong", 0);
        this.f21c = (TextView) findViewById(C0185R.id.correct);
        this.f22w = (TextView) findViewById(C0185R.id.wrong);
        this.score = (TextView) findViewById(C0185R.id.score);
        this.next = (Button) findViewById(C0185R.id.next);
        this.f21c.setTypeface(typeFace);
        this.f22w.setTypeface(typeFace);
        this.score.setTypeface(typeFace);
        this.f21c.append("" + this.correct);
        this.f22w.append("" + this.wrong);
        this.next.setTypeface(typeFace);
        this.next.setText("WhatsApp Ritika My Score");
        this.next.setOnClickListener(this);
    }

    public void onClick(View v) {
        this.sp = getSharedPreferences("Gift", 0);
        Editor edit = this.sp.edit();
        edit.putInt("page", 2);
        edit.commit();
        this.next.setText("Unlock Next Challenge!");
        if (this.flag) {
            this.flag = false;
            this.f21c.setText("Well done, you! You have passed the most important exam in your life after Boards.");
            this.score.setVisibility(4);
            this.f22w.setVisibility(4);
            Intent sendIntent = new Intent();
            sendIntent.setAction("android.intent.action.SEND");
            sendIntent.putExtra("android.intent.extra.TEXT", "I tested how well I know you at Level 1 and answered " + this.correct + " questions correctly and " + this.wrong + " questions incorrectly.");
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
            return;
        }
        int level = this.sp.getInt("Highest Level", 1);
        if (!this.sp.getBoolean("flag_once_quiz", false)) {
            edit.putInt("Highest Level", 2);
            edit.putBoolean("flag_once_quiz", true);
            edit.commit();
            Log.d("done writing", 2 + "");
        }
        startActivity(new Intent(this, LevelSelectorActivity.class));
        finish();
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }
}
