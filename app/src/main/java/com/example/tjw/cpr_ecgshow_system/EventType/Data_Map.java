package com.example.tjw.cpr_ecgshow_system.EventType;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Data_Map {

    private HashMap all_data;

    public Data_Map() {
        this.all_data = new HashMap<String,String>();
        all_data.put("RR","0");
        all_data.put("BP","0/0");
        all_data.put("SPO2","0");
        all_data.put("PH","0");
        all_data.put("PaO2","0");
        all_data.put("Mg","0");
        all_data.put("PaCO2","0");
        all_data.put("K","0");
        all_data.put("Creat","0");
    }
    public void putData(String key ,String val){
        Log.i("key",key +  ":" +val);
        if(!all_data.containsKey(key)){
            all_data.put(key,val);
        }else{
            all_data.remove(key);
            all_data.put(key,val);
        }
    }
    public HashMap<String,String> getMap(){
        return all_data;
    }

    public void putAll(HashMap map){
      all_data.putAll(map);
    }
    public String getData(String key){

        return String.valueOf(all_data.get(key));
    }
    public boolean isTrue_Key(String key){
        return all_data.containsKey(key);
    }
}
