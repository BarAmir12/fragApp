package com.example.fragapp.activitys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import android.widget.Button;
public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> {
    private ArrayList<DataModel> dataSet;
    public CustomeAdapter(ArrayList<DataModel> dataSet) {
        this.dataSet = dataSet;
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewPrice;
        ImageView imageView;
        Button minus;
        Button plus;
        TextView amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName =itemView.findViewById(R.id.ProductName);
            textViewPrice =itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.imageView);

            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
            amount = itemView.findViewById(R.id.viewAmount);

        }
    }

    @NonNull
    @Override
    public CustomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrow,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.MyViewHolder holder, int position) {
        DataModel dataModel = dataSet.get(position);
        holder.textViewName.setText(dataModel.getName());
        holder.textViewPrice.setText(dataModel.getPrice());
        holder.imageView.setImageResource(dataModel.getImage());

        holder.amount.setText(String.valueOf(dataModel.getAmount()));

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentAmount = dataModel.getAmount();
                currentAmount++;
                dataModel.setAmount(currentAmount);
                holder.amount.setText(String.valueOf(currentAmount));

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = currentUser.getUid();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                userRef.child("item").setValue(dataSet);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentAmount = dataModel.getAmount();
                if (currentAmount > 0) {
                    currentAmount--;
                    dataModel.setAmount(currentAmount);
                    holder.amount.setText(String.valueOf(currentAmount));

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = currentUser.getUid();
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                    userRef.child("item").setValue(dataSet);
                }
            }
        });



    }

    @Override
    public int getItemCount() {

        return dataSet.size();
    }
}
