package br.gov.sp.fatec.agenda.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.dao.DatabaseHelper;
import br.gov.sp.fatec.agenda.dao.UsuarioDAO;

public class ListaUsuarios extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Usuários";
    private ActionBar actionBar;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);
        setTitle(TITULO_APPBAR);
        definirCorActionBar();

        UsuarioDAO dao = new UsuarioDAO(dbHelper);
        ListView listaDeUsers = findViewById(R.id.lista_usuarios);
        listaDeUsers.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dao.buscaUsuarios()));
        dbHelper.close();
    }

    private void definirCorActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));
    }
}
