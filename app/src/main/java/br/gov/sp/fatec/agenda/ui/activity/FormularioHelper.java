package br.gov.sp.fatec.agenda.ui.activity;

import android.widget.EditText;
import android.widget.Spinner;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.model.Aluno;

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoTelefone;
    private final EditText campoEmail;
    private final Spinner spinnerGenero;

    private Aluno aluno;

    public FormularioHelper(FormularioAlunoActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = (EditText) activity.findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = (EditText) activity.findViewById(R.id.activity_formulario_aluno_email);
        spinnerGenero = activity.findViewById(R.id.activity_formulario_aluno_spinner);
        aluno = new Aluno();
    }

    public Aluno getAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
        aluno.setGenero(String.valueOf(spinnerGenero.getSelectedItem()));
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
        this.aluno = aluno;
    }
}