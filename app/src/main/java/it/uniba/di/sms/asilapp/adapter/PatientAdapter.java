package it.uniba.di.sms.asilapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import it.uniba.di.sms.asilapp.PatientDetailActivity;
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
    public void onBindViewHolder(@NonNull final PatientAdapter.ViewHolder holder, final int position){
        final User user = mUser.get(position);
        holder.text_userNameClicked.setText(user.getName());
        holder.text_userSurname.setText(user.getSurname());
        holder.text_userBirth.setText(user.getDate_of_birth());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get id of user clicked
                String user_clicked = user.getUserId();
                System.out.println("OBJ : " + user);
                System.out.println("EMAIL : " + user.getMail());
                System.out.println("NAME : " + user.getName());
                System.out.println("ID : " + user.getUserId());

                //Create new intent
                Intent patientDetailIntent = new Intent(mContext, PatientDetailActivity.class);
                //pass parameter to intent
                patientDetailIntent.putExtra("user_clicked", user_clicked);
                mContext.startActivity(patientDetailIntent);
            }
        });
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
