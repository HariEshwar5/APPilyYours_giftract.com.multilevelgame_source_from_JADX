package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.C0000R;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.internal.widget.TintManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextInputLayout extends LinearLayout {
    private static final int ANIMATION_DURATION = 200;
    private ValueAnimatorCompat mAnimator;
    private final CollapsingTextHelper mCollapsingTextHelper;
    private ColorStateList mDefaultTextColor;
    private EditText mEditText;
    private boolean mErrorEnabled;
    private int mErrorTextAppearance;
    private TextView mErrorView;
    private ColorStateList mFocusedTextColor;
    private CharSequence mHint;
    private boolean mHintAnimationEnabled;
    private Paint mTmpPaint;

    class C00141 implements TextWatcher {
        C00141() {
        }

        public void afterTextChanged(Editable s) {
            TextInputLayout.this.updateLabelVisibility(true);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    class C02104 implements AnimatorUpdateListener {
        C02104() {
        }

        public void onAnimationUpdate(ValueAnimatorCompat animator) {
            TextInputLayout.this.mCollapsingTextHelper.setExpansionFraction(animator.getAnimatedFloatValue());
        }
    }

    private class TextInputAccessibilityDelegate extends AccessibilityDelegateCompat {
        private TextInputAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(TextInputLayout.class.getSimpleName());
        }

        public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onPopulateAccessibilityEvent(host, event);
            CharSequence text = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty(text)) {
                event.getText().add(text);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setClassName(TextInputLayout.class.getSimpleName());
            CharSequence text = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty(text)) {
                info.setText(text);
            }
            if (TextInputLayout.this.mEditText != null) {
                info.setLabelFor(TextInputLayout.this.mEditText);
            }
            CharSequence error = TextInputLayout.this.mErrorView != null ? TextInputLayout.this.mErrorView.getText() : null;
            if (!TextUtils.isEmpty(error)) {
                info.setContentInvalid(true);
                info.setError(error);
            }
        }
    }

    class C02542 extends ViewPropertyAnimatorListenerAdapter {
        C02542() {
        }

        public void onAnimationStart(View view) {
            view.setVisibility(0);
        }
    }

    class C02553 extends ViewPropertyAnimatorListenerAdapter {
        C02553() {
        }

        public void onAnimationEnd(View view) {
            view.setVisibility(4);
        }
    }

    public TextInputLayout(Context context) {
        this(context, null);
    }

    public TextInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        this.mCollapsingTextHelper = new CollapsingTextHelper(this);
        setOrientation(1);
        setWillNotDraw(false);
        setAddStatesFromChildren(true);
        this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.mCollapsingTextHelper.setPositionInterpolator(new AccelerateInterpolator());
        this.mCollapsingTextHelper.setCollapsedTextGravity(8388659);
        TypedArray a = context.obtainStyledAttributes(attrs, C0000R.styleable.TextInputLayout, defStyleAttr, C0000R.style.Widget_Design_TextInputLayout);
        this.mHint = a.getText(C0000R.styleable.TextInputLayout_android_hint);
        this.mHintAnimationEnabled = a.getBoolean(C0000R.styleable.TextInputLayout_hintAnimationEnabled, true);
        if (a.hasValue(C0000R.styleable.TextInputLayout_android_textColorHint)) {
            ColorStateList colorStateList = a.getColorStateList(C0000R.styleable.TextInputLayout_android_textColorHint);
            this.mFocusedTextColor = colorStateList;
            this.mDefaultTextColor = colorStateList;
        }
        if (a.getResourceId(C0000R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
            setHintTextAppearance(a.getResourceId(C0000R.styleable.TextInputLayout_hintTextAppearance, 0));
        }
        this.mErrorTextAppearance = a.getResourceId(C0000R.styleable.TextInputLayout_errorTextAppearance, 0);
        boolean errorEnabled = a.getBoolean(C0000R.styleable.TextInputLayout_errorEnabled, false);
        a.recycle();
        setErrorEnabled(errorEnabled);
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setAccessibilityDelegate(this, new TextInputAccessibilityDelegate());
    }

    public void addView(View child, int index, LayoutParams params) {
        if (child instanceof EditText) {
            setEditText((EditText) child);
            super.addView(child, 0, updateEditTextMargin(params));
            return;
        }
        super.addView(child, index, params);
    }

    public void setTypeface(@Nullable Typeface typeface) {
        this.mCollapsingTextHelper.setTypeface(typeface);
    }

    private void setEditText(EditText editText) {
        if (this.mEditText != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        this.mEditText = editText;
        this.mCollapsingTextHelper.setTypeface(this.mEditText.getTypeface());
        this.mCollapsingTextHelper.setExpandedTextSize(this.mEditText.getTextSize());
        this.mCollapsingTextHelper.setExpandedTextGravity(this.mEditText.getGravity());
        this.mEditText.addTextChangedListener(new C00141());
        if (this.mDefaultTextColor == null) {
            this.mDefaultTextColor = this.mEditText.getHintTextColors();
        }
        if (TextUtils.isEmpty(this.mHint)) {
            setHint(this.mEditText.getHint());
            this.mEditText.setHint(null);
        }
        if (this.mErrorView != null) {
            ViewCompat.setPaddingRelative(this.mErrorView, ViewCompat.getPaddingStart(this.mEditText), 0, ViewCompat.getPaddingEnd(this.mEditText), this.mEditText.getPaddingBottom());
        }
        updateLabelVisibility(false);
    }

    private LinearLayout.LayoutParams updateEditTextMargin(LayoutParams lp) {
        LinearLayout.LayoutParams llp = lp instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) lp : new LinearLayout.LayoutParams(lp);
        if (this.mTmpPaint == null) {
            this.mTmpPaint = new Paint();
        }
        this.mTmpPaint.setTypeface(this.mCollapsingTextHelper.getTypeface());
        this.mTmpPaint.setTextSize(this.mCollapsingTextHelper.getCollapsedTextSize());
        llp.topMargin = (int) (-this.mTmpPaint.ascent());
        return llp;
    }

    private void updateLabelVisibility(boolean animate) {
        boolean hasText = (this.mEditText == null || TextUtils.isEmpty(this.mEditText.getText())) ? false : true;
        boolean isFocused = arrayContains(getDrawableState(), 16842908);
        if (!(this.mDefaultTextColor == null || this.mFocusedTextColor == null)) {
            this.mCollapsingTextHelper.setExpandedTextColor(this.mDefaultTextColor.getDefaultColor());
            this.mCollapsingTextHelper.setCollapsedTextColor(isFocused ? this.mFocusedTextColor.getDefaultColor() : this.mDefaultTextColor.getDefaultColor());
        }
        if (hasText || isFocused) {
            collapseHint(animate);
        } else {
            expandHint(animate);
        }
    }

    @Nullable
    public EditText getEditText() {
        return this.mEditText;
    }

    public void setHint(@Nullable CharSequence hint) {
        this.mHint = hint;
        this.mCollapsingTextHelper.setText(hint);
        sendAccessibilityEvent(2048);
    }

    @Nullable
    public CharSequence getHint() {
        return this.mHint;
    }

    public void setHintTextAppearance(@StyleRes int resId) {
        this.mCollapsingTextHelper.setCollapsedTextAppearance(resId);
        this.mFocusedTextColor = ColorStateList.valueOf(this.mCollapsingTextHelper.getCollapsedTextColor());
        if (this.mEditText != null) {
            updateLabelVisibility(false);
            this.mEditText.setLayoutParams(updateEditTextMargin(this.mEditText.getLayoutParams()));
            this.mEditText.requestLayout();
        }
    }

    public void setErrorEnabled(boolean enabled) {
        if (this.mErrorEnabled != enabled) {
            if (this.mErrorView != null) {
                ViewCompat.animate(this.mErrorView).cancel();
            }
            if (enabled) {
                this.mErrorView = new TextView(getContext());
                this.mErrorView.setTextAppearance(getContext(), this.mErrorTextAppearance);
                this.mErrorView.setVisibility(4);
                addView(this.mErrorView);
                if (this.mEditText != null) {
                    ViewCompat.setPaddingRelative(this.mErrorView, ViewCompat.getPaddingStart(this.mEditText), 0, ViewCompat.getPaddingEnd(this.mEditText), this.mEditText.getPaddingBottom());
                }
            } else {
                removeView(this.mErrorView);
                this.mErrorView = null;
            }
            this.mErrorEnabled = enabled;
        }
    }

    public boolean isErrorEnabled() {
        return this.mErrorEnabled;
    }

    public void setError(@Nullable CharSequence error) {
        if (!this.mErrorEnabled) {
            if (!TextUtils.isEmpty(error)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        if (!TextUtils.isEmpty(error)) {
            ViewCompat.setAlpha(this.mErrorView, 0.0f);
            this.mErrorView.setText(error);
            ViewCompat.animate(this.mErrorView).alpha(1.0f).setDuration(200).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setListener(new C02542()).start();
            ViewCompat.setBackgroundTintList(this.mEditText, ColorStateList.valueOf(this.mErrorView.getCurrentTextColor()));
        } else if (this.mErrorView.getVisibility() == 0) {
            ViewCompat.animate(this.mErrorView).alpha(0.0f).setDuration(200).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setListener(new C02553()).start();
            ViewCompat.setBackgroundTintList(this.mEditText, TintManager.get(getContext()).getTintList(C0000R.drawable.abc_edit_text_material));
        }
        sendAccessibilityEvent(2048);
    }

    @Nullable
    public CharSequence getError() {
        if (this.mErrorEnabled && this.mErrorView != null && this.mErrorView.getVisibility() == 0) {
            return this.mErrorView.getText();
        }
        return null;
    }

    public boolean isHintAnimationEnabled() {
        return this.mHintAnimationEnabled;
    }

    public void setHintAnimationEnabled(boolean enabled) {
        this.mHintAnimationEnabled = enabled;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.mCollapsingTextHelper.draw(canvas);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.mEditText != null) {
            int l = this.mEditText.getLeft() + this.mEditText.getCompoundPaddingLeft();
            int r = this.mEditText.getRight() - this.mEditText.getCompoundPaddingRight();
            this.mCollapsingTextHelper.setExpandedBounds(l, this.mEditText.getTop() + this.mEditText.getCompoundPaddingTop(), r, this.mEditText.getBottom() - this.mEditText.getCompoundPaddingBottom());
            this.mCollapsingTextHelper.setCollapsedBounds(l, getPaddingTop(), r, (bottom - top) - getPaddingBottom());
            this.mCollapsingTextHelper.recalculate();
        }
    }

    public void refreshDrawableState() {
        super.refreshDrawableState();
        updateLabelVisibility(ViewCompat.isLaidOut(this));
    }

    private void collapseHint(boolean animate) {
        if (this.mAnimator != null && this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (animate && this.mHintAnimationEnabled) {
            animateToExpansionFraction(1.0f);
        } else {
            this.mCollapsingTextHelper.setExpansionFraction(1.0f);
        }
    }

    private void expandHint(boolean animate) {
        if (this.mAnimator != null && this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (animate && this.mHintAnimationEnabled) {
            animateToExpansionFraction(0.0f);
        } else {
            this.mCollapsingTextHelper.setExpansionFraction(0.0f);
        }
    }

    private void animateToExpansionFraction(float target) {
        if (this.mCollapsingTextHelper.getExpansionFraction() != target) {
            if (this.mAnimator == null) {
                this.mAnimator = ViewUtils.createAnimator();
                this.mAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
                this.mAnimator.setDuration(ANIMATION_DURATION);
                this.mAnimator.setUpdateListener(new C02104());
            }
            this.mAnimator.setFloatValues(this.mCollapsingTextHelper.getExpansionFraction(), target);
            this.mAnimator.start();
        }
    }

    private int getThemeAttrColor(int attr) {
        TypedValue tv = new TypedValue();
        if (getContext().getTheme().resolveAttribute(attr, tv, true)) {
            return tv.data;
        }
        return -65281;
    }

    private static boolean arrayContains(int[] array, int value) {
        for (int v : array) {
            if (v == value) {
                return true;
            }
        }
        return false;
    }
}
