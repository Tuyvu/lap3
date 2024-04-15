package com.example.baikt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {
    EditText edtT,edtL,edtH,edtSBD,edtName;
    Button btnsua,btnQuaylai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        edtName = findViewById(R.id.edtHoten);
        edtSBD =findViewById(R.id.edtSBD);
        edtT = findViewById(R.id.edtT);
        edtL = findViewById(R.id.edtL);
        edtH = findViewById(R.id.edtH);
        btnsua = findViewById(R.id.btnedit);
        btnQuaylai = findViewById(R.id.btnback);
        Intent intent = getIntent();
        Bundle d = intent.getExtras();
        if(d!= null){
            String hoten = d.getString("Name");
            String SDT = d.getString("SBD");
            Float T = d.getFloat("Toan");
            Float L = d.getFloat("Ly");
            Float H = d.getFloat("Hoa");
            edtName.setText(hoten);
            edtSBD.setText(SDT);
            edtT.setText(T.toString());
            edtL.setText(L.toString());
            edtH.setText(H.toString());
        }
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String SDT = edtSBD.getText().toString();
                Float T = Float.valueOf(edtT.getText().toString());
                Float L = Float.valueOf(edtL.getText().toString());
                Float H = Float.valueOf(edtH.getText().toString());
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString("Name", name);
                b.putString("SBD", SDT);
                b.putFloat("Toan", T);
                b.putFloat("Ly", L);
                b.putFloat("Hoa", H);
                intent.putExtras(b);
                setResult(200, intent);
                finish();
            }
        });
    }
}