package com.example.tjw.cpr_ecgshow_system.EventType;

public class Completeshock {
    private boolean completeshock;
    public Completeshock(){

    }
    public Completeshock(boolean completeinj) {
        this.completeshock = completeinj;
    }

    public boolean isCompleteinj() {
        return completeshock;
    }

    public void setCompleteinj(boolean completeinj) {
        this.completeshock = completeinj;
    }

    @Override
    public String toString() {
        return String.valueOf(this.completeshock);
    }

}
