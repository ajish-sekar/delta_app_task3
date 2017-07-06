package com.example.android.pokedex;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<poke_mini> pokeList;
    private ProgressBar progressBar;
    private DataBaseHandler dbh;
    private int no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Prefs",MODE_PRIVATE);
        no = sharedPreferences.getInt("number",0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbh = new DataBaseHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        DbAsyncTask task= new DbAsyncTask();
        task.execute();
        if(no==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Instructions");
            builder.setMessage("Swipe left or right to delete an entry");
            builder.setNeutralButton("Got it!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("number",1);
        editor.commit();
    }

    private void setRecyclerViewItemTouchListener() {


        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int position = viewHolder.getAdapterPosition();
                dbh.deletePoke(pokeList.get(position));
                pokeList.remove(position);
                recyclerView.getAdapter().notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class DbAsyncTask extends AsyncTask<Void ,Void ,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            pokeList = dbh.getAllPOke();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            recyclerAdapter = new RecyclerAdapter(pokeList);
            recyclerView.setAdapter(recyclerAdapter);
            setRecyclerViewItemTouchListener();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.clear_all){
            dbh.deleteAll();
            pokeList.clear();
            recyclerView.getAdapter().notifyDataSetChanged();
            return true;
        }

        else if(item.getItemId()==R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        else
        return super.onOptionsItemSelected(item);
    }
}
