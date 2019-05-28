package br.gov.sp.fatec.agenda.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import br.gov.sp.fatec.agenda.model.Usuario;

public class RegistroActivity extends AppCompatActivity {

    private static final String TAG = "RegistroActivity";
    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoTelefone;
    private EditText campoSenha;
    private EditText campoRedigiteSenha;
    private Button botaoRegistrar;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        inicializacaoDosCampos();

        botaoRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void signup() {
        Log.d(TAG, "Login");

        if (!validaCampos()) {
            onSignupFailed();
            return;
        }

        botaoRegistrar.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegistroActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Criando conta...");
        progressDialog.show();

        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String senha = campoSenha.getText().toString();
        String redigiteSenha = campoRedigiteSenha.getText().toString();

        UsuarioDAO dao = new UsuarioDAO(this);

        Usuario usuarioCriado = new Usuario(nome, email, senha, telefone);

        dao.insere(usuarioCriado);
        dao.close();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private void onSignupSuccess() {
        botaoRegistrar.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Falha no Login!", Toast.LENGTH_SHORT).show();

        botaoRegistrar.setEnabled(true);
    }

    private boolean validaCampos() {
        boolean valido = true;

        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String senha = campoSenha.getText().toString();
        String redigiteSenha = campoRedigiteSenha.getText().toString();

        if (nome.isEmpty() || nome.length() < 3) {
            campoNome.setError("no mínimo 3 caracteres");
            valido = false;
        } else {
            campoNome.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            campoEmail.setError("insira um e-mail válido");
            valido = false;
        } else {
            campoNome.setError(null);
        }

        if (telefone.isEmpty() || telefone.length() < 12) {
            campoTelefone.setError("insira um telefone válido");
            valido = false;
        } else {
            campoNome.setError(null);
        }

        if (senha.isEmpty() || senha.length() < 4 || senha.length() > 10) {
            campoSenha.setError("senha deve ser entre 4 a 10 caracteres alfanuméricos");
            valido = false;
        } else {
            campoSenha.setError(null);
        }

        if (redigiteSenha.isEmpty() || redigiteSenha.length() < 4 || redigiteSenha.length() > 10 || !(redigiteSenha.equals(senha))) {
            campoRedigiteSenha.setError("Senhas devem ser iguais!");
            valido = false;
        } else {
            campoRedigiteSenha.setError(null);
        }
        return valido;
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.registro_input_name);
        campoEmail = findViewById(R.id.registro_input_email);
        campoTelefone = findViewById(R.id.registro_input_telefone);
        campoSenha = findViewById(R.id.registro_input_password);
        campoRedigiteSenha = findViewById(R.id.registro_input_reEnterPassword);
        botaoRegistrar = findViewById(R.id.registro_btn_criar_conta);
        loginLink = findViewById(R.id.registro_link_login);
    }
}
