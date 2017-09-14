package giftract.com.multilevelgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;

public class IntroActivity extends Activity {
    private Button btnNext;
    private Button btnSkip;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private int[] layouts;
    SharedPreferences sp;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    OnPageChangeListener viewPagerPageChangeListener = new C02491();

    class C02491 implements OnPageChangeListener {
        C02491() {
        }

        public void onPageSelected(int position) {
            IntroActivity.this.addBottomDots(position);
            if (position == IntroActivity.this.layouts.length - 1) {
                IntroActivity.this.btnNext.setText(IntroActivity.this.getString(C0185R.string.start));
                IntroActivity.this.btnSkip.setVisibility(8);
                IntroActivity.this.sp = IntroActivity.this.getSharedPreferences("Gift", 0);
                Editor edit = IntroActivity.this.sp.edit();
                edit.putInt("page", 1);
                edit.commit();
                return;
            }
            IntroActivity.this.btnNext.setText(IntroActivity.this.getString(C0185R.string.next));
            IntroActivity.this.btnSkip.setVisibility(0);
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public Object instantiateItem(ViewGroup container, int position) {
            this.layoutInflater = (LayoutInflater) IntroActivity.this.getSystemService("layout_inflater");
            View view = this.layoutInflater.inflate(IntroActivity.this.layouts[position], container, false);
            container.addView(view);
            return view;
        }

        public int getCount() {
            return IntroActivity.this.layouts.length;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(C0185R.layout.activity_intro);
        this.viewPager = (ViewPager) findViewById(C0185R.id.view_pager);
        this.dotsLayout = (LinearLayout) findViewById(C0185R.id.layoutDots);
        this.btnSkip = (Button) findViewById(C0185R.id.btn_skip);
        this.btnNext = (Button) findViewById(C0185R.id.btn_next);
        this.layouts = new int[]{C0185R.layout.slide1, C0185R.layout.slide2, C0185R.layout.slide3, C0185R.layout.slide4};
        addBottomDots(0);
        this.viewPagerAdapter = new ViewPagerAdapter();
        this.viewPager.setAdapter(this.viewPagerAdapter);
        this.viewPager.addOnPageChangeListener(this.viewPagerPageChangeListener);
    }

    public void btnSkipClick(View v) {
        this.sp = getSharedPreferences("Gift", 0);
        Editor edit = this.sp.edit();
        edit.putInt("page", 1);
        edit.commit();
        launchHomeScreen();
    }

    public void btnNextClick(View v) {
        int current = getItem(1);
        if (current < this.layouts.length) {
            this.viewPager.setCurrentItem(current);
        } else {
            launchHomeScreen();
        }
    }

    private void addBottomDots(int currentPage) {
        this.dots = new TextView[this.layouts.length];
        this.dotsLayout.removeAllViews();
        for (int i = 0; i < this.dots.length; i++) {
            this.dots[i] = new TextView(this);
            this.dots[i].setText(Html.fromHtml("&#8226;"));
            this.dots[i].setTextSize(35.0f);
            this.dots[i].setTextColor(Color.parseColor("#cccccc"));
            this.dotsLayout.addView(this.dots[i]);
        }
        if (this.dots.length > 0) {
            this.dots[currentPage].setTextColor(Color.parseColor("#000000"));
        }
    }

    private int getItem(int i) {
        return this.viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        startActivity(new Intent(this, LevelSelectorActivity.class));
        finish();
    }
}
