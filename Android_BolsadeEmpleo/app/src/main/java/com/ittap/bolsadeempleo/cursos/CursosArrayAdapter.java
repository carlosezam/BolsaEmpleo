package com.ittap.bolsadeempleo.cursos;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ittap.bolsadeempleo.R;

import java.util.ArrayList;
import java.util.List;



public class CursosArrayAdapter extends ArrayAdapter<Curso> {

    private final Context context;
    private final ArrayList<Curso> objects;

    public static class ViewHolder { TextView textView; }

    public CursosArrayAdapter(@NonNull Context context, @NonNull ArrayList<Curso> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.objects = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Curso curso = getItem(position);


        ViewHolder viewHolder = new ViewHolder();


        if( convertView == null )
        {

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.list_item, parent, false);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text_list);
            convertView.setTag( viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(curso.nombre);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }
}
