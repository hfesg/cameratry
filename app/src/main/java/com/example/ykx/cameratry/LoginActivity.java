package com.example.ykx.cameratry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YKX on 2017/5/19.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R2.id.input_email) EditText emailText;
    @BindView(R2.id.input_password) EditText passwordText;
    @BindView(R2.id.btn_login) Button loginButton;
    @BindView(R2.id.link_signup) TextView signupLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始注册
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);     //此处使用startActivityForResult打开SignupActivity，是为了从SignupActivity中得到返回数据，该返回数据由onActivityResult得到
            }
        });
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()){
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登陆中...");
        progressDialog.show();

//        String email = emailText.getText().toString();
//        String password = passwordText.getText().toString();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //完成后回调
                onLoginSuccess();
                progressDialog.dismiss();
            }
        }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {     //得到由SignupActivity关闭后返回的数据
        if (requestCode == REQUEST_SIGNUP){
            if (resultCode == RESULT_OK){
                //执行成功注册的代码
                Log.d(TAG, "执行代码");
                String returnedEmail = data.getStringExtra("email_return");
//                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private void onLoginSuccess() {
        loginButton.setEnabled(true);
        Log.d(TAG, "登录成功");
//        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);      //打开主任务
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed.", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("enter a valid email address");
            valid = false;
        }else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10){
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }else {
            passwordText.setError(null);
        }
        return valid;
    }
}












