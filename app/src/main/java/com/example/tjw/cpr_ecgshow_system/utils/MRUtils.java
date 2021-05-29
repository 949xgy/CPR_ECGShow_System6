package com.example.tjw.cpr_ecgshow_system.utils;



import android.graphics.Color;

import com.example.tjw.cpr_ecgshow_system.R;

import java.util.HashMap;

public class MRUtils {
    private static HashMap<String, Integer> id_hashMap = new HashMap<>();
    private static HashMap<String, Double[]> color_hashMap = new HashMap<>();


    public static HashMap<String, Integer> getId_hashMap() {
        return id_hashMap;
    }

    public static HashMap<String, Double[]> getColor_hashMap() {
        return color_hashMap;
    }

    public static void init() {

        id_hashMap.put("HR", R.id.include_HR);
        color_hashMap.put("HR", new Double[]{30.0, 60.0, 100.0, 130.0});

        id_hashMap.put("RR", R.id.include_RR);
        color_hashMap.put("RR", new Double[]{1.0, 2.0, 3.0, 4.0});

        id_hashMap.put("BP", R.id.include_BP);
        color_hashMap.put("BP", new Double[]{1.0, 2.0, 3.0, 4.0});

        id_hashMap.put("SPO2", R.id.include_SPO2);
        color_hashMap.put("SPO2", new Double[]{80.0, 90.0, 95.0, 98.0});

        id_hashMap.put("PH", R.id.include_PH);
        color_hashMap.put("PH", new Double[]{6.8, 7.35, 7.45, 8.0});

        id_hashMap.put("PaO2", R.id.include_PaO2);
        color_hashMap.put("PaO2", new Double[]{55.0, 75.0, 100.0, 120.0});

        id_hashMap.put("PaCO2", R.id.include_PaCO2);
        color_hashMap.put("PaCO2", new Double[]{25.0, 35.0, 45.0, 55.0});

        id_hashMap.put("Mg", R.id.include_Mg);
        color_hashMap.put("Mg", new Double[]{0.5, 0.75, 1.02, 1.3});

        id_hashMap.put("K", R.id.include_K);
        color_hashMap.put("K", new Double[]{30.0, 60.0, 100.0, 130.0});

        id_hashMap.put("Creat", R.id.include_Creat);
        color_hashMap.put("Creat", new Double[]{37.0, 57.0, 97.0, 117.0});
    }

    public int get_id(String key) {
        return id_hashMap.get(key);
    }

    public static int get_color(String key, double value) {
        Double[] num_value = color_hashMap.get(key);

        if (value < num_value[0]) { // 红
            return Color.RED;
        } else if (value < num_value[1]) { // 黄
            return Color.YELLOW;
        } else if (value < num_value[2]) { // 白
            return Color.WHITE;
        } else if (value < num_value[3]) { // 黄
            return Color.YELLOW;
        } else { // 红
            return Color.RED;
        }
    }
}
