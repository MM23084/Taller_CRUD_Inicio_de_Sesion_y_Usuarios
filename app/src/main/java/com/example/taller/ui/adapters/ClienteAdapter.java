package com.example.taller.ui.adapters;

import android.content.Intent;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taller.R;
import com.example.taller.data.Cliente;
import com.example.taller.ui.ClienteDetailActivity;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.VH> {

    public interface OnClienteClick { void onClick(Cliente c); }

    private List<Cliente> data;
    private final OnClienteClick onClickListener;
    private final OnClienteClick onLongClickListener;

    public ClienteAdapter(List<Cliente> data, OnClienteClick onClick, OnClienteClick onLongClick) {
        this.data = data;
        this.onClickListener = onClick;
        this.onLongClickListener = onLongClick;
    }

    public void setData(List<Cliente> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Cliente c = data.get(position);
        holder.tvNombre.setText(c.nombre);
        holder.tvTelefono.setText(c.telefono);
        holder.tvEmail.setText(c.email);
        holder.tvMunicipio.setText(c.municipio);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ClienteDetailActivity.class);
            intent.putExtra("cliente", c);
            v.getContext().startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            onLongClickListener.onClick(c);
            return true;
        });
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTelefono, tvEmail, tvMunicipio;
        VH(View v) {
            super(v);
            tvNombre    = v.findViewById(R.id.tv_nombre);
            tvTelefono  = v.findViewById(R.id.tv_telefono);
            tvEmail     = v.findViewById(R.id.tv_email);
            tvMunicipio = v.findViewById(R.id.tv_municipio);
        }
    }
}
