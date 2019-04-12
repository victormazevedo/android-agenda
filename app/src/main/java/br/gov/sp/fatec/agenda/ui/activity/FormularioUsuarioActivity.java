package br.gov.sp.fatec.agenda.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.model.Usuario;

public class FormularioUsuarioActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private FormularioUsuarioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        setTitle("Dados dos Usuários");
        definirCorActionBar();

        helper = new FormularioUsuarioHelper(this);

        Intent intent = getIntent();
        Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
        if (usuario != null) {
            helper.preencheFormulario(usuario);
        }

    }

    private void definirCorActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));
    }
}
