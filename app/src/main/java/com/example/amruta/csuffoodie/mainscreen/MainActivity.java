package com.example.amruta.csuffoodie.mainscreen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.helper.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    EditText userName, password;
    Button btnLogin, btnReg;
    String uName, uPass;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnReg = (Button)findViewById(R.id.btnReg);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean loginStatus = false;
                String Name = "";
                uName = userName.getText().toString();
                uPass = password.getText().toString();
                DatabaseHandler myDb = new DatabaseHandler(context);
                Cursor cursor = myDb.getUserInfo();
                if (cursor.getCount() == 0) {
                    return;
                }
                cursor.moveToFirst();
                do {
                    if (uName.equals(cursor.getString(0)) && (uPass.equals(0))) ;
                    {
                        loginStatus = true;
                        Name = cursor.getString(0);
                    }

                } while (cursor.moveToNext());
                if (loginStatus) {
                    Toast.makeText(getBaseContext(), "Login Successful! \n Welcome " + Name, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "Oops!Login Failed...", Toast.LENGTH_LONG).show();

                }
                // Toast.makeText(MainActivity.this, "Logged In Successfully!", Toast.LENGTH_LONG).show();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
              }

        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
