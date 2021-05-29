package com.example.tjw.cpr_ecgshow_system.EventType;

public class HreatData {
    private float data;
    public HreatData() {

    }
    public HreatData(float data) {
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

        return String.valueOf(this.getData());
    }
}
