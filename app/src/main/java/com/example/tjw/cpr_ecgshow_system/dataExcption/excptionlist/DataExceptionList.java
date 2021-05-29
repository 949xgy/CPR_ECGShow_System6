package com.example.tjw.cpr_ecgshow_system.dataExcption.excptionlist;

import com.example.tjw.cpr_ecgshow_system.dataExcption.DataException;

import java.util.ArrayList;

public class DataExceptionList {
    ArrayList<DataException> ecp_list = new ArrayList<>();

    public DataExceptionList(){

    }
    public DataExceptionList(ArrayList<DataException> ecp_list) {
        this.ecp_list = ecp_list;
    }

    public void addExcpList(DataException ecp){
        ecp_list.add(ecp);
    }
    public void addExcpList(int index,DataException ecp){
        ecp_list.add(index,ecp);
    }
    public DataException getExc(int index){
       DataException ecp=ecp_list.get(index);
       ecp_list.remove(index);
       return ecp;
    }
    public void clear(){
        if(!ecp_list.isEmpty()){
            ecp_list.clear();
        }
    }

}

