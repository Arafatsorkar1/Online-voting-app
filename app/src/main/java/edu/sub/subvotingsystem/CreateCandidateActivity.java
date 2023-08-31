package edu.sub.subvotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateCandidateActivity extends AppCompatActivity {

    private EditText editTextCandidateName, edittextCandidateGender;
    private Spinner editTextCandidatePartyName;
    private Button buttonRegisterCandidate;
    private ProgressBar progressBar;
    private static final String TAG = "CreateCandidate";

    private ArrayAdapter<String> adapter;
    private List<String> postList;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_candidate);

        progressBar = findViewById(R.id.progressBar);
        editTextCandidateName = findViewById(R.id.editText_candidate_name);
        editTextCandidatePartyName = findViewById(R.id.editText_candidate_party_name);
        edittextCandidateGender = findViewById(R.id.editText_candidate_gender);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        int voteCount = 0;

        postList = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                postList
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTextCandidatePartyName.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String postName = postSnapshot.child("name").getValue(String.class);
                    if (postName != null) {
                        postList.add(postName);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonRegisterCandidate = findViewById(R.id.button_registerCandidate);
        buttonRegisterCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String candidatePartyName = editTextCandidatePartyName.getSelectedItem().toString();
                String candidateName = editTextCandidateName.getText().toString();
                String candidateGender = edittextCandidateGender.getText().toString();

                if (TextUtils.isEmpty(candidateName)) {
                    Toast.makeText(CreateCandidateActivity.this, "Field can't be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(candidateName, candidatePartyName, candidateGender, voteCount);
                }

                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addDatatoFirebase(String candidateName, String candidatePartyName, String candidateGender, int voteCount) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Create a new node in the database with a unique key
        DatabaseReference candidatesReference = databaseReference.child("CandidatesInfo").push();

        // Create a Candidate object to hold the data
        CandidateInfo candidate = new CandidateInfo(candidateName, candidatePartyName, candidateGender, voteCount);

        // Set the value of the candidate object in the database
        candidatesReference.setValue(candidate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully written to the database
                        Toast.makeText(CreateCandidateActivity.this, "Succefully added candidate!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        editTextCandidateName.setHint("Candidate Name");
                        edittextCandidateGender.setHint("Gender");


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(CreateCandidateActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}