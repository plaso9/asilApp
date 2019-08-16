package it.uniba.di.sms.asilapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import it.uniba.di.sms.asilapp.PersonalDataActivity;
import it.uniba.di.sms.asilapp.R;
import it.uniba.di.sms.asilapp.models.Message;
import it.uniba.di.sms.asilapp.models.User;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    //variables to choose msg type in chat view
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    //Firebase variable
    FirebaseUser fUser;

    //Variable for Adapter
    private Context mContext;
    private List<Message> mMessage;

    //Adapter
    public MessageAdapter(Context mContext, List<Message> mMessage){
        this.mContext = mContext;
        this.mMessage = mMessage;
    }

    //Function to set type of view from type of msg
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        if (viewType == MSG_TYPE_LEFT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    //Function to populate ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position){
        Message message = mMessage.get(position);

        holder.show_message.setText(message.getMessage());
        holder.time_message.setText(message.getDate());
        holder.from_message.setText(message.getUser_id());
    }

    //Get number of messages
    @Override
    public int getItemCount(){
        return mMessage.size();
    }

    //Get type of message
    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mMessage.get(position).getUser_id().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    //Declaration class ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public TextView time_message;
        public TextView from_message;

        public ViewHolder(View itemView){
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            time_message = itemView.findViewById(R.id.time_message);
            from_message = itemView.findViewById(R.id.from_message);
        }
    }
}
