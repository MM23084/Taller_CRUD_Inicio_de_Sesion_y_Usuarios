package com.example.taller.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.taller.ui.adapters.ClienteAdapter;
import com.example.taller.ui.dialogs.ClienteDialogFragment;
import java.util.ArrayList;

public class ClientesFragment extends Fragment {

    private AppRepository repo;
    private ClienteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_clientes, container, false);

        repo = new AppRepository(requireActivity().getApplication());

        RecyclerView rv = v.findViewById(R.id.rv_clientes);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ClienteAdapter(new ArrayList<>(), cliente -> {}, this::showContextMenu);
        rv.setAdapter(adapter);

        TextView tvEmpty = v.findViewById(R.id.tv_empty);
        EditText etSearch = v.findViewById(R.id.et_search);

        repo.getAllClientes().observe(getViewLifecycleOwner(), clientes -> {
            adapter.setData(clientes);
            tvEmpty.setVisibility(clientes.isEmpty() ? View.VISIBLE : View.GONE);
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {
                String query = s.toString().trim();
                if (query.isEmpty()) {
                    repo.getAllClientes().observe(getViewLifecycleOwner(), list -> {
                        adapter.setData(list);
                        tvEmpty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
                    });
                } else {
                    repo.searchClientes(query).observe(getViewLifecycleOwner(), list -> {
                        adapter.setData(list);
                        tvEmpty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
                    });
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        FloatingActionButton fab = v.findViewById(R.id.fab_add);
        fab.setOnClickListener(x -> {
            ClienteDialogFragment dialog = ClienteDialogFragment.newInstance(null);
            dialog.setOnSaveListener(cliente -> repo.insertCliente(cliente));
            dialog.show(getChildFragmentManager(), "nuevo_cliente");
        });

        return v;
    }

    private void showContextMenu(Cliente cliente) {
        String[] opciones = {"Editar", "Eliminar"};
        new android.app.AlertDialog.Builder(requireContext())
                .setItems(opciones, (dialog, which) -> {
                    if (which == 0) {
                        ClienteDialogFragment df = ClienteDialogFragment.newInstance(cliente);
                        df.setOnSaveListener(c -> repo.updateCliente(c));
                        df.show(getChildFragmentManager(), "editar_cliente");
                    } else {
                        new android.app.AlertDialog.Builder(requireContext())
                                .setTitle("Eliminar cliente")
                                .setMessage("¿Eliminar a " + cliente.nombre + "?")
                                .setPositiveButton("Eliminar", (d, w) -> repo.deleteCliente(cliente))
                                .setNegativeButton("Cancelar", null)
                                .show();
                    }
                }).show();
    }
}
