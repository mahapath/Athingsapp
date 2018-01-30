package com.jungbums.athingscell;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by 75151 on 2017-09-03.
 */

public class DoorbellEntryAdapter extends BaseAdapter {
    private List<DoorbellEntry> listdata;
    private LayoutInflater layoutInflater;
    private Context context;


    public DoorbellEntryAdapter(Context context, List<DoorbellEntry> listdata ){
        this.context=context;
        this.listdata=listdata;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount(){
        return listdata.size();
    }
    @Override
    public Object getItem(int position){
        return listdata.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }

    public void add_list(DoorbellEntry entry){
        listdata.add(entry);
    }
    public void remove(Object o){
        listdata.remove(o);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        CharSequence prettyTime;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.listitem,null);
            holder= new ViewHolder();
            holder.flagView=(ImageView)convertView.findViewById(R.id.iconItem);
            holder.text1=(TextView)convertView.findViewById(R.id.datatext1);
            holder.text2=(TextView)convertView.findViewById((R.id.datatext2));
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        DoorbellEntry entry =this.listdata.get(position);
        prettyTime=DateUtils.getRelativeDateTimeString(context.getApplicationContext(),entry.getTimestamp(),DateUtils.SECOND_IN_MILLIS,DateUtils.WEEK_IN_MILLIS,0);
        holder.text1.setText(prettyTime);
        holder.text2.setText("");
        if(entry.getImage()!=null){
            byte[] imageBytes= Base64.decode(entry.getImage(),Base64.NO_WRAP|Base64.URL_SAFE);
            Bitmap bitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            if(bitmap != null){
                holder.flagView.setImageBitmap(bitmap);
            }else{
                Drawable placeholder= ContextCompat.getDrawable(context.getApplicationContext(),R.drawable.app);
                holder.flagView.setImageDrawable(placeholder);
            }
        }
        return convertView;

    }
    static class ViewHolder{
        ImageView flagView;
        TextView text1;
        TextView text2;
    }

}
