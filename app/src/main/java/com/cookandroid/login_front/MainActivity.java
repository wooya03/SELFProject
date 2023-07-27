package com.cookandroid.login_front;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    // 회원가입 버튼
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로그인 화면 아이디
        EditText loginId = findViewById(R.id.userid);
        // 로그인 화면 비밀번호
        EditText loginPass = findViewById(R.id.password);
        // 로그인 버튼
        Button loginBtn = findViewById(R.id.loginButton);

        // 회원가입 버튼
        signupButton = findViewById(R.id.signup_button);
        // 회원가입 버튼 이벤트 (회원가입 창으로 변경)
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignupIntent.class);
            startActivity(intent);
        });

    }

}