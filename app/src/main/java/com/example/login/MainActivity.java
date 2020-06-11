package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private Button bt;
    private EditText et;
    String sss = "";
    public Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
                  try {
                        et.setText(msg.obj.toString());
                      System.out.println(msg.obj.toString());
                        //req.setText(msg.getData().getString("babyJSON"));
                    }
                    catch (Exception e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }


            super.handleMessage(msg);
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.bt);
        et = (EditText)findViewById(R.id.et_data_uname);
        bt.setOnClickListener(new View.OnClickListener() {      //登录按钮实现
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        Message message=Message.obtain();;
                        message.obj=getServiceInfo("http://192.168.3.44:8080/getUserItem");
                        handler.sendMessage(message);                   }
                }).start();


                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        });

    }



    /**
     * 发送Get请求到服务器
     * @param strUrlPath:接口地址（带参数）
     * @return
     */
    public static String getServiceInfo(String strUrlPath){
        String strResult = "";
        try {
            URL url = new URL(strUrlPath);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            strResult = buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResult;
    }
}