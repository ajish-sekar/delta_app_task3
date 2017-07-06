package com.example.android.pokedex;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ajish on 06-07-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PokeHolder> {

    private ArrayList<poke_mini> mpokeList;

    public static class PokeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mimageView;
        private TextView mtextView;
        private poke_mini mpoke;

        public PokeHolder(View v){
            super(v);
            mimageView = (ImageView) v.findViewById(R.id.item_image);
            mtextView = (TextView) v.findViewById(R.id.item_name);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Log.v("Recycler","Click");
        }

        public void bindPoke(poke_mini poke){
            mpoke = poke;
            Picasso.with(mimageView.getContext()).load(poke.getMurl()).resize(60,60).into(mimageView);
            mtextView.setText(poke.getMname());

        }
    }

    public RecyclerAdapter(ArrayList<poke_mini> pokeList){

        mpokeList = pokeList;
    }

    @Override
    public RecyclerAdapter.PokeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new PokeHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.PokeHolder holder, int position) {

        poke_mini pokeMini = mpokeList.get(position);
        holder.bindPoke(pokeMini);
    }

    @Override
    public int getItemCount() {
        return mpokeList.size();
    }
}
