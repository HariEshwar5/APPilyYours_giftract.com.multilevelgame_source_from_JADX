package giftract.com.multilevelgame.Quiz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import giftract.com.multilevelgame.C0185R;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;

public class QuizActivity extends Activity implements OnClickListener {
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    int correct = 0;
    EditText f4e;
    boolean flag;
    int f5g = 0;
    String[] op1 = new String[]{"Actually, woh kya hai", "You're always on your phone", "Ice-cream", "Phone", "Pani puri", "DDLJ", "A waitress", "She can talk in her sleep", "Cockroach", "She doesn't hint.", "Playing kabaddi", "Lips", "Any Jagjit Singh song", "Threesome", "A big house with 87 dogs"};
    String[] op2 = new String[]{"Dhatt teri ki", "You're always indecisive about food", "A long drive", "Handbag", "Maggi", "K3G", "An astronaut", "She can eat like an elephant", "Lizard", "Excessive PDA", "Chasing an ice cream van", "Forehead", "Pehla Nasha", "Skinny dipping in the ocean", "2 honeymoons every year"};
    String[] op3 = new String[]{"Aaj bohot crazy kar liya", "You don't shave daily", "A tight hug", "Harry Potter's last book", "Cupcakes", "Kaho Na Pyar Hai", "A lion tamer", "She bathes for 6 hours", "Chicken", "She will wear her fav lingerie and make sure I know it", "Learning how to cycle", "Back", "I just called", "BDSM with whips and handcuffs", "Billionaires in a private island"};
    String[] op4 = new String[]{"Fridge mein ice cream hai?", "You spend an hour on the newspaper", "A weekend vacation plan", "The lingerie you gifted", "She can eat anything, including a village", "Andaz Apna Apna", "A wife", "She remembers every episode of 'Friends'", "Black cats", "She will sext me", "Dozing on a horse", "Ahem", "Tere Sang Yaara", "Sex in public", "Running the world's biggest toy shop"};
    String[] ques = new String[]{"Which dialogue does she/I use a lot?", "Which habit of yours irritates her the most?", "When she is upset, what can cheer her up the fastest?", "What’s that one thing, apart from you, she would save in a fire.", "What’s her favourite food?", "What’s the first movie we saw together?", "When she was a child, what did she want to be when she grew up?", "What’s her best natural talent?", "Which insect or animal is she most afraid of?", "How does she hint if she's in a naughty mood.", "She fractured her arm in 5th grade. What was she doing?", "Where does she like kissing you the most?", "Which song does she hum all the time?", "What's her naughtiest fantasy.", "What's her idea of the perfect future with you."};
    TextView res;
    TextView text;
    int wrong = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0185R.layout.activity_questions);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/strawberrymuffins.ttf");
        this.text = (TextView) findViewById(C0185R.id.text);
        this.res = (TextView) findViewById(C0185R.id.res);
        this.b1 = (Button) findViewById(C0185R.id.button1);
        this.b2 = (Button) findViewById(C0185R.id.button2);
        this.b3 = (Button) findViewById(C0185R.id.button3);
        this.b4 = (Button) findViewById(C0185R.id.button4);
        this.b1.setTypeface(typeFace);
        this.b2.setTypeface(typeFace);
        this.b3.setTypeface(typeFace);
        this.b4.setTypeface(typeFace);
        this.text.setTypeface(typeFace);
        this.res.setTypeface(typeFace);
        this.text.setText(this.ques[0]);
        this.b1.setText(this.op1[0]);
        this.b2.setText(this.op2[0]);
        this.b3.setText(this.op3[0]);
        this.b4.setText(this.op4[0]);
        this.b1.setOnClickListener(this);
        this.b2.setOnClickListener(this);
        this.b3.setOnClickListener(this);
        this.b4.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == new Button[]{this.b3, this.b2, this.b2, this.b4, this.b4, this.b3, this.b1, this.b1, this.b3, this.b1, this.b3, this.b4, this.b2, this.b2, this.b4}[this.f5g]) {
            MediaPlayer.create(getApplicationContext(), C0185R.raw.correct_answer).start();
            this.flag = true;
            this.res.setText("Correct");
            this.res.setTextColor(Color.parseColor("#4ef438"));
            nextQ();
            return;
        }
        MediaPlayer.create(getApplicationContext(), C0185R.raw.wrong_answer).start();
        this.res.setText("Incorrect");
        this.res.setTextColor(Color.parseColor("#ee0000"));
        this.flag = false;
        nextQ();
    }

    private void nextQ() {
        if (this.flag) {
            this.correct++;
        } else {
            this.wrong++;
        }
        this.f5g++;
        if (this.f5g < this.ques.length) {
            this.text.setText(this.ques[this.f5g]);
            this.b1.setText(this.op1[this.f5g]);
            this.b2.setText(this.op2[this.f5g]);
            this.b3.setText(this.op3[this.f5g]);
            this.b4.setText(this.op4[this.f5g]);
            return;
        }
        Intent n = new Intent(this, ResultActivity.class);
        n.putExtra("correct", this.correct);
        n.putExtra("wrong", this.wrong);
        startActivity(n);
        finish();
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }
}
