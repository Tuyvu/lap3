package com.example.baikt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityAdd extends AppCompatActivity {
    Button btnAdd, btnCancel;
    EditText edtname,edtSDT,edtToan,edtLy,edtHoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        edtSDT = findViewById(R.id.edtSDT);
        edtname = findViewById(R.id.edtname);
        edtLy = findViewById(R.id.edtLy);
        edtToan = findViewById(R.id.edtToan);
        edtHoa = findViewById(R.id.edtHoa);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtname.getText().toString();
                String SDT = edtSDT.getText().toString();
                Float T = Float.valueOf(edtToan.getText().toString());
                Float L = Float.valueOf(edtLy.getText().toString());
                Float H = Float.valueOf(edtHoa.getText().toString());
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString("Name", name);
                b.putString("SDT", SDT);
                b.putFloat("Toan", T);
                b.putFloat("Ly", L);
                b.putFloat("Hoa", H);
                intent.putExtras(b);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}