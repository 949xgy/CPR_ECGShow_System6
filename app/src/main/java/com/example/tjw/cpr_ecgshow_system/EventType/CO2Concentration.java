package com.example.tjw.cpr_ecgshow_system.EventType;

public class CO2Concentration {
/**
 * 1.PETCO2＜持续10mmHg             希望渺茫；
 * 2.PETCO2＜ 20mmHg                按压低质量；
 * 3.PETCO2 ＞ 20mmHg               按压高质量；
 * 4.PETCO2 ＞30mmHg                突然恢复自主循环。
 */
    private float data;
    public CO2Concentration(){

    }

    public CO2Concentration(float data) {
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
        return String.valueOf(this.getData())+"mmHg";
    }
}
