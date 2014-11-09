package com.example.ermanyafay.photomapnavigation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.ermanyafay.photomapnavigation.R;

public class RegisterActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText userFirstNameRegister = (EditText) findViewById(R.id.userFirstNameRegisterGap);
        final EditText userLastNameRegister = (EditText) findViewById(R.id.userLastNameRegisterGap);
        final EditText emailRegister = (EditText) findViewById(R.id.emailRegisterGap);
        final EditText passwordRegister = (EditText) findViewById(R.id.passwordRegisterGap);
        final Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            String firstName = userFirstNameRegister.getText().toString();
            String lastName = userLastNameRegister.getText().toString();
            String email = emailRegister.getText().toString();
            String password = passwordRegister.getText().toString();

            public void onClick(View v)
            {
                if(register(firstName,lastName,email,password))
                {
                    goMapActivity();
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Register Failed!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }

    private boolean register(String firstName, String lastName, String email, String password)
    {
        // register
        return true;
    }

    private void goMapActivity()
    {
        startActivity(new Intent(this, MapsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
