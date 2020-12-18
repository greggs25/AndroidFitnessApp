package com.oo115.myapplication.bodyMeasures;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oo115.myapplication.BodyPart;
import com.oo115.myapplication.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BodyPartAdaptor extends ArrayAdapter<String> implements View.OnClickListener {
    String[] bodyPartName;
    Integer[] bodyPartImage;
    String[] lastRecord;
    private Activity context;

    public BodyPartAdaptor(Activity context, String[] bodyPartName, Integer[] bodyPartImage, String[] lastRecord) {
        super(context, R.layout.bodypartlist, bodyPartName);

        this.context = context;
        this.bodyPartName = bodyPartName;
        this.bodyPartImage = bodyPartImage;
        this.lastRecord = lastRecord;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder = null;
        if (view == null) {
            //build in class to convert xml file to corressponding java object
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.bodypartlist, null, true);
            viewHolder = new ViewHolder(view);
            //view class method which stores view
            view.setTag(viewHolder);
        } else {
            //this gets the stored method in set tag
            viewHolder = (ViewHolder) view.getTag();
        }
        //setting the reference for the image and text view
        viewHolder.bodyPart_image.setImageResource(bodyPartImage[position]);
        viewHolder.bodyPart_name.setText(bodyPartName[position]);

        viewHolder.last_record.setText(lastRecord[position]);
        return view;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        BodyPart dataModel = (BodyPart) object;
    }

    //to optimise list viw performance
    //holds and finds view by id of image and name

    class ViewHolder {
        TextView bodyPart_name, last_record, unit;
        ImageView bodyPart_image;


        ViewHolder(View view) {
            bodyPart_name = view.findViewById(R.id.LIST_DATE);
            bodyPart_image = view.findViewById(R.id.LIST_BODYPART_LOGO);
            last_record = view.findViewById(R.id.LIST_MEASUREMENT_SIZE);
        }
    }

}