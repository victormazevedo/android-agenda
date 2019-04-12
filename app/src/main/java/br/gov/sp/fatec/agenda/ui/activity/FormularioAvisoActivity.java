package br.gov.sp.fatec.agenda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.model.Aviso;

import static br.gov.sp.fatec.agenda.ui.activity.AvisoActivityConstantes.CHAVE_AVISO;
import static br.gov.sp.fatec.agenda.ui.activity.AvisoActivityConstantes.CHAVE_POSICAO;
import static br.gov.sp.fatec.agenda.ui.activity.AvisoActivityConstantes.POSICAO_INVALIDA;

public class FormularioAvisoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_INSERE = "Insere aviso";
    public static final String TITULO_APPBAR_ALTERA = "Altera aviso";
    private int posicaoRecibida = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descricao;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aviso);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));

        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();

        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra(CHAVE_AVISO)){
            setTitle(TITULO_APPBAR_ALTERA);
            Aviso avisoRecebido = (Aviso) dadosRecebidos
                    .getSerializableExtra(CHAVE_AVISO);
            posicaoRecibida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencheCampos(avisoRecebido);
        }
    }

    private void preencheCampos(Aviso avisoRecebido) {
        titulo.setText(avisoRecebido.getTitulo());
        descricao.setText(avisoRecebido.getDescricao());
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_aviso_salvo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(ehMenuSalvaNota(item)){
            Aviso avisoCriado = criaAviso();
            retornaNota(avisoCriado);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Aviso nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_AVISO, nota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecibida);
        setResult(Activity.RESULT_OK,resultadoInsercao);
    }

    @NonNull
    private Aviso criaAviso() {
        return new Aviso(titulo.getText().toString(),
                descricao.getText().toString());
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }
}
