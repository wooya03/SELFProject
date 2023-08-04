package com.cookandroid.login_front;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cookandroid.login_front.database.User;
import com.cookandroid.login_front.database.UserDatabase;

public class MainActivity extends AppCompatActivity {
    // 회원가입 버튼
    Button signupButton;
    // 로그인 화면 아이디
    EditText loginId;
    // 로그인 화면 비밀번호
    EditText loginPass;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로그인 화면 아이디
        loginId = findViewById(R.id.userid);
        // 로그인 화면 비밀번호
        loginPass = findViewById(R.id.password);
        // 로그인 버튼
        Button loginBtn = findViewById(R.id.loginButton);

        // 회원가입 버튼
        signupButton = findViewById(R.id.signup_button);
        // 회원가입 버튼 이벤트 (회원가입 창으로 변경)
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignupIntent.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(v -> {
            new AsyncTask<Void, Void, User>() {
                @Override
                protected User doInBackground(Void... voids) {
                    String id = loginId.getText().toString();
                    String password = loginPass.getText().toString();

                    UserDatabase db = UserDatabase.getInstance(getApplicationContext());
                    return db.userDao().getUser(id, password);
                }

                @Override
                protected void onPostExecute(User user) {
                    if (user != null) {
                        // 로그인 성공
                        Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        // TODO: Implement logic if succeeded login phase
                    } else {
                        // 로그인 실패
                        Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        });
    }
}