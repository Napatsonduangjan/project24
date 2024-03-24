package com.example.project4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

class AddActivity extends AppCompatActivity {


    EditText firstName,lastName,phone,email,room,price,status,url;
    Button btnAdd,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        firstName = (EditText)findViewById(R.id.txtfname);
        lastName = (EditText)findViewById(R.id.txtlname);
        phone = (EditText)findViewById(R.id.txtphone);
        email = (EditText)findViewById(R.id.txtemail);
        room = (EditText)findViewById(R.id.txtroom);
        price = (EditText)findViewById(R.id.txtprice);
        status = (EditText)findViewById(R.id.status);
        url = (EditText)findViewById(R.id.txturl);


        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                clearAll();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void insertData(){

        Map<String,Object> map = new HashMap<>();
        map.put("firstName",firstName.getText().toString());
        map.put("lastName",lastName.getText().toString());
        map.put("phone",phone.getText().toString());
        map.put("email",email.getText().toString());
        map.put("room",room.getText().toString());
        map.put("price",price.getText().toString());
        map.put("status",status.getText().toString());
        map.put("url",url.getText().toString());


        FirebaseDatabase.getInstance().getReference().child("user").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,"Data Insert Successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AddActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void clearAll(){

        firstName.setText("");
        lastName.setText("");
        phone.setText("");
        email.setText("");
        room.setText("");
        price.setText("");
        status.setText("");
        url.setText("");


    }



}