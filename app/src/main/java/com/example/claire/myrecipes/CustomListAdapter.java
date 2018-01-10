package com.example.claire.myrecipes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] imgurl;

    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.customlist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.imgurl = null;
    }

    public CustomListAdapter(Activity context, String[] itemname, String[] imgurl) {
        super(context, R.layout.customlist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgurl=imgurl;
        this.imgid = null;
    }



    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.customlist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        try {
            Picasso.with(context).load(imgurl[position]).into(imageView);
        } catch (Exception e) {

        }
        txtTitle.setText(itemname[position]);

        return rowView;

    };
}