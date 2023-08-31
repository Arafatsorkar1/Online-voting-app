package edu.sub.subvotingsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.sub.subvotingsystem.admin.adapter.CandidateAdapter;


public class VoteNowActivity extends AppCompatActivity {

    private RecyclerView recyclerView, recyclerView1, recyclerView2, recyclerView3, recyclerView4, recyclerView5;
    private CandidateAdapter adapter, adapter1, adapter2, adapter3;
    private List<CandidateInfo> candidateList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_now);
        President();
        VicePresident();
        JointSecretary();
        GeneralSecretary();
    }

    public void President() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CandidateAdapter(candidateList);
        recyclerView.setAdapter(adapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CandidatesInfo");
        Query query = databaseReference.orderByChild("candidatePartyName").equalTo("President");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                candidateList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CandidateInfo candidate = snapshot.getValue(CandidateInfo.class);
                    candidateList.add(candidate);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

        // Set a click listener for each candidate item
        adapter.setOnItemClickListener(new CandidateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Update vote count and store in Voting database
                CandidateInfo selectedCandidate = candidateList.get(position);
                DatabaseReference votingReference = FirebaseDatabase.getInstance().getReference("Voting");
                votingReference.child(selectedCandidate.getCandidatePartyName()).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        Integer currentVoteCount = currentData.getValue(Integer.class);
                        if (currentVoteCount == null) {
                            currentData.setValue(1); // If no votes, initialize to 1
                        } else {
                            currentData.setValue(currentVoteCount + 1); // Increment vote count
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot currentData) {
                        // Handle transaction completion
                        if (committed) {
                            Toast.makeText(VoteNowActivity.this, "Vote counted!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(VoteNowActivity.this, "Vote failed to count.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    public void VicePresident() {
        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        adapter1 = new CandidateAdapter(candidateList);
        recyclerView1.setAdapter(adapter1);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CandidatesInfo");
        Query query = databaseReference.orderByChild("candidatePartyName").equalTo("Vice-President");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                candidateList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CandidateInfo candidate = snapshot.getValue(CandidateInfo.class);
                    candidateList.add(candidate);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });


    }

    public void JointSecretary() {
        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        adapter2 = new CandidateAdapter(candidateList);
        recyclerView2.setAdapter(adapter2);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CandidatesInfo");
        Query query = databaseReference.orderByChild("candidatePartyName").equalTo("Joint-Secretary");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                candidateList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CandidateInfo candidate = snapshot.getValue(CandidateInfo.class);
                    candidateList.add(candidate);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    public void GeneralSecretary() {
        recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        adapter3 = new CandidateAdapter(candidateList);
        recyclerView3.setAdapter(adapter3);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CandidatesInfo");
        Query query = databaseReference.orderByChild("candidatePartyName").equalTo("Joint-Secretary");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                candidateList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CandidateInfo candidate = snapshot.getValue(CandidateInfo.class);
                    candidateList.add(candidate);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

}