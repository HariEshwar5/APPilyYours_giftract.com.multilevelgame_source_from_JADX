package giftract.com.multilevelgame.SpinWheel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import giftract.com.multilevelgame.C0185R;
import giftract.com.multilevelgame.Const;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;

public class SpinTaskActivity extends AppCompatActivity {
    CountDownTimer f23a;
    int[] bgimage = new int[]{C0185R.drawable.yellow_spin_bg, C0185R.drawable.green_spin_bg, C0185R.drawable.pink_spin_bg, C0185R.drawable.blue_spin_bg, C0185R.drawable.purple_spin_bg, C0185R.drawable.orange_spin_bg};
    String color;
    TextView color_choosen_text;
    int color_position;
    TextView counter;
    Editor edit;
    boolean flag_button_toggle = true;
    MediaPlayer ring;
    Button share_whatsapp_button;
    SharedPreferences sp;

    class C01882 implements OnClickListener {
        C01882() {
        }

        public void onClick(View v) {
            if (SpinTaskActivity.this.flag_button_toggle) {
                Intent sendIntent = new Intent();
                sendIntent.setAction("android.intent.action.SEND");
                sendIntent.putExtra("android.intent.extra.TEXT", "The dare I got is \" " + Const.SPIN_TASK[SpinTaskActivity.this.color_position] + " \" I will do it within 24 hours (God promise) and then kill you later for making me do it. Give me the password so I can proceed.");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                SpinTaskActivity.this.startActivity(sendIntent);
                SpinTaskActivity.this.flag_button_toggle = false;
                SpinTaskActivity.this.sp = SpinTaskActivity.this.getSharedPreferences("Gift", 0);
                Editor edit = SpinTaskActivity.this.sp.edit();
                edit.putInt("page", 4);
                edit.commit();
                SpinTaskActivity.this.share_whatsapp_button.setText("Unlock Next Challenge!");
                SpinTaskActivity.this.color_choosen_text.setText("Woah! 80% people do not finish their task (we just made the statistics up).)");
                SpinTaskActivity.this.counter.setText("You are just 20.3% away from your surprise! (we made this up too)");
                if (!SpinTaskActivity.this.sp.getBoolean("flag_once_spin_wheel", false)) {
                    int level = SpinTaskActivity.this.sp.getInt("Highest Level", 1);
                    edit.putInt("Highest Level", 4);
                    edit.putBoolean("flag_once_spin_wheel", true);
                    edit.commit();
                    Log.d("done writing", 4 + "");
                    edit.putString("Spin_Color", SpinTaskActivity.this.color);
                    edit.commit();
                    return;
                }
                return;
            }
            Log.d("inside dialogBox", "yes " + SpinTaskActivity.this.color);
            Builder dialogBuilder = new Builder(SpinTaskActivity.this, C0185R.style.AppCompatAlertDialogStyle);
            View dialogView = SpinTaskActivity.this.getLayoutInflater().inflate(C0185R.layout.custom_dialog, null);
            dialogBuilder.setView(dialogView);
            final EditText pass = (EditText) dialogView.findViewById(C0185R.id.edit1);
            dialogBuilder.setTitle((CharSequence) "Enter Password to proceed");
            dialogBuilder.setMessage((CharSequence) "Enter the password Ritika gave you. (If she agreed to give you one already :p)");
            dialogBuilder.setPositiveButton((CharSequence) "Done", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (pass.getText().toString().toLowerCase().equals(Const.SPIN_PASS)) {
                        SpinTaskActivity.this.startActivity(new Intent(SpinTaskActivity.this.getApplicationContext(), LevelSelectorActivity.class));
                        SpinTaskActivity.this.finish();
                        return;
                    }
                    Toast.makeText(SpinTaskActivity.this, "Incorrect Password", 0).show();
                }
            });
            dialogBuilder.show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_spin_task);
        RelativeLayout view1 = (RelativeLayout) findViewById(C0185R.id.spintask);
        this.sp = getApplicationContext().getSharedPreferences("Gift", 0);
        this.edit = this.sp.edit();
        this.color = this.sp.getString("SpinColor", "Orange");
        this.color_position = this.sp.getInt("SpinColor_positon", 5);
        view1.setBackgroundResource(this.bgimage[this.color_position]);
        this.color_choosen_text = (TextView) findViewById(C0185R.id.text_view_color_choosen);
        this.counter = (TextView) findViewById(C0185R.id.text_view_ticker_message);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/strawberrymuffins.ttf");
        this.color_choosen_text.setTypeface(typeFace);
        this.counter.setTypeface(typeFace);
        this.counter.setGravity(17);
        this.share_whatsapp_button = (Button) findViewById(C0185R.id.spin_button_done);
        this.share_whatsapp_button.setTypeface(typeFace);
        this.share_whatsapp_button.setVisibility(4);
        this.color_choosen_text.setText("*Hold your breath*");
        this.f23a = new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                SpinTaskActivity.this.counter.setText("  And your dare is... " + (millisUntilFinished / 1000));
                SpinTaskActivity.this.ring = MediaPlayer.create(SpinTaskActivity.this.getApplicationContext(), C0185R.raw.tick);
                SpinTaskActivity.this.ring.start();
            }

            public void onFinish() {
                SpinTaskActivity.this.counter.setText("And your dare is... " + Const.SPIN_TASK[SpinTaskActivity.this.color_position]);
                SpinTaskActivity.this.share_whatsapp_button.setVisibility(0);
            }
        }.start();
        this.share_whatsapp_button.setOnClickListener(new C01882());
    }

    private void releaseMediaPlayer() {
        if (this.ring != null) {
            if (this.ring.isPlaying()) {
                this.ring.stop();
            }
            this.ring.release();
            this.ring = null;
        }
    }

    public void onBackPressed() {
        releaseMediaPlayer();
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }
}
