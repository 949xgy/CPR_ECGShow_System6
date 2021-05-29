package com.example.tjw.cpr_ecgshow_system.EventType;

public class Completeinj {
    private boolean completeinj;
    public Completeinj(){

    }
    public Completeinj(boolean completeinj) {
        this.completeinj = completeinj;
    }

    public boolean isCompleteinj() {
        return completeinj;
    }

    public void setCompleteinj(boolean completeinj) {
        this.completeinj = completeinj;
    }

    @Override
    public String toString() {
        return String.valueOf(this.completeinj);
    }

}
