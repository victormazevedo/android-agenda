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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.dao.AlunoDAO;
import br.gov.sp.fatec.agenda.dao.DatabaseHelper;
import br.gov.sp.fatec.agenda.dto.EnderecoAlunoDTO;
import br.gov.sp.fatec.agenda.dao.EnderecoDAO;
import br.gov.sp.fatec.agenda.model.Aluno;
import br.gov.sp.fatec.agenda.model.Endereco;
import br.gov.sp.fatec.agenda.model.SimpleCallback;
import br.gov.sp.fatec.agenda.service.EnderecoService;

public class FormularioAlunoActivity extends AppCompatActivity {

    private EditText campoCep;

    public static final String TITULO_APPBAR = "Novo aluno";
    private FormularioHelper helper;
    private ActionBar actionBar;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        setTitle(TITULO_APPBAR);
        definirCorActionBar();
        campoCep = findViewById(R.id.activity_formulario_aluno_cep);
        helper = new FormularioHelper(this);

        Spinner spinner = findViewById(R.id.activity_formulario_aluno_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Genero, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        EnderecoAlunoDTO aluno = (EnderecoAlunoDTO) intent.getSerializableExtra("aluno");
        if (aluno != null) {
            helper.preencheFormulario(aluno);
            String pegaGeneroAluno = aluno.getGenero();
            if (pegaGeneroAluno != null) {
                int posicaoSpinner = adapter.getPosition(pegaGeneroAluno);
                spinner.setSelection(posicaoSpinner);
            }
        }
        configuraBotaoBuscar();
        configuraBotaoSalvar();
    }


    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno alunoCriado = helper.getAluno();
                Endereco enderecoCriado = helper.getEndereco();
                salva(alunoCriado, enderecoCriado);
            }
        });
    }

    private void definirCorActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));
    }

    private void salva(Aluno aluno, Endereco endereco) {
        AlunoDAO dao = new AlunoDAO(dbHelper);
        Long idEndereco = salvaEndereco(endereco);
        if (aluno.getId() != null) {
            dao.altera(aluno, endereco.getId());
        } else {
            dao.insere(aluno, idEndereco);

        }
        dbHelper.close();

        Toast.makeText(this, "Aluno " + aluno.getNome() + " salvo!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private Long salvaEndereco(Endereco endereco) {
        EnderecoDAO dao = new EnderecoDAO(dbHelper);
        dao.insere(endereco);
        dbHelper.close();
        return dao.getId();
    }

    private void configuraBotaoBuscar() {
        Button botaoBuscar = findViewById(R.id.activity_formulario_aluno_botao_busca_cep);
        botaoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!campoCep.getText().toString().isEmpty()) {
                    EnderecoService service = new EnderecoService(FormularioAlunoActivity.this);
                    service.getCep(campoCep.getText().toString(), new SimpleCallback<Endereco>() {
                        @Override
                        public void onResponse(Endereco response) {
                            Endereco endereco = response;
                            helper.preencheEnderecoFormulario(endereco);
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(getApplicationContext(), "erro onError: " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "CEP Vazio!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
