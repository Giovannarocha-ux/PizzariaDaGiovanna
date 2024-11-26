package com.example.pizzariadagiovanna;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SaboresAdapter extends RecyclerView.Adapter<SaboresAdapter.SaborViewHolder> {

    private final List<Sabor> sabores;
    private int selectedPosition = -1;

    public SaboresAdapter(List<Sabor> sabores) {
        this.sabores = sabores;
    }

    @NonNull
    @Override
    public SaborViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sabor, parent, false);
        return new SaborViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaborViewHolder holder, int position) {
        Sabor sabor = sabores.get(position);
        holder.tvNome.setText(sabor.getNome());
        holder.ivImagem.setImageResource(sabor.getImagemResId());

        holder.radioButton.setChecked(position == selectedPosition);
        holder.radioButton.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged(); // Atualiza a seleção
        });
    }

    @Override
    public int getItemCount() {
        return sabores.size();
    }

    public String getSaborSelecionado() {
        if (selectedPosition != -1) {
            return sabores.get(selectedPosition).getNome();
        }
        return null;
    }

    public void limparSelecao() {
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    public static class SaborViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome;
        ImageView ivImagem;
        RadioButton radioButton;

        public SaborViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNome);
            ivImagem = itemView.findViewById(R.id.ivImagem);
            radioButton = itemView.findViewById(R.id.radioButton);
        }
    }
}
