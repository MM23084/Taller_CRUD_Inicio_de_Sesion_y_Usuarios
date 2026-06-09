package com.example.taller.ui;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.taller.R;
import com.example.taller.data.AppRepository;
import com.example.taller.data.Cliente;
import com.example.taller.data.Servicio;
import com.example.taller.ui.adapters.ServicioAdapter;
import com.example.taller.ui.dialogs.ServicioDialogFragment;
import java.util.ArrayList;
import java.util.List;

public class ServiciosFragment extends Fragment {

    private AppRepository repo;
    private ServicioAdapter adapter;
    private List<Cliente> clientesActuales = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_servicios, container, false);

        repo = new AppRepository(requireActivity().getApplication());

        RecyclerView rv = v.findViewById(R.id.rv_servicios);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView tvEmpty = v.findViewById(R.id.tv_empty);

        adapter = new ServicioAdapter(new ArrayList<>(), this::showContextMenu);
        rv.setAdapter(adapter);

        repo.getAllServicios().observe(getViewLifecycleOwner(), servicios -> {
            adapter.setData(servicios);
            tvEmpty.setVisibility(servicios.isEmpty() ? View.VISIBLE : View.GONE);
        });

        repo.getAllClientes().observe(getViewLifecycleOwner(), clientes ->
                clientesActuales = clientes);

        FloatingActionButton fab = v.findViewById(R.id.fab_add);
        fab.setOnClickListener(x -> {
            if (clientesActuales.isEmpty()) {
                Toast.makeText(getContext(),
                        "Debes registrar al menos un cliente primero", Toast.LENGTH_LONG).show();
                return;
            }
            ServicioDialogFragment dialog = ServicioDialogFragment.newInstance(null, clientesActuales);
            dialog.setOnSaveListener(s -> repo.insertServicio(s));
            dialog.show(getChildFragmentManager(), "nuevo_servicio");
        });

        return v;
    }

    private void showContextMenu(Servicio servicio) {
        String[] opciones = {"Editar", "Eliminar"};
        new android.app.AlertDialog.Builder(requireContext())
                .setItems(opciones, (dialog, which) -> {
                    if (which == 0) {
                        ServicioDialogFragment df = ServicioDialogFragment.newInstance(servicio, clientesActuales);
                        df.setOnSaveListener(s -> repo.updateServicio(s));
                        df.show(getChildFragmentManager(), "editar_servicio");
                    } else {
                        if ("En proceso".equals(servicio.estado)) {
                            new android.app.AlertDialog.Builder(requireContext())
                                    .setTitle("Eliminar servicio")
                                    .setMessage("No se puede eliminar un servicio en proceso")
                                    .setPositiveButton("Cerrar", null)
                                    .show();
                        } else {
                            new android.app.AlertDialog.Builder(requireContext())
                                    .setTitle("Eliminar servicio")
                                    .setMessage("Tipo: " + servicio.tipoServicio +
                                            "\nDescripción: " + servicio.descripcion +
                                            "\nEstado: " + servicio.estado +
                                            "\nCliente: " + servicio.clienteNombre)
                                    .setPositiveButton("Eliminar", (d, w) -> repo.deleteServicio(servicio))
                                    .setNegativeButton("Cancelar", null)
                                    .show();
                        }
                    }
                }).show();
    }
}