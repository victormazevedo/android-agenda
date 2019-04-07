package br.gov.sp.fatec.agenda.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.gov.sp.fatec.agenda.R;

public class FormularioAlunoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Novo aluno");
        setContentView(R.layout.activity_formulario_aluno);
    }
}
