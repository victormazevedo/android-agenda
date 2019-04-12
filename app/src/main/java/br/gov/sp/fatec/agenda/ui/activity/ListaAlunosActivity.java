package br.gov.sp.fatec.agenda.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.dao.AlunoDAO;
import br.gov.sp.fatec.agenda.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Alunos";

    private ListView listaAlunos;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITULO_APPBAR);
        definirCorActionBar();

        listaAlunos = findViewById(R.id.activity_lista_alunos_listview);
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent vaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
                vaiProFormulario.putExtra("aluno", aluno);
                startActivity(vaiProFormulario);
            }
        });

        iniciarNavigationDrawer();
        configuraFabNovoAluno();

        registerForContextMenu(listaAlunos);//menu de contexto para abrir a opcão de Deletar ao segurar
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

                new AlertDialog.Builder(ListaAlunosActivity.this)
                        .setTitle("Deletar Aluno")
                        .setMessage("Tem certeza que deseja deletar este aluno?")
                        .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                                dao.deleta(aluno);
                                dao.close();
                                configuraLista();
                            }
                        })
                        .setNegativeButton("não", null)
                        .show();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        configuraLista();
    }

    private void definirCorActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));
    }

    private void configuraFabNovoAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioAlunoActivity();
            }
        });
    }

    private void iniciarNavigationDrawer() {
        drawerLayout = findViewById(R.id.activity_main);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.activity_lista_alunos_navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.account) {
                    Toast.makeText(ListaAlunosActivity.this, "Minha conta", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.settings) {
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(ListaAlunosActivity.this, ConfiguracaoActivity.class));
                } else if (id == R.id.info) {
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(ListaAlunosActivity.this, AboutActivity.class));
                } else if (id == R.id.add_aluno) {
                    drawerLayout.closeDrawers();
                    abreFormularioAlunoActivity();
                } else if (id == R.id.avisos) {
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(ListaAlunosActivity.this, ListaAvisosActivity.class));
                }
                return false;
            }
        });
    }

    private void abreFormularioAlunoActivity() {
        startActivity(new Intent(this, FormularioAlunoActivity.class));
    }

    private void configuraLista() {
        AlunoDAO dao = new AlunoDAO(this);
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        listaDeAlunos.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dao.buscaAlunos()));
        dao.close();
    }

}
