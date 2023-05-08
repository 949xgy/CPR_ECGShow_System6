package com.example.tjw.cpr_ecgshow_system.Types;

import org.json.JSONArray;
// http返回数据封装类
public class ReturnData {
    public JSONArray Data;
    public String Msg;
    public Boolean State;
    public ReturnData(JSONArray data , String msg , int state){
        this.Data = data;
        this.Msg = msg;
        this.State = state==1?true:false;
    }
}
