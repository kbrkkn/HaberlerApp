package com.example.kubra.haberlerapp;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
public class HaberAdapter extends BaseAdapter {
    private ArrayList<HaberModel>haberListe;
    private LayoutInflater layoutInflater;

    public HaberAdapter(Activity activity,ArrayList<HaberModel> haberListe) {
        layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.haberListe = haberListe;}

    @Override
    public int getCount() {
        return haberListe.size();
    }

    @Override
    public Object getItem(int position) {
        return haberListe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satir=layoutInflater.inflate(R.layout.custom_satir,null);
        HaberModel haberModel=haberListe.get(position);

        ImageView imageView= (ImageView) satir.findViewById(R.id.imageView);

        TextView textViewTitle= (TextView) satir.findViewById(R.id.title);
        TextView textViewDetail= (TextView) satir.findViewById(R.id.detail);
        textViewTitle.setText(haberModel.getTitle());
        textViewDetail.setText(haberModel.getDescription());

        ArrayList<HaberGorsel>haberResimleri=haberModel.getFiles();
        if(haberResimleri.size()>0){
            Glide.with(imageView.getContext()).load(haberResimleri.get(0).getFileUrl()).into(imageView);
        }
        else{
            imageView.setVisibility(View.GONE);
        }

        return satir;
    }
}
