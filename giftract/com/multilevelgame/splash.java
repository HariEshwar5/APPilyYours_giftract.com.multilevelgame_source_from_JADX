 package giftract.com.multilevelgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;

public class splash extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_splash);
        final SharedPreferences sp = getApplicationContext().getSharedPreferences("Gift", 0);
        Editor edit = sp.edit();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (sp.getInt("page", 0) == 0) {
                    splash.this.startActivity(new Intent(splash.this, IntroActivity.class));
                    splash.this.finish();
                    return;
                }
                splash.this.startActivity(new Intent(splash.this, LevelSelectorActivity.class));
                splash.this.finish();
            }
        }, 2000);
    }
}
