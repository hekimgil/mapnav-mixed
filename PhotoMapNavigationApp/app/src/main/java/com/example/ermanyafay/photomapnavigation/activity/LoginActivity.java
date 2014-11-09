package com.example.ermanyafay.photomapnavigation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.ermanyafay.photomapnavigation.R;

public class LoginActivity extends Activity
{
    public static final String AppPreferences = "preferences" ;
    public static final String Email = "email";
    public static final String Password = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button button_login = (Button) findViewById(R.id.loginButton);
        final Button button_tour = (Button) findViewById(R.id.tourButton);
        final EditText passwordLogin = (EditText) findViewById(R.id.passwordLoginGap);
        final EditText emailLogin = (EditText) findViewById(R.id.emailLoginGap);

        button_login.setOnClickListener(new View.OnClickListener()
        {
            String email = emailLogin.getText().toString();
            String password = passwordLogin.getText().toString();

            public void onClick(View v)
            {
                if(login(email,password))
                {
                    goMapActivity();
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Login Failed";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        button_tour.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                goMapActivity();
            }
        });
    }

    private boolean login(String email, String password)
    {
        //login
        return true;
    }

    private void goMapActivity()
    {
        startActivity(new Intent(this, MapsActivity.class));
    }

    private void goRegisterActivity()
    {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.login_actionbar, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_register_activity)
        {
            goRegisterActivity();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}
