package com.example.tjw.cpr_ecgshow_system.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tjw.cpr_ecgshow_system.R;
import com.example.tjw.cpr_ecgshow_system.domain.Patient;

import java.util.List;
import java.util.zip.Inflater;

public class InpatientListAdapter extends BaseAdapter {
    Context m_context = null;
    List<Patient> m_patients = null;
    public InpatientListAdapter(Context context, List<Patient> patients) {
        this.m_context = context;
        this.m_patients = patients;
    }
    public void clearAll(){
        this.m_patients.clear();
    }

    @Override
    public int getCount() {
        return m_patients.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(m_context).inflate(R.layout.list_inpatient_layout,viewGroup,false);
            Patient patient = m_patients.get(i);
            TextView textView_name = view.findViewById(R.id.list_showInpatientInfo_item_name),
            textView_sex = view.findViewById(R.id.list_showInpatientInfo_item_sex),
            textView_age = view.findViewById(R.id.list_showInpatientInfo_item_age),
            textView_room = view.findViewById(R.id.list_showInpatientInfo_item_room),
            textView_relation = view.findViewById(R.id.list_showInpatientInfo_item_phone),
            textView_id = view.findViewById(R.id.list_showInpatientInfo_item_id),
            textView_doctor = view.findViewById(R.id.list_showInpatientInfo_item_doctor);

            textView_name.setText(patient.getPatientName());
            textView_sex.setText(patient.getPatientSex());
            textView_age.setText(String.valueOf(patient.getPatientAge()));
            textView_room.setText(String.valueOf(patient.getPatientRoom()));
            textView_relation.setText(patient.getPatientPhone());
            textView_id.setText(patient.getPatientID());
            textView_doctor.setText(patient.getPatientDoc());
        }
        return view;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
