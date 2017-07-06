package com.example.android.pokedex;

/**
 * Created by Ajish on 05-07-2017.
 */

public class poke_mini {
    private String mname;
    private String murl;

    public poke_mini(String name, String url){
        mname = name;
        murl = url;
    }

    public String getMname() {
        return mname;
    }

    public String getMurl() {
        return murl;
    }
}
