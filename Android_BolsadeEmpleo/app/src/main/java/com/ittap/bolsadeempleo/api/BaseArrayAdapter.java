package com.ittap.bolsadeempleo.api;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ittap.bolsadeempleo.R;
import com.ittap.bolsadeempleo.cursos.Curso;
import com.ittap.bolsadeempleo.cursos.CursosArrayAdapter;

import java.util.ArrayList;
import java.util.List;



public class BaseArrayAdapter extends ArrayAdapter<BaseModel> {

    private final Context context;
    private final ArrayList<BaseModel> objects;

    private static class ViewHolder { TextView textView; }

    public BaseArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<BaseModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BaseModel model = getItem(position);


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

        viewHolder.textView.setText(model.toString());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }
}
