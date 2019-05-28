package br.gov.sp.fatec.agenda.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.dao.UsuarioDAO;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public static final String EMAIL = "victor.moura@fatec.com.br";
    public static final String SENHA = "123456";

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

        textoRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validaCampos()) {
            falhaLogin();
            return;
        }

        botaoLogar.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        final String email = campoEmail.getText().toString();
        final String senha = campoSenha.getText().toString();

        final UsuarioDAO dao = new UsuarioDAO(this);

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (dao.buscaParaLogar(email, senha)) {
                            sucessoLogin();
                        } else {
                            falhaLogin();
                            botaoLogar.setEnabled(true);
                        }
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    private void sucessoLogin() {
        botaoLogar.setEnabled(true);
        startActivity(new Intent(this, ListaAlunosActivity.class));
    }

    private void falhaLogin() {
        if (!campoSenha.getText().toString().equals(SENHA) && !campoEmail.getText().toString().equals(EMAIL)) {
            campoSenha.setError("senha incorreta!");
            campoEmail.setError("email não cadastrado!");

            campoSenha.setText("");
            campoEmail.setText("");
        } else if (!campoEmail.getText().toString().equals(EMAIL)) {
            campoEmail.setError("email não cadastrado!");
            campoEmail.setText("");
        } else {
            campoSenha.setError("senha incorreta!");
            campoSenha.setText("");
        }

        Toast.makeText(this, "Falha no login!", Toast.LENGTH_SHORT).show();
        botaoLogar.setEnabled(true);
    }

    private boolean validaCampos() {
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
