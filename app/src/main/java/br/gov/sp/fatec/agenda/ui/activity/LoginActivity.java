package br.gov.sp.fatec.agenda.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.gov.sp.fatec.agenda.R;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";

    private EditText campoEmail;
    private EditText campoSenha;
    private Button botaoLogar;
    private TextView textoRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializacaoDosCampos();

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validar()) {
            loginFalhou();
        }
    }

    private void loginFalhou() {
        Toast.makeText(getBaseContext(), "Login falhou!", Toast.LENGTH_LONG).show();

        botaoLogar.setEnabled(true);
    }

    private boolean validar() {
        boolean valido = true;

        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            campoEmail.setError("insira um e-mail válido!");
            valido = false;
        } else {
            campoEmail.setError(null);
        }

        if (senha.isEmpty() || senha.length() < 4 || senha.length() > 15) {
            campoSenha.setError("deve ser maior que 4 e menor que 15 caracteres");
            valido = false;
        } else {
            campoSenha.setError(null);
        }

        return valido;
    }

    private void inicializacaoDosCampos() {
        campoEmail = findViewById(R.id.activity_login_email);
        campoSenha = findViewById(R.id.activity_login_senha);
        botaoLogar = findViewById(R.id.activity_login_botao_logar);
        textoRegistrar = findViewById(R.id.activity_login_registrar);
    }
}
