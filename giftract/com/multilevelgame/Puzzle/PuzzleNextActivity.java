package giftract.com.multilevelgame.Puzzle;

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

public class PuzzleNextActivity extends AppCompatActivity {
    int autosolve;
    boolean flag = true;
    TextView message;
    Button next;
    SharedPreferences sp;

    class C01831 implements OnClickListener {
        C01831() {
        }

        public void onClick(View v) {
            PuzzleNextActivity.this.sp = PuzzleNextActivity.this.getSharedPreferences("Gift", 0);
            Editor edit = PuzzleNextActivity.this.sp.edit();
            edit.putInt("page", 2);
            edit.commit();
            if (PuzzleNextActivity.this.flag) {
                PuzzleNextActivity.this.next.setText("Unlock Next Challenge!");
                PuzzleNextActivity.this.message.setText("Congratulations. You're her knight in shining armour. Before she loads you with kisses and more, proceed to the next challenge.");
                Intent sendIntent;
                if (PuzzleNextActivity.this.autosolve == 0) {
                    PuzzleNextActivity.this.flag = false;
                    sendIntent = new Intent();
                    sendIntent.setAction("android.intent.action.SEND");
                    sendIntent.putExtra("android.intent.extra.TEXT", "I gave up and admitted you are the best! Proceeding to the next level now");
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.whatsapp");
                    PuzzleNextActivity.this.startActivity(sendIntent);
                    return;
                }
                PuzzleNextActivity.this.flag = false;
                sendIntent = new Intent();
                sendIntent.setAction("android.intent.action.SEND");
                sendIntent.putExtra("android.intent.extra.TEXT", "Fixed your perfect selfie in the puzzle game without any help! Proceeding to the next level now!");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                PuzzleNextActivity.this.startActivity(sendIntent);
                return;
            }
            int level = PuzzleNextActivity.this.sp.getInt("Highest Level", 1);
            if (!PuzzleNextActivity.this.sp.getBoolean("flag_once_quiz", false)) {
                edit.putInt("Highest Level", 2);
                edit.putBoolean("flag_once_quiz", true);
                edit.commit();
                Log.d("done writing", 2 + "");
            }
            PuzzleNextActivity.this.startActivity(new Intent(PuzzleNextActivity.this, LevelSelectorActivity.class));
            PuzzleNextActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_puzzle_next);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/strawberrymuffins.ttf");
        this.autosolve = getIntent().getIntExtra("autosolve", 0);
        this.message = (TextView) findViewById(C0185R.id.message_puzzle);
        this.next = (Button) findViewById(C0185R.id.button_puzzle_done);
        this.message.setTypeface(typeFace);
        this.message.setText("Whatsapp Ritika to proceed.\n P.S. She wants to know if you used autosolve and cheated!");
        this.next.setTypeface(typeFace);
        this.next.setText("WhatsApp Ritika My Score");
        this.next.setOnClickListener(new C01831());
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }
}
