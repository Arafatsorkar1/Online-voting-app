package edu.sub.subvotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    TextView tvR, tvPython, tvCPP, tvJava;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvR = findViewById(R.id.tvR);
        tvPython = findViewById(R.id.tvPython);
        tvCPP = findViewById(R.id.tvCPP);
        tvJava = findViewById(R.id.tvJava);
        pieChart = findViewById(R.id.piechart);

        //setData();
    }
/*
    private void setData()
    {
        // Set the percentage of language used
        tvR.setText(Integer.toString(VoteNowActivity.c1));
        tvPython.setText(Integer.toString(VoteNowActivity.c2));
        tvCPP.setText(Integer.toString(VoteNowActivity.c3));
        tvJava.setText(Integer.toString(VoteNowActivity.c4));

        // Set the data and color to the pie chart
        pieChart.addPieSlice(new PieModel("Candidate 1", Integer.parseInt(tvR.getText().toString()), Color.parseColor("#FFA726")));
        pieChart.addPieSlice(new PieModel("Candidate 2", Integer.parseInt(tvPython.getText().toString()), Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Candidate 3", Integer.parseInt(tvCPP.getText().toString()), Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Candidate 4", Integer.parseInt(tvJava.getText().toString()), Color.parseColor("#29B6F6")));

        // To animate the pie chart
        pieChart.startAnimation();
    }

 */
    //Creating ActionBar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu items
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //When any menu item is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(ResultActivity.this);
        } else if (id == R.id.menu_refresh) {
            //Referesh Activity
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        } else if (id == R.id.menu_vote) {
            Intent intent = new Intent(ResultActivity.this, PhoneVerificationActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_update_profile) {
            Intent intent = new Intent(ResultActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(ResultActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(ResultActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_delete_profile) {
            Intent intent = new Intent(ResultActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_settings) {
            Toast.makeText(ResultActivity.this, "Setting", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_logout) {
            UserProfileActivity.authProfile.signOut();
            Toast.makeText(ResultActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);

            //Clear stack to prevent user coming back to ResultActivity on pressing back button after Logging out
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();       //Close ResultActivity
        } else {
            Toast.makeText(ResultActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}