package br.gov.sp.fatec.agenda.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.dao.UsuarioDAO;
import br.gov.sp.fatec.agenda.model.Usuario;

public class ListaUsuarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        UsuarioDAO dao = new UsuarioDAO(this);
        ListView listaDeUsers = findViewById(R.id.lista_usuarios);
        listaDeUsers.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dao.buscaUsuarios()));
        dao.close();
    }
}
