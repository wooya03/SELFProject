package com.cookandroid.call_me;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity implements View.OnClickListener {
    // 전화번호 입력 Text
    private EditText mEditNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 전화 바로 걸기 버튼
        Button mCall;
        // 다이얼로그 버튼
        Button mDialogCall;
        // 권한 요청을 식별하기 위해 쓰이는 변수
        final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1000;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCall = findViewById(R.id.btnCall);
        mEditNumber = findViewById(R.id.edtNumber);
        mCall.setOnClickListener(MainActivity.this);

        // 현재 전화 걸기 권한의 상태
        int permissionCheck = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE);

        // 권한을 허용 받지 못했을 때
        if (permissionCheck!= PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"권한 승인이 필요합니다",Toast.LENGTH_LONG).show();

            // 사용자가 명시적으로 권한을 거부했을 때
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE)) {
                Toast.makeText(this,"전화 권한이 필요합니다.",Toast.LENGTH_LONG).show();
            }
            else {
                // 전화 걸기 권한을 다시 요청한다. 뒤에 상수는 권한 요청 식별할 때 쓰입니다.
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                Toast.makeText(this,"전화 권한이 필요합니다.",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        // 입력한 번호를 대입할 변수
        String mNum;
        // 입력한 번호 문자열로 가져오기
        mNum = mEditNumber.getText().toString();
        // 앞에는 tel: 을 붙여야함
        String tel = "tel:" + mNum;
        // 버튼이라는 이벤트가 전화걸기 버튼이었을 때
        if (v.getId() == R.id.btnCall) {
            startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
        }
    }
}