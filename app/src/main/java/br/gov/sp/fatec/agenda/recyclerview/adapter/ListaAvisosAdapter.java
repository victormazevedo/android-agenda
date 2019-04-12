package br.gov.sp.fatec.agenda.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import br.gov.sp.fatec.agenda.R;
import br.gov.sp.fatec.agenda.model.Aviso;
import br.gov.sp.fatec.agenda.recyclerview.adapter.listener.OnItemClickListener;

public class ListaAvisosAdapter extends RecyclerView.Adapter<ListaAvisosAdapter.AvisoViewHolder> {

    private final List<Aviso> avisos;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public ListaAvisosAdapter(Context context, List<Aviso> avisos) {
        this.context = context;
        this.avisos = avisos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ListaAvisosAdapter.AvisoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_aviso, parent, false);
        return new AvisoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(ListaAvisosAdapter.AvisoViewHolder holder, int position) {
        Aviso aviso = avisos.get(position);
        holder.vincula(aviso);
    }

    @Override
    public int getItemCount() {
        return avisos.size();
    }

    public void altera(int posicao, Aviso aviso) {
        avisos.set(posicao, aviso);
        notifyDataSetChanged();
    }

    public void remove(int posicao) {
        avisos.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public void troca(int posicaoInicial, int posicaoFinal) {
        Collections.swap(avisos, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    class AvisoViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private Aviso aviso;

        public AvisoViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(aviso, getAdapterPosition());
                }
            });
        }

        public void vincula(Aviso aviso) {
            this.aviso = aviso;
            preencheCampo(aviso);
        }

        private void preencheCampo(Aviso aviso) {
            titulo.setText(aviso.getTitulo());
            descricao.setText(aviso.getDescricao());
        }
    }

    public void adiciona(Aviso aviso) {
        avisos.add(aviso);
        notifyDataSetChanged();
    }

}
