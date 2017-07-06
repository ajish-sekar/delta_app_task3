package com.example.android.pokedex;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.type;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.android.pokedex.R.id.progressBar;

public class MainActivity extends AppCompatActivity {
    private DataBaseHandler db;

    String Api = "http://pokeapi.co/api/v2/pokemon/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DataBaseHandler(this);
        Button searchBtn = (Button) findViewById(R.id.search);
        TextView error = (TextView) findViewById(R.id.error);
        if(!isNetworkAvailable()){
            error = (TextView) findViewById(R.id.error);
            error.setText("Please Connect to the Internet");
            error.setVisibility(View.VISIBLE);
        }
        else {
            error.setVisibility(View.GONE);
        }
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               search();
            }
        });
        EditText editText =(EditText) findViewById(R.id.name);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    search();
                    handled=true;
                }
                return handled;
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void search(){
        EditText editText = (EditText) findViewById(R.id.name);
        String name = editText.getText().toString();
        String link = Api + name.toLowerCase();
        PokeAsyncTask task = new PokeAsyncTask();
        task.urlApi=link;
        task.execute();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void updateUI(Pokemon poke){
        TextView textView = (TextView) findViewById(R.id.poke_name);
        textView.setText(poke.getMname().substring(0,1).toUpperCase()+poke.getMname().substring(1));
        TextView textView1 = (TextView) findViewById(R.id.id_no);
        textView1.setText("#"+poke.getMid());
        ArrayList<String> abilities = poke.getMabilities();
        String ability = "";
        for(int i=0;i<abilities.size();i++){
            if(i!=abilities.size()-1)
                ability+=abilities.get(i)+", ";
            else
                ability+=abilities.get(i);
        }
        TextView textView2 = (TextView) findViewById(R.id.act_abilities);
        textView2.setText(ability);
        TextView textView3 =(TextView) findViewById(R.id.act_height);
        textView3.setText(poke.getMheight()+"");
        TextView textView4 = (TextView) findViewById(R.id.act_weight);
        textView4.setText(poke.getMweight()+"");
        ArrayList<String> types = poke.getMtypes();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.act_types);
        linearLayout.removeAllViews();
        for (int i=0; i<types.size(); i++){
            String type=types.get(i);
            TextView textView5 = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(16,16,16,16);

            textView5.setPadding(8,8,8,8);
            textView5.setLayoutParams(params);
            textView5.setTextColor(Color.parseColor("#ffffff"));
            textView5.setTextSize(16);
            if(type.equals("fire")){
                textView5.setBackgroundColor(Color.parseColor("#ff6d00"));
            }
            if(type.equals("water")){
                textView5.setBackgroundColor(Color.parseColor("#2196f3"));
            }
            if(type.equals("grass")){
                textView5.setBackgroundColor(Color.parseColor("#76ff03"));
            }
            if(type.equals("electric")){
                textView5.setBackgroundColor(Color.parseColor("#ffea00"));
            }
            if(type.equals("psychic")){
                textView5.setBackgroundColor(Color.parseColor("#ec407a"));
            }
            if(type.equals("ice")){
                textView5.setBackgroundColor(Color.parseColor("#4dd0e1"));
            }
            if(type.equals("dragon")){
                textView5.setBackgroundColor(Color.parseColor("#3f51b5"));
            }
            if(type.equals("dark")){
                textView5.setBackgroundColor(Color.parseColor("#4e342e"));
            }
            if(type.equals("fairy")){
                textView5.setBackgroundColor(Color.parseColor("#f48fb1"));
            }
            if(type.equals("normal")){
                textView5.setBackgroundColor(Color.parseColor("#9e9e9e"));
            }
            if(type.equals("fighting")){
                textView5.setBackgroundColor(Color.parseColor("#b71c1c"));
            }
            if(type.equals("flying")){
                textView5.setBackgroundColor(Color.parseColor("#b39ddb"));
            }
            if(type.equals("poison")){
                textView5.setBackgroundColor(Color.parseColor("#9c27b0"));
            }
            if(type.equals("ground")){
                textView5.setBackgroundColor(Color.parseColor("#ffab40"));
            }
            if(type.equals("rock")){
                textView5.setBackgroundColor(Color.parseColor("#8d6e63"));
            }
            if(type.equals("bug")){
                textView5.setBackgroundColor(Color.parseColor("#c6ff00"));
            }
            if(type.equals("ghost")){
                textView5.setBackgroundColor(Color.parseColor("#6200ea"));
            }
            if(type.equals("steel")){
                textView5.setBackgroundColor(Color.parseColor("#bdbdbd"));
            }

            String output = type.substring(0,1).toUpperCase() + type.substring(1);
            textView5.setText(output);
            linearLayout.addView(textView5);


        }

        TextView textView6 = (TextView) findViewById(R.id.hp);
        textView6.setText(poke.getMhp()+"");
        TextView textView7 = (TextView) findViewById(R.id.attack);
        textView7.setText(poke.getMattack()+"");
        TextView textView8 = (TextView) findViewById(R.id.defence);
        textView8.setText(poke.getMdefense()+"");
        TextView textView9 = (TextView) findViewById(R.id.sp_attack);
        textView9.setText(poke.getMsp_attack()+"");
        TextView textView10 = (TextView) findViewById(R.id.sp_defence);
        textView10.setText(poke.getMsp_defense()+"");
        TextView textView11 = (TextView) findViewById(R.id.speed);
        textView11.setText(poke.getMspeed()+"");
        String url = poke.getMurl();
        ImageView imageView = (ImageView) findViewById(R.id.pic);
        Picasso.with(getApplicationContext()).load(url).resize(100,100).into(imageView);
    }

    private class PokeAsyncTask extends AsyncTask<URL, Void,Pokemon >{

        public String urlApi;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TextView error = (TextView) findViewById(R.id.error);
            error.setVisibility(View.GONE);
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            RelativeLayout info = (RelativeLayout) findViewById(R.id.info);
            info.setVisibility(View.GONE);
        }

        @Override
        protected Pokemon doInBackground(URL... urls) {
           URL url = createUrl(urlApi);
           String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
               Log.v("Main","IOException");
            }

            Pokemon poke = extractFeatureFromJson(jsonResponse);

            return poke;
        }

        @Override
        protected void onPostExecute(Pokemon poke) {
            super.onPostExecute(poke);
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            if(poke!=null) {
                db.addPoke(new poke_mini(poke.getMname(),poke.getMurl()));
                updateUI(poke);
                RelativeLayout info = (RelativeLayout) findViewById(R.id.info);
                info.setVisibility(View.VISIBLE);
            }else {
                TextView error = (TextView) findViewById(R.id.error);
                error.setText("No results Found!");
                error.setVisibility(View.VISIBLE);
            }

        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e("main", "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                if(url != null) {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.connect();
                    if (urlConnection.getResponseCode() == 200) {
                        inputStream = urlConnection.getInputStream();
                        jsonResponse = readFromStream(inputStream);
                    }
                    else {
                        Log.e("Main activity", "Status code:" + urlConnection.getResponseCode());
                    }
                }
            } catch (IOException e) {


                Log.e("Main Activity","IOException: ", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {

                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        private Pokemon extractFeatureFromJson(String pokeJSON) {
            try {
                JSONObject baseJsonResponse = new JSONObject(pokeJSON);
                String name = baseJsonResponse.getString("name");
                int height = baseJsonResponse.getInt("height");
                int weight = baseJsonResponse.getInt("weight");
                JSONArray abilitiesArray = baseJsonResponse.getJSONArray("abilities");
                ArrayList<String> abilities = new ArrayList<>();
                for(int i=0;i<abilitiesArray.length();i++){
                    JSONObject ability = abilitiesArray.getJSONObject(i);
                    JSONObject abilityObject = ability.getJSONObject("ability");
                    abilities.add(abilityObject.getString("name"));
                }
                JSONArray typeArray = baseJsonResponse.getJSONArray("types");
                ArrayList<String> types = new ArrayList<>();
                for (int i=0;i<typeArray.length();i++){
                    JSONObject type = typeArray.getJSONObject(i);
                    JSONObject typeObject = type.getJSONObject("type");
                    types.add(typeObject.getString("name"));
                }
                int hp=0;
                int attack=0;
                int defense=0;
                int sp_attack=0;
                int sp_defense=0;
                int speed=0;
                JSONArray stats = baseJsonResponse.getJSONArray("stats");
                for(int i=0;i<stats.length();i++){
                    JSONObject stat = stats.getJSONObject(i);
                    JSONObject statObject = stat.getJSONObject("stat");
                    if(statObject.getString("name").equals("speed")){
                        speed = stat.getInt("base_stat");
                    }
                    if(statObject.getString("name").equals("attack")){
                        attack = stat.getInt("base_stat");
                    }
                    if(statObject.getString("name").equals("defense")){
                        defense = stat.getInt("base_stat");
                    }
                    if(statObject.getString("name").equals("hp")){
                        hp = stat.getInt("base_stat");
                    }
                    if(statObject.getString("name").equals("special-attack")){
                        sp_attack = stat.getInt("base_stat");
                    }
                    if(statObject.getString("name").equals("special-defense")){
                        sp_defense = stat.getInt("base_stat");
                    }

                }
                int id = baseJsonResponse.getInt("id");
                JSONObject sprite = baseJsonResponse.getJSONObject("sprites");
                String url = sprite.getString("front_default");
                return new Pokemon(name,abilities,types,id,height,weight,attack,defense,hp,sp_attack,sp_defense,speed,url);
            } catch (JSONException e) {
                Log.e("main", "Problem parsing the earthquake JSON results", e);
            }
            return null;
        }
    }
}
