package com.example.tjw.cpr_ecgshow_system.EventType;

public class PushDepth {
//    按压深度      mm
//    成人正常为 50-60mm
    private float data;
    public PushDepth(){

    }
    public PushDepth(float data) {
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
        return String.valueOf(this.data)+"mm";
    }
}
