package giftract.com.multilevelgame.Voucher;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import giftract.com.multilevelgame.C0185R;
import giftract.com.multilevelgame.Const;
import giftract.com.multilevelgame.LevelSummary.LevelSelectorActivity;

public class Message_Grid extends Activity {
    public static Context contextOfApplication;
    String[] key = new String[]{"pic1", "pic2", "pic3", "pic4", "pic5", "pic6"};
    String[][] key2;
    int pos;
    String[] redeems;
    String f8s;
    SharedPreferences sp;

    public Message_Grid() {
        r0 = new String[6][];
        r0[0] = new String[]{"1", "pic1"};
        r0[1] = new String[]{"0", "pic2"};
        r0[2] = new String[]{"0", "pic3"};
        r0[3] = new String[]{"0", "pic4"};
        r0[4] = new String[]{"0", "pic5"};
        r0[5] = new String[]{"0", "pic6"};
        this.key2 = r0;
        this.redeems = new String[]{"Candle-light dinner", "A Long Drive", "A shopping spree", "A wish of your choice", "A long kiss", "A surprise"};
    }

    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(C0185R.layout.message_grid);
            GridView grid = (GridView) findViewById(C0185R.id.grid);
            grid.setAdapter(new MyAdapter(this));
            final SharedPreferences sp = getSharedPreferences("Gift", 0);
            contextOfApplication = getApplicationContext();
            grid.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                    if (sp.getString(Message_Grid.this.key[position], "0").equals("0")) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction("android.intent.action.SEND");
                        sendIntent.putExtra("android.intent.extra.TEXT", "Hi Ritika, I would like to redeem voucher " + Message_Grid.this.redeems[position] + ". Please provide the password for the same.");
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("com.whatsapp");
                        Message_Grid.this.startActivity(sendIntent);
                        Message_Grid.this.pos = position;
                        Message_Grid.this.dialogbox();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LevelSelectorActivity.class));
        finish();
    }

    private void dialogbox() {
        Builder dialogBuilder = new Builder(this, C0185R.style.AppCompatAlertDialogStyle);
        View dialogView = getLayoutInflater().inflate(C0185R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText pass = (EditText) dialogView.findViewById(C0185R.id.edit1);
        dialogBuilder.setMessage((CharSequence) "Enter Password");
        dialogBuilder.setPositiveButton((CharSequence) "Done", new OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!pass.getText().toString().toLowerCase().equals(Const.VOUCHER_PASS[Message_Grid.this.pos])) {
                    Toast.makeText(Message_Grid.this, "Incorrect Password", 0).show();
                } else if (Message_Grid.this.pos < 6) {
                    Message_Grid.this.sp = Message_Grid.getContextOfApplication().getSharedPreferences("Gift", 0);
                    Editor edit = Message_Grid.this.sp.edit();
                    edit.putString(Message_Grid.this.key[Message_Grid.this.pos], "1");
                    edit.commit();
                }
            }
        });
        dialogBuilder.create().show();
    }
}
