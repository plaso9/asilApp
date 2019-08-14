package it.uniba.di.sms.asilapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import it.uniba.di.sms.asilapp.R;
import it.uniba.di.sms.asilapp.models.MedicalRecord;

public class MedicalRecordAdapter extends RecyclerView.Adapter<MedicalRecordAdapter.ViewHolder> {

    //Variable for Adapter
    private Context mContext;
    private List<MedicalRecord> mMedicalRecord;

    //Adapter
    public MedicalRecordAdapter(Context mContext, List<MedicalRecord> mMedicalRecord){
        this.mContext = mContext;
        this.mMedicalRecord = mMedicalRecord;
    }

    //Function to set view of medical records row
    @NonNull
    @Override
    public MedicalRecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_medical_record_row, parent, false);
            return new MedicalRecordAdapter.ViewHolder(view);
    }

    //Function to populate ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MedicalRecordAdapter.ViewHolder holder, int position){
        MedicalRecord medicalRecord = mMedicalRecord.get(position);
        holder.text_valueMedicalRecord.setText(medicalRecord.getValue());
        holder.text_dataMedicalRecord.setText(medicalRecord.getData_measurement());
    }

    //Get number of medical record
    @Override
    public int getItemCount(){
        return mMedicalRecord.size();
    }

    //Declaration class ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text_valueMedicalRecord;
        public TextView text_dataMedicalRecord;

        public ViewHolder(View itemView){
            super(itemView);
            text_valueMedicalRecord = itemView.findViewById(R.id.text_valueMedicalRecord);
            text_dataMedicalRecord = itemView.findViewById(R.id.text_dataMedicalRecord);
        }
    }
}
