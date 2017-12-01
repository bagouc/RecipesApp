package com.example.claire.myrecipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class MyListAdapter extends BaseAdapter {

    private ArrayList<?> list;
    private int R_layout_IdView;
    private Context context;

    public MyListAdapter(Context context, int R_layout_IdView, ArrayList<?> list) {
        super();
        this.context = context;
        this.list = list;
        this.R_layout_IdView = R_layout_IdView;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
        }
        input(list.get(position), view, position);
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /** Devuelve cada una de las list con cada una de las vistas articleView la que debe de ser asociada
     * @param input La enrada que será la asociada articleView la view. La entrada es del tipo del paquete/handler
     * @param view View particular que contendrá los datos del paquete/handler
     */
    public abstract void input(Object input, View view, int position);

}