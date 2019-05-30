package br.gov.sp.fatec.agenda.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.dao.AlunoDAO;
import br.gov.sp.fatec.agenda.dao.DatabaseHelper;
import br.gov.sp.fatec.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Novo aluno";
    private Aluno aluno;
    private FormularioHelper helper;
    private ActionBar actionBar;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        setTitle(TITULO_APPBAR);
        definirCorActionBar();
        helper = new FormularioHelper(this);

        Spinner spinner = findViewById(R.id.activity_formulario_aluno_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Genero, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if (aluno != null) {
            helper.preencheFormulario(aluno);
            String pegaGeneroAluno = aluno.getGenero();
            if (pegaGeneroAluno != null) {
                int posicaoSpinner = adapter.getPosition(pegaGeneroAluno);
                spinner.setSelection(posicaoSpinner);
            }
        }
        configuraBotaoSalvar();
    }


    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno alunoCriado = helper.getAluno();
                salva(alunoCriado);
            }
        });
    }

    private void definirCorActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));
    }

    private void salva(Aluno aluno) {
        AlunoDAO dao = new AlunoDAO(dbHelper);
        if (aluno.getId() != null) {
            dao.altera(aluno);
        } else {
            dao.insere(aluno);
        }
        dbHelper.close();

        Toast.makeText(this, "Aluno " + aluno.getNome() + " salvo!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
