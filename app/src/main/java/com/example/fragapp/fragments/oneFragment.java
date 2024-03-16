package com.example.fragapp.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragapp.R;
import com.example.fragapp.models.User;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link oneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class oneFragment extends Fragment {
    private FirebaseAuth mAuth;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public oneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment oneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static oneFragment newInstance(String param1, String param2) {
        oneFragment fragment = new oneFragment();
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


        View view = inflater.inflate(R.layout.fragment_one, container, false);
        Button button1To2 = view.findViewById(R.id.buttonTwoToTwo);
        button1To2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_oneFragment_to_twoFragment);
            }
        });

        Button button1To3 = view.findViewById(R.id.buttonOneToThree);
        button1To3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFunc(view);

            }
        });





        return view;
    }


    public void loginFunc(View view) {
        EditText emailEdittext = view.findViewById(R.id.editTextTextEmailAddress2) ;
        EditText passwordEditText = view.findViewById(R.id.editTextTextPassword2);
        String email = emailEdittext.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        if (email.isEmpty() || password.isEmpty()) {
            // Display a toast message indicating that both fields are required
            Toast.makeText(view.getContext(), "Email and password are required", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(TaskExecutors.MAIN_THREAD, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(view.getContext(), "signIn ok", Toast.LENGTH_LONG).show();
                            Bundle bundle = new Bundle();

                            Navigation.findNavController(view).navigate(R.id.action_oneFragment_to_threeFragment);
                        } else {
                            Toast.makeText(view.getContext(), "signIn fail", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }




}