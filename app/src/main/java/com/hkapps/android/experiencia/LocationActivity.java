package com.hkapps.android.experiencia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LocationActivity extends AppCompatActivity {


    EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
  //      prefs  = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
   edt = (EditText) findViewById(R.id.edt);
        CheckBox chk = (CheckBox) findViewById(R.id.chk);
        Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locat = edt.getText().toString();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
