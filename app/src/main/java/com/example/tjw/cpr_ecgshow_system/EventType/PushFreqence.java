package com.example.tjw.cpr_ecgshow_system.EventType;

public class PushFreqence {
//    按压频率    1Hz=60CMP
//    cmp为单位  大约为100-120cmp为正常按压频率
    private float data;
    public PushFreqence(){

    }

    public PushFreqence(float data) {
        this.data = data;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getData())+"cmp";
    }
}
