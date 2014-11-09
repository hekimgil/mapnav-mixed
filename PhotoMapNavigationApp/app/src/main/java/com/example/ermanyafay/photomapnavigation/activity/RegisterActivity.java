package com.example.ermanyafay.photomapnavigation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.ermanyafay.photomapnavigation.R;

public class RegisterActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText usernameRegister = (EditText) findViewById(R.id.usernameRegisterGap);
        final EditText userFirstNameRegister = (EditText) findViewById(R.id.userFirstNameRegisterGap);
        final EditText userLastNameRegister = (EditText) findViewById(R.id.userLastNameRegisterGap);
        final EditText emailRegister = (EditText) findViewById(R.id.emailRegisterGap);
        final EditText passwordRegister = (EditText) findViewById(R.id.passwordRegisterGap);
        final Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            String username = usernameRegister.getText().toString();
            String firstName = userFirstNameRegister.getText().toString();
            String lastName = userLastNameRegister.getText().toString();
            String email = emailRegister.getText().toString();
            String password = passwordRegister.getText().toString();

            public void onClick(View v)
            {
                if(register(username,firstName,lastName,email,password))
                {
                    goMapActivity();
                }
            }
        });
    }

    private boolean register(String username, String firstName, String lastName, String email, String password)
    {
        // register
        return true;
    }

    private void goMapActivity()
    {
        startActivity(new Intent(this, MapsActivity.class));
    }

    private void goTourActivity()
    {
        startActivity(new Intent(this, TourActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.register_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.tourActivityAction)
        {
            goTourActivity();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}
