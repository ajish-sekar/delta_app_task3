package com.example.android.pokedex;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by Ajish on 04-07-2017.
 */

public class Pokemon {
    private String mname;
    private ArrayList<String> mabilities = new ArrayList<>();
    private ArrayList<String> mtypes = new ArrayList<>();
    private int mid;
    private int mheight;
    private int mweight;
    private int mattack;
    private int mdefense;
    private int mhp;
    private int msp_attack;
    private int msp_defense;
    private int mspeed;
    private String murl;

    public Pokemon(String name, ArrayList<String> abilities, ArrayList<String> types, int id, int height, int weight, int attack, int defense, int hp, int sp_attack, int sp_defense, int speed, String url){
        mname = name;
        mabilities = abilities;
        mtypes = types;
        mid = id;
        mheight = height;
        mweight = weight;
        mattack = attack;
        mdefense = defense;
        mhp = hp;
        msp_attack = sp_attack;
        msp_defense = sp_defense;
        mspeed = speed;
        murl = url;
    }

    public String getMname() {
        return mname;
    }

    public ArrayList<String> getMabilities() {
        return mabilities;
    }

    public ArrayList<String> getMtypes() {
        return mtypes;
    }

    public int getMid() {
        return mid;
    }

    public int getMheight() {
        return mheight;
    }

    public int getMweight() {
        return mweight;
    }

    public int getMattack() {
        return mattack;
    }

    public int getMdefense() {
        return mdefense;
    }

    public int getMhp() {
        return mhp;
    }

    public int getMsp_attack() {
        return msp_attack;
    }

    public int getMsp_defense() {
        return msp_defense;
    }

    public int getMspeed() {
        return mspeed;
    }

    public String getMurl() {
        return murl;
    }
}
