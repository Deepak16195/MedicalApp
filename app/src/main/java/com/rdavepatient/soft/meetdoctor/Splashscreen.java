package com.rdavepatient.soft.meetdoctor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.rdavepatient.soft.meetdoctor.Activity.Login_page;
import com.rdavepatient.soft.meetdoctor.Data.SharePrefarence;

public class Splashscreen extends AppCompatActivity {
    String Userid;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        final int id =SharePrefarence.getmInstance(this).getUserId();
        if (!(id==0)) {
                i= new Intent(this,MainActivity.class);

        }else {
            i= new Intent(this,Login_page.class);
        }

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();

                }


            }
        };
        timer.start();


    }
}
