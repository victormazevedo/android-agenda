package br.gov.sp.fatec.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import br.gov.sp.fatec.agenda.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarLogin();
            }
        }, 2000);
    }

    private void mostrarLogin() {
        Intent intent = new Intent(SplashScreenActivity.this,
                LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
