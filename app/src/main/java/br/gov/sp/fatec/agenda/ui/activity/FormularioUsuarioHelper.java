package br.gov.sp.fatec.agenda.ui.activity;

import android.widget.TextView;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.model.Usuario;

public class FormularioUsuarioHelper {

    private final TextView campoNome;
    private final TextView campoTelefone;
    private final TextView campoEmail;
    private final TextView campoSenha;

    private Usuario usuario;

    public FormularioUsuarioHelper(FormularioUsuarioActivity activity) {
        campoNome = activity.findViewById(R.id.activity_formulario_usuario_nome);
        campoTelefone = activity.findViewById(R.id.activity_formulario_usuario_telefone);
        campoEmail = activity.findViewById(R.id.activity_formulario_usuario_email);
        campoSenha = activity.findViewById(R.id.activity_formulario_usuario_senha);

        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        usuario.setNome(campoNome.getText().toString());
        usuario.setTelefone(campoTelefone.getText().toString());
        usuario.setEmail(campoEmail.getText().toString());
        usuario.setSenha(campoSenha.getText().toString());
        return usuario;
    }

    public void preencheFormulario(Usuario usuario) {
        campoNome.setText(usuario.getNome());
        campoTelefone.setText(usuario.getTelefone());
        campoEmail.setText(usuario.getEmail());
        campoSenha.setText(usuario.getSenha());
        this.usuario = usuario;
    }
}
