package com.ucc.csbsafetydataviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class establishmentInput extends AppCompatActivity {
    EditText esName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_input);
        esName = findViewById(R.id.esName);



    }

    public void gotoDataViewer(View view) {
        if (esName.getText().toString().length() != 0){
            Intent i = new Intent(this,dataViewer.class);
            i.putExtra("esName",esName.getText().toString());
            startActivity(i);
        }
        else {
            Toast.makeText(this, "Please Enter the Establishment name", Toast.LENGTH_SHORT).show();
        }
        
    }
}