#include <SoftwareSerial.h>

#include <MPU6050_tockn.h>
#include <Wire.h>


//bluetooth
#define BT_RXD 3
#define BT_TXD 2
SoftwareSerial bluetooth(BT_RXD, BT_TXD);

char* g_buffer = new char[0xff];

//mpu
MPU6050 mpu(Wire);

//data
float current_x, current_y, current_z;


void setup() {
  bluetooth.begin(9600);  //bluetooth init

  Serial.begin(9600);             //serial init
  Wire.begin();                   //i2c init
  mpu.begin();                //mpu init
  mpu.calcGyroOffsets(false);  //mpu setting

  Serial.println("x,y,z");
  //delay(2000);  //just wait

}

#define fs(f) (String(f).c_str())
#define m mpu

void MakeJson() {

  //가속도
  float acc_x = m.getAccX();
  float acc_y = m.getAccY();
  float acc_z = m.getAccZ(); //상하 축

  //각속도
  float gyro_x = m.getGyroX();
  float gyro_y = m.getGyroY();
  float gyro_z = m.getGyroZ();

  //잘 몰?루
  float acc_angle_x = m.getAccAngleX();
  float acc_angle_y = m.getAccAngleY();

  //current 에서 총 기울기 각도
  float gyro_angle_x = m.getGyroAngleX();
  float gyro_angle_y = m.getGyroAngleY();
  float gyro_angle_z = m.getGyroAngleZ();

  //각도 그자체 한바퀴돌면 다시 돌아옴(z축은 안돌아옴)
  float angle_x = m.getAngleX();
  float angle_y = m.getAngleY();
  float angle_z = m.getAngleZ();
  
  

  int idx = 0;
  //const char* format = "{\"acc_x\":%f,\"acc_y\":%f,\"angle_x\":%f,\"angle_y\":%f,\"angle_z\":%f,\"gyro_x\":%f,\"gyro_y\":%f,\"gyro_z\":%f}";
  
  //full
  //const char* format = "{\"acc_x\":%s,\"acc_y\":%s,\"acc_z\":%s,\"gyro_x\":%s,\"gyro_y\":%s,\"gyro_z\":%s,\"acc_angle_x\":%s,\"acc_angle_y\":%s,\"gyro_angle_x\":%s,\"gyro_angle_y\":%s,\"gyro_angle_z\":%s,\"angle_x\":%s,\"angle_y\":%s,\"angle_z\":%s}";   // \"acc_y\":%s
  //sprintf(g_buffer, format, fs(acc_x), fs(acc_y), fs(acc_z), fs(gyro_x), fs(gyro_y), fs(gyro_z), fs(acc_angle_x), fs(acc_angle_y), fs(gyro_angle_x), fs(gyro_angle_y), fs(gyro_angle_z), fs(angle_x), fs(angle_y), fs(angle_z));

  //최적화
  //const char* format = "{\"acc_x\":%s,\"acc_y\":%s,\"acc_z\":%s,\"gy_x\":%s,\"gy_y\":%s,\"gy_z\":%s,\"an_x\":%s,\"an_y\":%s,\"an_z\":%s}";   // \"acc_y\":%s
  //sprintf(g_buffer, format, fs(acc_x), fs(acc_y), fs(acc_z), fs(gyro_x), fs(gyro_y), fs(gyro_z), fs(angle_x), fs(angle_y), fs(angle_z));

  //더 최적화
  const char* format = "{\"acc_x\":%s,\"acc_y\":%s,\"acc_z\":%s,\"an_x\":%s,\"an_y\":%s,\"an_z\":%s}";   // \"acc_y\":%s
  sprintf(g_buffer, format, fs(acc_x), fs(acc_y), fs(acc_z), fs(angle_x), fs(angle_y), fs(angle_z));
}

void SendData() {
  MakeJson();

  bluetooth.println(g_buffer);
  //Serial.println("0");
}
void debug(){
  //가속도
  float acc_x = m.getAccX();
  float acc_y = m.getAccY();
  float acc_z = m.getAccZ(); //상하 축

  //각속도
  float gyro_x = m.getGyroX();
  float gyro_y = m.getGyroY();
  float gyro_z = m.getGyroZ();

  //잘 몰?루
  float acc_angle_x = m.getAccAngleX();
  float acc_angle_y = m.getAccAngleY();

  //current 에서 총 기울기 각도
  float gyro_angle_x = m.getGyroAngleX();
  float gyro_angle_y = m.getGyroAngleY();
  float gyro_angle_z = m.getGyroAngleZ();

  //각도 그자체 한바퀴돌면 다시 돌아옴(z축은 안돌아옴)
  float angle_x = m.getAngleX();
  float angle_y = m.getAngleY();
  float angle_z = m.getAngleZ();


  //낙상 감지 알고리즘 짤때

  Serial.print(gyro_x);
  Serial.print(",");
  Serial.print(gyro_y);
  Serial.print(",");
  Serial.print(gyro_z);

  Serial.println("");

  
}

//일정 이상의 가속도가 감지되고 angle을 일정이상 유지한다면 낙상 처리
void loop() {
  static long timer = millis();

  mpu.update();
  
  //if (millis() > timer) {
  if(1){
    SendData();
    
    debug();


    timer = millis();
  }
  return;

}
