package edu.sub.subvotingsystem.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.sub.subvotingsystem.CreateCandidateActivity;
import edu.sub.subvotingsystem.LoginActivity;
import edu.sub.subvotingsystem.R;
import edu.sub.subvotingsystem.ResultActivity;

public class AdminActivity extends AppCompatActivity {

    private CardView postCreate, candidCreate, VoterResult, candidListId, cardViewLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Create Post
        postCreate = findViewById(R.id.postCreateId);
        candidListId = findViewById(R.id.candidListId);
        candidListId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, CandidateListActivity.class);
                startActivity(intent);
            }
        });
        postCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, CreatePostActivity.class);
                startActivity(intent);
            }
        });
        //Create
        candidCreate = findViewById(R.id.candidCreateId);
        candidCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, CreateCandidateActivity.class);
                startActivity(intent);
            }
        });

        //Result
        VoterResult = findViewById(R.id.VoterResultId);
        VoterResult.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ResultActivity.class);
            startActivity(intent);
        });

        cardViewLogout = findViewById(R.id.cardlogoutId);
        cardViewLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}