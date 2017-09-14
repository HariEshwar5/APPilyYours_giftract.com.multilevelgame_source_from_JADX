package giftract.com.multilevelgame.SpinWheel;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import giftract.com.multilevelgame.C0185R;
import giftract.com.multilevelgame.Const;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;
import java.util.Random;

public class SpinWheel extends AppCompatActivity {
    int NUMBER_OF_SLICE = 6;
    int[] f24a = new int[]{0, 60, 120, 180, 240, 300};
    String colour;
    boolean flag = true;
    int f25p;
    Random f26r;
    MediaPlayer ring;
    SharedPreferences sp;
    Button spin;
    ImageView spinwheel;
    String[] val = new String[]{"Yellow", "Green", "Pink", "Blue", "Purple", "Orange"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_spin_wheel);
        this.spinwheel = (ImageView) findViewById(C0185R.id.spinwheel);
        this.spin = (Button) findViewById(C0185R.id.spin);
        this.f26r = new Random();
        this.f25p = this.f26r.nextInt(this.NUMBER_OF_SLICE);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Gift", 0);
        final Editor edit = sp.edit();
        int check = sp.getInt("page", 0);
        ((TextView) findViewById(C0185R.id.text_view_spin_wheel)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/strawberrymuffins.ttf"));
        if (check != 3) {
            this.spin.setOnClickListener(new OnClickListener() {

                class C01891 implements AnimationListener {
                    C01891() {
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        SpinWheel.this.spin.setVisibility(0);
                        SpinWheel.this.spin.setText("View My Task");
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                }

                public void onClick(View view) {
                    if (SpinWheel.this.flag) {
                        SpinWheel.this.spin.setVisibility(4);
                        RotateAnimation rotate = new RotateAnimation(0.0f, (float) (SpinWheel.this.f24a[SpinWheel.this.f25p] + 1800), 1, 0.5f, 1, 0.5f);
                        rotate.setFillAfter(true);
                        rotate.setDuration(5000);
                        rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                        rotate.setAnimationListener(new C01891());
                        SpinWheel.this.spinwheel.startAnimation(rotate);
                        SpinWheel.this.ring = MediaPlayer.create(SpinWheel.this.getApplicationContext(), C0185R.raw.spinning_wheel);
                        SpinWheel.this.ring.start();
                        SpinWheel.this.colour = SpinWheel.this.val[SpinWheel.this.f25p];
                        SpinWheel.this.flag = false;
                        edit.putString("SpinColor", SpinWheel.this.colour);
                        edit.putInt("SpinColor_positon", SpinWheel.this.f25p);
                        edit.commit();
                        return;
                    }
                    SpinWheel.this.startActivity(new Intent(SpinWheel.this, SpinTaskActivity.class));
                    SpinWheel.this.finish();
                }
            });
        } else {
            this.spin.setOnClickListener(/* anonymous class already generated */);
        }
    }

    private void dialogbox(String c) {
        Intent sendIntent = new Intent();
        sendIntent.setAction("android.intent.action.SEND");
        sendIntent.putExtra("android.intent.extra.TEXT", "I have completed the task - " + this.colour + " of Stage 3 that is Spin Wheel game.Please provide the password to proceed to the next level.");
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
        Builder dialogBuilder = new Builder(this);
        View dialogView = getLayoutInflater().inflate(C0185R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText pass = (EditText) dialogView.findViewById(C0185R.id.edit1);
        dialogBuilder.setTitle("Your colour is " + c);
        dialogBuilder.setMessage("Enter Password");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (pass.getText().toString().equals(Const.SPIN_PASS)) {
                    SpinWheel.this.sp = SpinWheel.this.getSharedPreferences("Gift", 0);
                    Editor edit = SpinWheel.this.sp.edit();
                    edit.putInt("page", 4);
                    edit.commit();
                    if (!SpinWheel.this.sp.getBoolean("flag_once_spin_wheel", false)) {
                        int level = SpinWheel.this.sp.getInt("Highest Level", 1);
                        edit.putInt("Highest Level", 4);
                        edit.putBoolean("flag_once_spin_wheel", true);
                        edit.commit();
                        Log.d("done writing", 4 + "");
                        edit.putString("Spin_Color", SpinWheel.this.colour);
                        edit.commit();
                    }
                    SpinWheel.this.startActivity(new Intent(SpinWheel.this.getApplicationContext(), LevelSelectorActivity.class));
                    SpinWheel.this.finish();
                    return;
                }
                Toast.makeText(SpinWheel.this, "Incorrect Password", 0).show();
            }
        });
        dialogBuilder.create().show();
    }

    public void onBackPressed() {
        if (this.ring != null) {
            this.ring.stop();
        }
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }
}
