package giftract.com.multilevelgame.LevelSummary;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import giftract.com.multilevelgame.C0185R;

public class CustomButton extends FrameLayout {
    ImageView imageView;
    int levelNumber = 0;
    public IOnButtonClicked mListener2;
    Rect rect = null;
    TextView textView;

    class C01761 implements OnTouchListener {
        C01761() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            int eventType = event.getAction();
            if (eventType == 0) {
                if (CustomButton.this.getScaleX() == 1.0f) {
                    CustomButton.this.rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    CustomButton.this.setScaleX(0.8f);
                    CustomButton.this.setScaleY(0.8f);
                }
            } else if (eventType == 1) {
                if (CustomButton.this.getScaleY() == 0.8f) {
                    CustomButton.this.setScaleX(1.0f);
                    CustomButton.this.setScaleY(1.0f);
                    Log.d("XXX", "onTouch: button Clicked");
                    if (CustomButton.this.mListener2 != null) {
                        CustomButton.this.mListener2.onLoadLevel();
                    }
                }
            } else if (eventType == 2) {
                if (CustomButton.this.getScaleY() == 0.8f && !CustomButton.this.rect.contains(v.getLeft() + ((int) event.getX()), v.getTop() + ((int) event.getY()))) {
                    CustomButton.this.setScaleX(1.0f);
                    CustomButton.this.setScaleY(1.0f);
                }
            } else if (eventType == 3 && CustomButton.this.getScaleY() == 0.8f) {
                CustomButton.this.setScaleX(1.0f);
                CustomButton.this.setScaleY(1.0f);
            }
            return true;
        }
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.imageView = new ImageView(context);
        this.imageView.setImageResource(C0185R.drawable.g1);
        new LayoutParams(-2, -2).gravity = 17;
        addView(this.imageView);
        this.textView = new TextView(context);
        this.textView.setText("12");
        this.textView.setTextSize(15.0f);
        this.textView.setGravity(17);
        this.textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/kush.ttf"));
        this.textView.setVisibility(4);
        new LayoutParams(-2, -2).gravity = 17;
        addView(this.textView);
        setOnTouchListener(new C01761());
    }

    public TextView getTextView() {
        return this.textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
