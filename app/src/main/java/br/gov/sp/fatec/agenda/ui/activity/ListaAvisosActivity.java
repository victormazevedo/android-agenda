package br.gov.sp.fatec.agenda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.dao.AvisoDAO;
import br.gov.sp.fatec.agenda.model.Aviso;
import br.gov.sp.fatec.agenda.recyclerview.adapter.ListaAvisosAdapter;
import br.gov.sp.fatec.agenda.recyclerview.adapter.listener.OnItemClickListener;
import br.gov.sp.fatec.agenda.recyclerview.helper.callback.AvisoItemTouchHelperCallback;

import static br.gov.sp.fatec.agenda.ui.activity.AvisoActivityConstantes.CHAVE_AVISO;
import static br.gov.sp.fatec.agenda.ui.activity.AvisoActivityConstantes.CHAVE_POSICAO;
import static br.gov.sp.fatec.agenda.ui.activity.AvisoActivityConstantes.CODIGO_REQUISICAO_ALTERA_AVISO;
import static br.gov.sp.fatec.agenda.ui.activity.AvisoActivityConstantes.CODIGO_REQUISICAO_INSERE_AVISO;
import static br.gov.sp.fatec.agenda.ui.activity.AvisoActivityConstantes.POSICAO_INVALIDA;

public class ListaAvisosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Avisos";
    private ListaAvisosAdapter adapter;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_avisos);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));

        setTitle(TITULO_APPBAR);

        List<Aviso> todosAvisos = pegaTodosAvisos();
        configuraRecyclerView(todosAvisos);
        configuraBotaoInsereNota();
    }

    private void configuraBotaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioAvisoActivityInsere();
            }
        });
    }

    private void vaiParaFormularioAvisoActivityInsere() {
        Intent iniciaFormularioNota =
                new Intent(ListaAvisosActivity.this,
                        FormularioAvisoActivity.class);
        startActivityForResult(iniciaFormularioNota,
                CODIGO_REQUISICAO_INSERE_AVISO);
    }

    private List<Aviso> pegaTodosAvisos() {
        AvisoDAO dao = new AvisoDAO();
        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ehResultadoInsereNota(requestCode, data)) {

            if (resultadoOk(resultCode)) {
                Aviso avisoRecebido = (Aviso) data.getSerializableExtra(CHAVE_AVISO);
                adiciona(avisoRecebido);
            }

        }

        if (ehResultadoAlteraNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Aviso avisoRecebido = (Aviso) data.getSerializableExtra(CHAVE_AVISO);
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
                if (ehPosicaoValida(posicaoRecebida)) {
                    altera(avisoRecebido, posicaoRecebida);
                }
            }
        }
    }

    private void altera(Aviso aviso, int posicao) {
        new AvisoDAO().altera(posicao, aviso);
        adapter.altera(posicao, aviso);
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) &&
                temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_AVISO;
    }

    private void adiciona(Aviso aviso) {
        new AvisoDAO().insere(aviso);
        adapter.adiciona(aviso);
    }

    private boolean ehResultadoInsereNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                temNota(data);
    }

    private boolean temNota(Intent data) {
        return data != null && data.hasExtra(CHAVE_AVISO);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_AVISO;
    }

    private void configuraRecyclerView(List<Aviso> todosAvisos) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todosAvisos, listaNotas);
        configuraItemTouchHelper(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new AvisoItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Aviso> todosAvisos, RecyclerView listaNotas) {
        adapter = new ListaAvisosAdapter(this, todosAvisos);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Aviso aviso, int posicao) {
                vaiParaFormularioAvisoActivityAltera(aviso, posicao);
            }
        });
    }

    private void vaiParaFormularioAvisoActivityAltera(Aviso aviso, int posicao) {
        Intent abreFormularioComNota = new Intent(ListaAvisosActivity.this,
                FormularioAvisoActivity.class);
        abreFormularioComNota.putExtra(CHAVE_AVISO, aviso);
        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_AVISO);
    }
}
