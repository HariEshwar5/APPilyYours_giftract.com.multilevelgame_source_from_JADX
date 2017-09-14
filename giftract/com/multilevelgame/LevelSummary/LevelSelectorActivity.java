package giftract.com.multilevelgame.LevelSummary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import giftract.com.multilevelgame.C0185R;
import giftract.com.multilevelgame.LevelSummary.SinglePageFragment.IOnPageFragmentInteractionListener;

public class LevelSelectorActivity extends AppCompatActivity implements IOnPageFragmentInteractionListener {
    FrameLayout rootFrameLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_level_selector);
        this.rootFrameLayout = (FrameLayout) findViewById(C0185R.id.root_frame_layout);
        this.rootFrameLayout.setSystemUiVisibility(4871);
        addLevelSelectorFragment();
    }

    private void addLevelSelectorFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(C0185R.id.fragment_container_layout_id) == null) {
            fm.beginTransaction().add(C0185R.id.fragment_container_layout_id, new SinglePageFragment(), "fragment").commit();
        }
    }

    public void onPageFragmentInteraction(Uri uri) {
    }

    public void onBackPressed() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        startActivity(intent);
        finish();
    }
}
