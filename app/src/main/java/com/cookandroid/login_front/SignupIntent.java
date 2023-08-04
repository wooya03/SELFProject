package com.cookandroid.login_front;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import com.cookandroid.login_front.database.User;
import com.cookandroid.login_front.database.UserDatabase;

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

        Button signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = signupId.getText().toString();
                String password = signupPassword.getText().toString();

                UserDatabase db = UserDatabase.getInstance(getApplicationContext());

                new Thread(() -> {
                    User existingUser = db.userDao().findById(id);

                    if (existingUser != null) {
                        // 중복 아이디가 존재
                        runOnUiThread(() -> {
                            Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        // 아이디 중복 없음, 회원가입 진행
                        User user = new User(id, password);
                        db.userDao().insert(user);
                        runOnUiThread(() -> {
                            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            // 회원가입 완료 후 로그인 화면으로 이동
                            startActivity(new Intent(SignupIntent.this, MainActivity.class));
                        });
                    }
                }).start();
            }
        });



    }
}
