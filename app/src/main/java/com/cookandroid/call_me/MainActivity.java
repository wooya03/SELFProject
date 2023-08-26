package com.cookandroid.call_me;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cookandroid.call_me.utils.DBManager;

import java.util.List;

public class MainActivity extends Activity{
    // SQLite 변수 선언
    private DBManager dbManager;
    // 전화 번호 추가
    private EditText mEditNumber;
    // 전화 번호 확인 View
    private TextView telTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 전화번호 추가 버튼 및 걸기 버튼
        Button mAdd;
        Button mCall;
        // 권한 요청을 식별 하기 위해 쓰이는 변수
        final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1000;

        // dbManager 초기화 및 조회를 위해 DB 가져 오기
        dbManager = new DBManager(this);
        dbManager.deleteAllUserData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        telTextView = findViewById(R.id.telTextView);
        mAdd = findViewById(R.id.btnAdd);
        mCall = findViewById(R.id.btnCall);
        mEditNumber = findViewById(R.id.edtNumber);

        // 전화번호 추가 이벤트 리스너
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        // 전화 걸기 이벤트 리스너
        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(view);
            }
        });

        // 자동 하이폰 추가
        mEditNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

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

    public void add() {
        String mNum = mEditNumber.getText().toString();
        if(mNum.length() < 13){
            Toast.makeText(this,"전화 번호 형식이 올바르지 않습니다.",Toast.LENGTH_LONG).show();
        }else{
            long result = dbManager.insertUserData(mNum);
            if(result != -1){
                updateUiFromDatabase();
            }
        }
    }

    public void call(View v){
        // 입력한 번호를 대입할 변수
        String mNum;

        // 입력한 번호 문자열로 가져오기
        List<List<String>> data = dbManager.selectAll();

        if(data.isEmpty()){
            Toast.makeText(this,"등록된 전화번호가 없습니다.",Toast.LENGTH_LONG).show();
        }else{
            mNum = data.get(0).get(1);
            // 앞에는 tel: 을 붙여야함
            String tel = "tel:" + mNum;
            // 버튼이라는 이벤트가 전화걸기 버튼이었을 때
            if (v.getId() == R.id.btnCall) {
                Toast.makeText(this,mNum + " 로 전화를 겁니다.",Toast.LENGTH_LONG).show();

                startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
            }
        }
    }

    // DB 데이터를 UI에 그려 주는 함수
    public void updateUiFromDatabase(){
        // 간단하게 StringBuilder 를 사용해서 합쳐진 문자열을 뿌려줌
        StringBuilder viewData = new StringBuilder();
        List<List<String>> data = dbManager.selectAll();

        for(int i = 0; i < data.size(); i++){
            viewData.append(data.get(i)).append("\n");
        }

        telTextView.setText(viewData.toString());
    }
}