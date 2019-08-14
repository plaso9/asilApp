package it.uniba.di.sms.asilapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import it.uniba.di.sms.asilapp.R;
import it.uniba.di.sms.asilapp.models.User;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    //Variable for Adapter
    private Context mContext;
    private List<User> mUser;

    //Adapter
    public PatientAdapter(Context mContext, List<User> mUser){
        this.mContext = mContext;
        this.mUser = mUser;
    }

    //Function to set view of users row
    @NonNull
    @Override
    public PatientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_patient_row, parent, false);
        return new PatientAdapter.ViewHolder(view);
    }

    //Function to populate ViewHolder
    @Override
    public void onBindViewHolder(@NonNull PatientAdapter.ViewHolder holder, int position){
        User user = mUser.get(position);
        holder.text_userNameClicked.setText(user.getName());
        holder.text_userSurname.setText(user.getSurname());
        holder.text_userBirth.setText(user.getDate_of_birth());
    }

    //Get number of user
    @Override
    public int getItemCount(){
        return mUser.size();
    }

    //Declaration class ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text_userNameClicked;
        public TextView text_userSurname;
        public TextView text_userBirth;

        public ViewHolder(View itemView){
            super(itemView);
            text_userNameClicked = itemView.findViewById(R.id.text_userNameClicked);
            text_userSurname = itemView.findViewById(R.id.userSurname);
            text_userBirth = itemView.findViewById(R.id.userBirth);
        }
    }
}
