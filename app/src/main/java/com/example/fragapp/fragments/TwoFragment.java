package com.example.fragapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragapp.R;
import com.example.fragapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragment extends Fragment {
    private FirebaseAuth mAuth;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwoFragment newInstance(String param1, String param2) {
        TwoFragment fragment = new TwoFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_two, container, false);
        Button button2To3 = view.findViewById(R.id.buttonThreeToThree);
        button2To3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunReg(view);

            }
        });

        return view;
    }


    public void FunReg(View view) {
        EditText emailEdittext = view.findViewById(R.id.editTextTextEmailAddress) ;
        EditText passwordEditText = view.findViewById(R.id.editTextTextPassword);
        EditText UserName = view.findViewById(R.id.UserName);
        String email = emailEdittext.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String user = UserName.getText().toString().trim();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(TaskExecutors.MAIN_THREAD, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(view.getContext(), "reg ok", Toast.LENGTH_LONG).show();
                            addData(view);
                            Bundle bundle = new Bundle();
                            bundle.putString("name",user);
                            Navigation.findNavController(view).navigate(R.id.action_twoFragment_to_threeFragment,bundle);

                        } else {
                            Toast.makeText(view.getContext(), "reg fail", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    public void addData(View view){
        EditText emailEdittext = view.findViewById(R.id.editTextTextEmailAddress);
        EditText passwordEditText = view.findViewById(R.id.editTextTextPassword);
        EditText phone = view.findViewById(R.id.editTextPhone) ;
        EditText age = view.findViewById(R.id.editTextNumber);
        EditText user = view.findViewById(R.id.UserName);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String str = currentUser.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(str);

        User user1 = new User(user.getText().toString(),emailEdittext.getText().toString(),passwordEditText.getText().toString(),phone.getText().toString(),age.getText().toString());

        myRef.setValue(user1);
    }



}