package com.cookandroid.login_front;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

// 화면 전환 메소드
public class SignupIntent extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        // 회원가입 화면 이름
        EditText signupName = findViewById(R.id.username);
        // 회원가입 화면 아이디
        EditText signupId = findViewById(R.id.userid);
        // 회원가입 화면 비밀번호
        EditText signupPassword = findViewById(R.id.password);

    }
}
