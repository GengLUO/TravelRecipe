package be.kuleuven.travelrecipe.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.base.DatabaseConnect;

public class LoginActivity extends AppCompatActivity {
    private boolean visible;
    private RequestQueue requestQueue;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordEditText2;
    private DatabaseConnect databaseConnect;
    private static String loginURL = "https://studev.groept.be/api/a21pt210/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.userNameInputEditText);
        passwordEditText = findViewById(R.id.passwordInputEditText);
        passwordEditText2 = findViewById(R.id.passwordInputEditText2);
        passwordEditText2.setVisibility(View.INVISIBLE);
        visible = false;
        requestQueue = Volley.newRequestQueue(this);
        databaseConnect = new DatabaseConnect(requestQueue);

    }

    public void onClickLoginButton(View caller)
    {
        if (visible)
        {
            passwordEditText2.setVisibility(View.INVISIBLE);
            visible = false;
        }
        else {
            databaseConnect.login(this, usernameEditText.getText().toString(), encrypt(passwordEditText.getText().toString()));
        }
    }
    public void onClickRegisterButton(View caller)
    {
        if (visible)
        {
            if (passwordEditText.getText().toString().equals(passwordEditText2.getText().toString())) {
                databaseConnect.register(caller, usernameEditText.getText().toString(), encrypt(passwordEditText.getText().toString()));
                passwordEditText2.setVisibility(View.INVISIBLE);
                visible = false;
            }
            else
            {
                Toast.makeText(this,"password input different",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            passwordEditText2.setVisibility(View.VISIBLE);
            visible = true;
        }


    }

    public String encrypt(String password){
        String encryptedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            encryptedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }

    public void login(int userid)
    {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("userid",userid);
        startActivity(intent);
    }


}