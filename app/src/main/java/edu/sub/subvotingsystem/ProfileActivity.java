package edu.sub.subvotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textViewFullName;
    private String fullName;
    public static FirebaseAuth authProfile;
    private LinearLayout VoteNowId, profileId, settingid, passChangeId, logoutBoxId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        intId();

    }

    public void intId() {

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        if (firebaseUser == null) {
            Toast.makeText(this, "User Not Found! Please Registration First!", Toast.LENGTH_LONG).show();
        } else {
            showUserProfile(firebaseUser);
        }
        logoutBoxId = findViewById(R.id.logoutIdd);
        logoutBoxId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseUser != null) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(ProfileActivity.this, firebaseUser.getEmail() + " Sign out!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProfileActivity.this, "You aren't login Yet!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textViewFullName = findViewById(R.id.textViewName);

        imageView = findViewById(R.id.imageView_profile_dp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UploadProfilePicActivity.class);
                startActivity(intent);
            }
        });
        VoteNowId = findViewById(R.id.voteNowId);
        profileId = findViewById(R.id.myProfileId);
        settingid = findViewById(R.id.mySettingId);
        passChangeId = findViewById(R.id.passChangeid);

        VoteNowId.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, PhoneVerificationActivity.class);
            startActivity(intent);
        });
        profileId.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
        });
        settingid.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
        });

        passChangeId.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        //Extracting User Reference from Database for "Registered Users"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    fullName = firebaseUser.getDisplayName();

                    textViewFullName.setText(fullName);

                    //Set User DP (After user has uploaded)
                    Uri uri = firebaseUser.getPhotoUrl();

                    //ImageViewer setImagerUri() should not be used with regular URIs. So we are using Picasso
                    Picasso.with(ProfileActivity.this).load(uri).into(imageView);
                } else {
                    Toast.makeText(ProfileActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }
}