package br.gov.sp.fatec.agenda;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import br.gov.sp.fatec.agenda.dto.EnderecoAlunoDTO;

public class MapaActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Mapa do Aluno";
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        setTitle(TITULO_APPBAR);
        definirCorActionBar();

        Intent intent = getIntent();
        EnderecoAlunoDTO aluno = (EnderecoAlunoDTO) intent.getSerializableExtra("endereco");
        Bundle bundle = new Bundle();
        String enderecoTeste = aluno.getLogradouro() + aluno.getComplemento();
        bundle.putString("endereco", enderecoTeste);

        MapaFragment mapaFragment = new MapaFragment();
        mapaFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();
        tx.replace(R.id.frame_mapa, mapaFragment);
        tx.commit();
    }

    private void definirCorActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));
    }


}
