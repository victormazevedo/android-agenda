package br.gov.sp.fatec.agenda.ui.activity;

import android.widget.EditText;
import android.widget.Spinner;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.dto.EnderecoAlunoDTO;
import br.gov.sp.fatec.agenda.model.Aluno;
import br.gov.sp.fatec.agenda.model.Endereco;

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoTelefone;
    private final EditText campoEmail;
    private final Spinner spinnerGenero;
    private final EditText campoCep;
    private final EditText campoLogradouro;
    private final EditText campoComplemento;
    private final EditText campoBairro;
    private final EditText campoCidade;
    private final EditText campoUf;


    private Aluno aluno;
    private Endereco endereco;
    private EnderecoAlunoDTO enderecoAlunoDTO;

    public FormularioHelper(FormularioAlunoActivity activity) {
        campoNome = activity.findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = activity.findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = activity.findViewById(R.id.activity_formulario_aluno_email);
        spinnerGenero = activity.findViewById(R.id.activity_formulario_aluno_spinner);
        campoCep = activity.findViewById(R.id.activity_formulario_aluno_cep);
        campoLogradouro = activity.findViewById(R.id.activity_formulario_aluno_logradouro);
        campoComplemento = activity.findViewById(R.id.activity_formulario_aluno_complemento);
        campoBairro = activity.findViewById(R.id.activity_formulario_aluno_bairro);
        campoCidade = activity.findViewById(R.id.activity_formulario_aluno_cidade);
        campoUf = activity.findViewById(R.id.activity_formulario_aluno_uf);
        aluno = new Aluno();
        endereco = new Endereco();
    }

    public Aluno getAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
        aluno.setGenero(String.valueOf(spinnerGenero.getSelectedItem()));
        return aluno;
    }

    public Endereco getEndereco() {
        endereco.setCep(campoCep.getText().toString());
        endereco.setLogradouro(campoLogradouro.getText().toString());
        endereco.setComplemento(campoComplemento.getText().toString());
        endereco.setBairro(campoBairro.getText().toString());
        endereco.setLocalidade(campoCidade.getText().toString());
        endereco.setUf(campoUf.getText().toString());
        return endereco;
    }

    public void preencheFormulario(EnderecoAlunoDTO aluno) {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
        campoCep.setText(aluno.getCep());
        campoLogradouro.setText(aluno.getLogradouro());
        campoComplemento.setText(aluno.getComplemento());
        campoBairro.setText(aluno.getBairro());
        campoCidade.setText(aluno.getLocalidade());
        campoUf.setText(aluno.getUf());
        this.enderecoAlunoDTO = aluno;
    }

    public void preencheEnderecoFormulario(Endereco endereco) {
        campoCep.setText(endereco.getCep());
        campoLogradouro.setText(endereco.getLogradouro());
        campoComplemento.setText(endereco.getComplemento());
        campoBairro.setText(endereco.getBairro());
        campoCidade.setText(endereco.getLocalidade());
        campoUf.setText(endereco.getUf());
        this.endereco = endereco;
    }
}