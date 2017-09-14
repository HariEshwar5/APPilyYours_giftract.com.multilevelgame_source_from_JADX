package giftract.com.multilevelgame.LevelSummary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import giftract.com.multilevelgame.C0185R;
import giftract.com.multilevelgame.Puzzle.MainActivityPuzzle;
import giftract.com.multilevelgame.Quiz.MainActivityQuiz;
import giftract.com.multilevelgame.SpinWheel.MainActivitySpinWheel;
import giftract.com.multilevelgame.Voucher.About_voucher;
import giftract.com.multilevelgame.basketGame.MainActivity;

public class SinglePageFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "XXX";
    int counter = 0;
    private IOnPageFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    RelativeLayout relativeLayout;

    public interface IOnPageFragmentInteractionListener {
        void onPageFragmentInteraction(Uri uri);
    }

    public static SinglePageFragment newInstance(String param1, String param2) {
        SinglePageFragment fragment = new SinglePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(C0185R.layout.fragment_single_page, container, false);
        this.relativeLayout = (RelativeLayout) rootView.findViewById(C0185R.id.root_linear_layout);
        loopOnChildrenViews(this.relativeLayout);
        setButtonsListeners(this.relativeLayout);
        return rootView;
    }

    private void setButtonsListeners(ViewGroup viewGroup) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("Gift", 0);
        Editor editor = sharedPref.edit();
        final int level = sharedPref.getInt("Highest Level", 1);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (!(v instanceof ImageButton)) {
                if (v instanceof CustomButton) {
                    String id = (String) ((CustomButton) v).getTextView().getText();
                    Log.d("id", id);
                    final int i_id = Integer.parseInt(id);
                    if (i_id <= level) {
                        v.setAlpha(1.0f);
                        ((CustomButton) v).mListener2 = new IOnButtonClicked() {
                            public void onLoadLevel() {
                                Log.d("check", i_id + " " + level);
                                if (i_id == level) {
                                }
                                if (i_id == 1) {
                                    SinglePageFragment.this.startActivity(new Intent(SinglePageFragment.this.getActivity(), MainActivityQuiz.class));
                                    SinglePageFragment.this.getActivity().finish();
                                } else if (i_id == 2) {
                                    SinglePageFragment.this.startActivity(new Intent(SinglePageFragment.this.getActivity(), MainActivityPuzzle.class));
                                    SinglePageFragment.this.getActivity().finish();
                                } else if (i_id == 3) {
                                    SinglePageFragment.this.startActivity(new Intent(SinglePageFragment.this.getActivity(), MainActivitySpinWheel.class));
                                    SinglePageFragment.this.getActivity().finish();
                                } else if (i_id == 4) {
                                    SinglePageFragment.this.startActivity(new Intent(SinglePageFragment.this.getActivity(), MainActivity.class));
                                    SinglePageFragment.this.getActivity().finish();
                                } else if (i_id == 5) {
                                    SinglePageFragment.this.startActivity(new Intent(SinglePageFragment.this.getActivity(), About_voucher.class));
                                    SinglePageFragment.this.getActivity().finish();
                                }
                            }
                        };
                    } else {
                        v.setClickable(false);
                        v.setOnTouchListener(null);
                    }
                } else if (v instanceof ViewGroup) {
                    setButtonsListeners((ViewGroup) v);
                }
            }
        }
    }

    private void loopOnChildrenViews(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (!(v instanceof ImageButton)) {
                if (v instanceof ImageView) {
                    if (!(v instanceof ImageButton)) {
                        if (this.counter == 0) {
                            ((ImageView) v).setImageResource(C0185R.drawable.g1);
                        }
                        if (this.counter == 1) {
                            ((ImageView) v).setImageResource(C0185R.drawable.g2);
                        }
                        if (this.counter == 2) {
                            ((ImageView) v).setImageResource(C0185R.drawable.g3);
                        }
                        if (this.counter == 3) {
                            ((ImageView) v).setImageResource(C0185R.drawable.g4);
                        }
                        if (this.counter == 4) {
                            ((ImageView) v).setImageResource(C0185R.drawable.g5);
                        }
                    }
                } else if (v instanceof TextView) {
                    ((TextView) v).setText("" + ((this.counter + 0) + 1));
                    this.counter++;
                } else if (v instanceof ViewGroup) {
                    loopOnChildrenViews((ViewGroup) v);
                }
            }
        }
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onPageFragmentInteraction(uri);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IOnPageFragmentInteractionListener) {
            this.mListener = (IOnPageFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement IOnPageFragmentInteractionListener");
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}
