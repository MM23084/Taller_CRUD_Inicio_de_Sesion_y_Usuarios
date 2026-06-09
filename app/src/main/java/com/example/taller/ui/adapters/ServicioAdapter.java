package com.example.taller.ui.adapters;

import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taller.R;
import com.example.taller.data.Servicio;
import java.util.List;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.VH> {

    public interface OnServicioLongClick { void onLongClick(Servicio s); }

    private List<Servicio> data;
    private final OnServicioLongClick longClickListener;

    public ServicioAdapter(List<Servicio> data, OnServicioLongClick listener) {
        this.data = data;
        this.longClickListener = listener;
    }

    public void setData(List<Servicio> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_servicio, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Servicio s = data.get(position);
        holder.tvCliente.setText(s.clienteNombre);
        holder.tvTipo.setText(s.tipoServicio);
        holder.tvDescripcion.setText(s.descripcion);
        holder.tvTotal.setText(String.format("$%.2f", s.getTotal()));
        holder.tvFecha.setText(s.fecha);
        holder.tvEstado.setText(s.estado);

        switch (s.estado) {
            case "Completado":
                holder.tvEstado.setTextColor(Color.parseColor("#4CAF50")); break;
            case "En proceso":
                holder.tvEstado.setTextColor(Color.parseColor("#FF9800")); break;
            default:
                holder.tvEstado.setTextColor(Color.parseColor("#9E9E9E")); break;
        }

        holder.itemView.setOnLongClickListener(v -> {
            longClickListener.onLongClick(s);
            return true;
        });
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvCliente, tvTipo, tvDescripcion, tvTotal, tvFecha, tvEstado;
        VH(View v) {
            super(v);
            tvCliente     = v.findViewById(R.id.tv_cliente);
            tvTipo        = v.findViewById(R.id.tv_tipo);
            tvDescripcion = v.findViewById(R.id.tv_descripcion);
            tvTotal       = v.findViewById(R.id.tv_total);
            tvFecha       = v.findViewById(R.id.tv_fecha);
            tvEstado      = v.findViewById(R.id.tv_estado);
        }
    }
}