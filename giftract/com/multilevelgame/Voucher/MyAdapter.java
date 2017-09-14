package giftract.com.multilevelgame.Voucher;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import giftract.com.multilevelgame.C0185R;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    String[] key = new String[]{"pic1", "pic2", "pic3", "pic4", "pic5", "pic6", "pic7", "pic8"};
    private final LayoutInflater mInflater;
    public List<Item> mItem = new ArrayList();
    int[] redeem_pics = new int[]{C0185R.drawable.r1, C0185R.drawable.r2, C0185R.drawable.r3, C0185R.drawable.r4, C0185R.drawable.r5, C0185R.drawable.r6};
    SharedPreferences sp;

    public static class Item {
        public final int drawableid;
        public final String name;

        Item(String name, int drawableid) {
            this.name = name;
            this.drawableid = drawableid;
        }
    }

    public MyAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mItem.add(new Item("", C0185R.drawable.i1));
        this.mItem.add(new Item("", C0185R.drawable.i2));
        this.mItem.add(new Item("", C0185R.drawable.i3));
        this.mItem.add(new Item("", C0185R.drawable.i4));
        this.mItem.add(new Item("", C0185R.drawable.i5));
        this.mItem.add(new Item("", C0185R.drawable.i6));
    }

    public int getCount() {
        return this.mItem.size();
    }

    public Item getItem(int position) {
        return (Item) this.mItem.get(position);
    }

    public long getItemId(int position) {
        return (long) ((Item) this.mItem.get(position)).drawableid;
    }

    public View getView(int i, View view, ViewGroup parent) {
        View v = view;
        if (v == null) {
            v = this.mInflater.inflate(C0185R.layout.grid_item, parent, false);
            v.setTag(C0185R.id.picture, v.findViewById(C0185R.id.picture));
            v.setTag(C0185R.id.text, v.findViewById(C0185R.id.text));
        }
        ImageView picture = (ImageView) v.getTag(C0185R.id.picture);
        TextView name = (TextView) v.getTag(C0185R.id.text);
        Item item = getItem(i);
        this.sp = Message_Grid.getContextOfApplication().getSharedPreferences("Gift", 0);
        if (this.sp.getString(this.key[i], "0").equals("0")) {
            picture.setImageResource(item.drawableid);
        } else {
            picture.setImageResource(this.redeem_pics[i]);
        }
        return v;
    }
}
