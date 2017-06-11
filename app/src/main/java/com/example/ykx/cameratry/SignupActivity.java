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
 * Created by YKX on 2017/5/22.
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name) EditText nameText;
    @BindView(R.id.input_address) EditText addressText;
    @BindView(R.id.input_email) EditText emailText;
    @BindView(R.id.input_mobile) EditText mobileText;
    @BindView(R.id.input_password) EditText passwordText;
    @BindView(R.id.input_reEnterPassword) EditText reEnterPasswordText;
    @BindView(R.id.btn_signup) Button signupButton;
    @BindView(R.id.link_login) TextView loginLink;

    String name = nameText.getText().toString();
    String address = addressText.getText().toString();
    String email = emailText.getText().toString();
    String mobile = mobileText.getText().toString();
    String password = passwordText.getText().toString();
    String reEnterPassword = reEnterPasswordText.getText().toString();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户有账号，就结束注册界面，返回登录界面
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void signup() {
        Log.d(TAG, "Signup");
        if (!validate()){
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("创建账户...");
        progressDialog.show();

        //加入注册之后的逻辑

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //完成之后回调onSignupSuccess或者onSignupFailed
                onSignupSuccess();
                progressDialog.dismiss();
            }
        }, 3000);
    }

    private boolean validate() {
        boolean valid = true;
//        String name = nameText.getText().toString();
//        String address = addressText.getText().toString();
//        String email = emailText.getText().toString();
//        String mobile = mobileText.getText().toString();
//        String password = passwordText.getText().toString();
//        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length()<3){
            nameText.setError("最少输入两个字符");
            valid = false;
        }else {
            nameText.setError(null);
        }

        if (address.isEmpty()){
            addressText.setError("请输入有效地址");
            valid = false;
        }else {
            addressText.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("请输入有效邮箱地址");
            valid = false;
        }else {
            emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() < 10){
            mobileText.setError("请输入正确的电话号码");
            valid = false;
        }else {
            mobileText.setError(null);
        }

        if (password.isEmpty() || password.length()<4 || password.length()>10){
            passwordText.setError("请输入4到10位的密码");
            valid = false;
        }else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length()<4 || reEnterPassword.length()>10 || !(reEnterPassword.equals(password))){
            reEnterPasswordText.setError("密码不匹配");
            valid = false;
        }else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "登录失败..", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    private void onSignupSuccess() {
        signupButton.setEnabled(true);
        Intent intent = new Intent();
        intent.putExtra("email_return", email);
        setResult(RESULT_OK, intent);     //用于向LoginActivity的onActivityResult返回resultCode和 Intent data，此处返回了为null的Intent，可以返回包含登录名的Intent
        finish();
    }
}
