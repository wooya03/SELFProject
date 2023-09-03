package com.cookandroid.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.SystemClock;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream; // 블루투스 디바이스로 부터 데이터 읽어오기 위한 변수
    private final OutputStream mmOutStream; // 블루투스 디바이스로 부터 데이터 받아오기 위한 변수

    private volatile boolean receivingData = false;

    private static final String TAG = "ArduinoSensorData"; // TAG 변수를 정의

    public ConnectedThread(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }


    public void startReceiving() {
        receivingData = true;
    }


    @Override
    public void run() {

        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytecount; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (receivingData) {
            try {
                // Read from the InputStream
                bytecount = mmInStream.available();
                if (bytecount != 0) {
                    //SystemClock.sleep(100); // pause and wait for rest of data. Adjust this depending on your sending speed.
                    bytecount = mmInStream.available(); // how many bytes are ready to be read?
                    bytecount = mmInStream.read(buffer, 0, bytecount); // record how many bytes we actually read
                    buffer[bytecount] = '\0';




                    //String str = new String(bytes, java.nio.charset.StandardCharsets.US_ASCII);
                    //Log.d(TAG, str);
                    if(buffer[0] == '{'){

                    }
                    Log.d(TAG, "");

                    // Parse JSON data and process the sensor value
                    //String jsonData = new String(buffer, 0, bytecount+1);
                    String jsonData = new String(buffer, 0, bytecount);
                    Log.d(TAG, jsonData);
                    processData(jsonData);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

    }

    // 데이터를 페이링된 장치로 보내는 메서드
    public void write(String input) {
        byte[] bytes = input.getBytes();           //converts entered String into bytes
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }

    private void processData(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            double sensorValue = jsonObject.getDouble("acc_x");

            // 센서 값을 사용하여 필요한 작업 수행
            // 예시로 로그를 출력합니다.
            String logMessage = "Received sensor value: " + sensorValue;
            Log.d(TAG, logMessage);
            // 여기서부터 센서 값을 활용하여 필요한 작업을 수행할 수 있습니다.
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON data: " + e.getMessage());
        }
    }
}
