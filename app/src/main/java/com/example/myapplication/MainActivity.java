package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.button4);

        Toast.makeText(this, "OnCreate", Toast.LENGTH_SHORT).show();
        // The activity is created
    }

    public void sendMessage(View view) {
        EditText mEdit   = findViewById(R.id.editTextTextPersonName6);
        Intent i = new Intent(this, MainActivity2.class);
        i.putExtra("name",mEdit.getText().toString());
        startActivity(i);
    }
}