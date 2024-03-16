package com.example.fragapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragapp.R;
import com.example.fragapp.activitys.CustomeAdapter;
import com.example.fragapp.activitys.DataModel;
import com.example.fragapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;
import java.util.ArrayList;
import com.example.fragapp.activitys.myData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThreeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private static final String TAG = "ThreeFragment";
    private ArrayList<DataModel> dataSet;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CustomeAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThreeFragment newInstance(String param1, String param2) {
        ThreeFragment fragment = new ThreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        dataSet = new ArrayList<>();
        recyclerView = view.findViewById(R.id.resView);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for(int i = 0 ; i < myData.nameArray.length ; i++) {
            dataSet.add(new DataModel(
                    myData.nameArray[i],
                    myData.drawableArray[i],
                    myData.priceArray[i],
                    myData.id_[i]
            ));
        }

        adapter =new CustomeAdapter(dataSet);
        recyclerView.setAdapter(adapter);


        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment

        textView = view.findViewById(R.id.textView);

        readData(view);
        updateUserQuantities();


        return view;
    }
    private void updateUserQuantities() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null && user.getItem() != null) {
                    for (DataModel dataModel : user.getItem()) {
                        updateProductQuantity(dataModel);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                Log.w(TAG, "Failed to read user data", error.toException());
            }
        });
    }

    // פונקציה זו מעדכנת את כמות המוצר במודל ובאינטרפייס
    private void updateProductQuantity(DataModel dataModel) {
        for (DataModel model : dataSet) {
            if (model.getId_() == dataModel.getId_()) {
                model.setAmount(dataModel.getAmount());
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    public void readData(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String str = currentUser.getUid();
        DatabaseReference myRef = database.getReference("users").child(str);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);

                if (value != null) {
                    String strR = value.getUser();
                    textView.setText("Welcome :" + strR);
                } else {
                    // Handle the case where the value is null
                    Toast.makeText(view.getContext(), "User data is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(view.getContext(), "Failed to read user data", Toast.LENGTH_SHORT).show();
            }
        });
    }



}