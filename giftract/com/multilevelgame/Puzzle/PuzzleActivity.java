package giftract.com.multilevelgame.Puzzle;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import giftract.com.multilevelgame.C0185R;
import giftract.com.multilevelgame.Const;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;

public class PuzzleActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    CountDownTimer f20a;
    ImageButton autoSolve;
    private PuzzleBoardView boardView;
    int correct;
    private Bitmap imageBitmap = null;
    private ProgressBar progressBar;
    MediaPlayer ring;
    ImageButton showImage;
    SharedPreferences sp;
    private TextView textView;
    int wrong;

    class C01791 implements OnClickListener {

        class C01771 implements DialogInterface.OnClickListener {
            C01771() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }

        C01791() {
        }

        public void onClick(View view) {
            Builder alertDialog = new Builder(PuzzleActivity.this, C0185R.style.AppCompatAlertDialogStyle);
            alertDialog.setMessage((CharSequence) "Giving up so soon? Admit that I am better and that you can't solve puzzles, and the puzzle will autosolve!\n\npassword : ritikaisthebest");
            final View input = new EditText(PuzzleActivity.this.getApplicationContext());
            input.setInputType(129);
            alertDialog.setView(input);
            alertDialog.setPositiveButton((CharSequence) "Cancel", new C01771());
            alertDialog.setNegativeButton((CharSequence) "Unlock Next Challenge", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (input.getText().toString().equalsIgnoreCase(Const.PUZZLE_PASS)) {
                        PuzzleActivity.this.solve();
                        return;
                    }
                    Toast.makeText(PuzzleActivity.this.getApplicationContext(), "Wrong Password", 0).show();
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    class C01802 implements OnClickListener {
        C01802() {
        }

        public void onClick(View view) {
            PuzzleActivity.this.showImage.setVisibility(4);
            PuzzleActivity.this.autoSolve.setVisibility(4);
            FragmentTransaction fragmentTransaction = PuzzleActivity.this.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(C0185R.id.full_container, new ShowImageActivity());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private AsyncTaskRunner() {
        }

        protected void onPreExecute() {
        }

        protected String doInBackground(String... params) {
            String result = null;
            try {
                Thread.sleep(1000);
                return "Completed";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return result;
            }
        }

        protected void onPostExecute(String s) {
            if (s.equals("Completed")) {
                PuzzleActivity.this.displayPicture();
                PuzzleActivity.this.autoSolve.setVisibility(0);
                PuzzleActivity.this.showImage.setVisibility(0);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0185R.layout.activity_puzzle);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/strawberrymuffins.ttf");
        RelativeLayout container = (RelativeLayout) findViewById(C0185R.id.puzzle_container);
        this.boardView = new PuzzleBoardView(this);
        this.textView = (TextView) findViewById(C0185R.id.text1);
        this.textView.setTypeface(typeFace);
        this.progressBar = (ProgressBar) findViewById(C0185R.id.progressBar2);
        this.boardView.setLayoutParams(new LayoutParams(-1, -1));
        container.addView(this.boardView);
        this.autoSolve = (ImageButton) findViewById(C0185R.id.auto_solve_button);
        this.autoSolve.setImageResource(C0185R.drawable.icon_help_password);
        this.autoSolve.setOnClickListener(new C01791());
        this.showImage = (ImageButton) findViewById(C0185R.id.show_image_button);
        this.showImage.setImageResource(C0185R.drawable.icon_show_image);
        this.showImage.setOnClickListener(new C01802());
        new AsyncTaskRunner().execute(new String[]{""});
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void dispatchTakePictureIntent(View view) {
    }

    public void displayPicture() {
        this.imageBitmap = BitmapFactory.decodeResource(getResources(), C0185R.drawable.puzzlepic);
        this.boardView.initialize(this.imageBitmap, this.boardView);
        this.boardView.setTouchable(false);
        this.boardView.invalidate();
        this.f20a = new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                PuzzleActivity.this.textView.setText("" + (millisUntilFinished / 1000));
                PuzzleActivity.this.ring = MediaPlayer.create(PuzzleActivity.this.getApplicationContext(), C0185R.raw.tick);
                PuzzleActivity.this.ring.start();
            }

            public void onFinish() {
                if (PuzzleActivity.this.ring != null) {
                    PuzzleActivity.this.ring.stop();
                }
                PuzzleActivity.this.textView.setText("");
                PuzzleActivity.this.shuffleImage();
                PuzzleActivity.this.boardView.setTouchable(true);
                PuzzleActivity.this.boardView.invalidate();
            }
        }.start();
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

    public void shuffleImage() {
        this.boardView.shuffle();
    }

    public void solve() {
        this.progressBar.setVisibility(0);
        this.boardView.solve();
        this.sp = getSharedPreferences("Gift", 0);
        Editor edit = this.sp.edit();
        edit.putInt("page", 3);
        edit.commit();
    }

    public void onBackPressed() {
        releaseMediaPlayer();
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
            finish();
            return;
        }
        this.autoSolve.setVisibility(0);
        this.showImage.setVisibility(0);
        getFragmentManager().popBackStack();
    }
}
