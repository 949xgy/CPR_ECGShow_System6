package com.example.tjw.cpr_ecgshow_system.EventType;

public class ChestSpringBack {

//    胸腔回弹true为正常，为false为回弹幅度不够
    private boolean isTrue;
    public ChestSpringBack(){
    }
    public ChestSpringBack(boolean isTrue) {
        this.isTrue = isTrue;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    @Override
    public String toString() {
        return String.valueOf(this.isTrue);
    }
}
