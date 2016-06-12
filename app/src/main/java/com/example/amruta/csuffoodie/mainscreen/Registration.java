package com.example.amruta.csuffoodie.mainscreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.helper.DatabaseHandler;

/**
 * Created by amruta on 4/23/2016.
 */
public class Registration extends Activity {
    EditText editUserName,editPassword,editEmailId,editPhoneNo;
    Button btnReg;
    DatabaseHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        myDb = new DatabaseHandler(this);

        editUserName = (EditText) findViewById(R.id.userName);
        editPassword = (EditText) findViewById(R.id.password);
        editEmailId = (EditText) findViewById(R.id.emailId);
        editPhoneNo = (EditText)findViewById(R.id.phone_no);
        btnReg = (Button) findViewById(R.id.btnReg);
        addData();
    }

    public void addData() {

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = myDb.insertUserInfo(
                        editUserName.getText().toString(),
                        editPassword.getText().toString(),
                        editEmailId.getText().toString(),
                        editPhoneNo.getText().toString()
                );

                if (isInserted = true) {
                    Toast.makeText(Registration.this,
                            "Registered Successfully!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Registration.this,
                            " Data Not Inserted !, Try Again..",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}