package com.example.sa005gu.swaggersdksamples;

/**
 * Created by sa005gu on 9/24/2017.
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter<String[]>  {
    private List<String[]>  scoreList = new ArrayList<String[]>();

    static class ItemViewHolder {
        TextView name;
        TextView score;
    }

    public ItemArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }



    @Override
    public void add(String[] object) {
        scoreList.add(object);
        super.add(object);
    }



    @Override
    public int getCount() {
        return this.scoreList.size();
    }

    @Override
    public String[] getItem(int index) {
        return this.scoreList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_layout, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.name = (TextView) row.findViewById(R.id.name);

            //viewHolder.score = (TextView) row.findViewById(R.id.score);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder)row.getTag();
        }
        String[] stat = getItem(position);
        String address = stat[0] + " " + stat[1]+ " " + stat[2]+ " " + stat[3]+ " " + stat[4]+ " " + stat[5];
        viewHolder.name.setText(address);
        //viewHolder.score.setText(stat[1]);
        return row;
    }
}

