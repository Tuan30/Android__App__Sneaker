package nhom8.android_coding.sneaker_app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import io.paperdb.Paper;
import nhom8.android_coding.sneaker_app.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);
        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (Exception exception){

                }finally {
                    if (Paper.book().read("user") == null){
                        Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent home = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(home);
                        finish();
                    }
                }
            }
        };
        thread.start();
    }
}