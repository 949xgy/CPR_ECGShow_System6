package com.example.tjw.cpr_ecgshow_system.dataExcption;

public class DataException {
    String ecp_type = null;
    int ecp_state;
    String opration = null;
    public DataException(){

    }
    public DataException(String ecp_type, int ecp_state, String opration) {
        this.ecp_type = ecp_type;
        this.ecp_state = ecp_state;
        this.opration = opration;
    }

    public DataException(String ecp_type, int ecp_state) {
        this.ecp_type = ecp_type;
        this.ecp_state = ecp_state;
    }

    public String getEcp_type() {
        return ecp_type;
    }

    public void setEcp_type(String ecp_type) {
        this.ecp_type = ecp_type;
    }

    public int getEcp_state() {
        return ecp_state;
    }

    public void setEcp_state(int ecp_state) {
        this.ecp_state = ecp_state;
    }
    public String getOpration() {
        return opration;
    }

    public void setOpration(String opration) {
        this.opration = opration;
    }

    public void setExcpData(String type,int state){
        this.ecp_state = state;
        this.ecp_type = type;

    }
    public void setExcpData(String type,int state,String opration){
        this.ecp_state = state;
        this.ecp_type = type;
        this.opration = opration;
    }


}
