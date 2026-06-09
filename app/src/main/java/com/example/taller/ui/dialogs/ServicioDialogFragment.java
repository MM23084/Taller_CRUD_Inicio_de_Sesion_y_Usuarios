package com.example.taller.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.taller.R;
import com.example.taller.data.Cliente;
import com.example.taller.data.Servicio;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServicioDialogFragment extends DialogFragment {

    public interface OnSaveListener { void onSave(Servicio s); }

    private OnSaveListener listener;

    public static ServicioDialogFragment newInstance(
            @Nullable Servicio s, List<Cliente> clientes) {
        ServicioDialogFragment f = new ServicioDialogFragment();
        Bundle b = new Bundle();
        if (s != null) b.putSerializable("servicio", s);
        int[] ids = new int[clientes.size()];
        String[] nombres = new String[clientes.size()];
        for (int i = 0; i < clientes.size(); i++) {
            ids[i] = clientes.get(i).id;
            nombres[i] = clientes.get(i).nombre;
        }
        b.putIntArray("clienteIds", ids);
        b.putStringArray("clienteNombres", nombres);
        f.setArguments(b);
        return f;
    }

    public void setOnSaveListener(OnSaveListener l) { this.listener = l; }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_servicio, null);

        Bundle args = getArguments();
        Servicio existing = args != null ? (Servicio) args.getSerializable("servicio") : null;
        int[] ids        = args != null ? args.getIntArray("clienteIds") : new int[0];
        String[] nombres = args != null ? args.getStringArray("clienteNombres") : new String[0];

        TextView tvTitle       = v.findViewById(R.id.tv_title);
        Spinner  spCliente     = v.findViewById(R.id.sp_cliente);
        Spinner  spTipo        = v.findViewById(R.id.sp_tipo);
        Spinner  spEstado      = v.findViewById(R.id.sp_estado);
        EditText etDescripcion = v.findViewById(R.id.et_descripcion);
        EditText etManoObra    = v.findViewById(R.id.et_mano_obra);
        EditText etMateriales  = v.findViewById(R.id.et_materiales);
        Button   btnCancelar   = v.findViewById(R.id.btn_cancelar);
        Button   btnGuardar    = v.findViewById(R.id.btn_guardar);

        // Spinner clientes
        ArrayAdapter<String> clienteAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, nombres);
        clienteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCliente.setAdapter(clienteAdapter);

        // Spinner tipo servicio
        String[] tipos = {"Instalación", "Reparación", "Mantenimiento"};
        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, tipos);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(tipoAdapter);

        // Spinner estado
        String[] estados = {"Pendiente", "En proceso", "Completado"};
        ArrayAdapter<String> estadoAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, estados);
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(estadoAdapter);

        if (existing != null) {
            tvTitle.setText("Editar Servicio");
            etDescripcion.setText(existing.descripcion);
            etManoObra.setText(String.valueOf(existing.manoDeObra));
            etMateriales.setText(String.valueOf(existing.materiales));
            for (int i = 0; i < nombres.length; i++)
                if (nombres[i].equals(existing.clienteNombre)) spCliente.setSelection(i);
            for (int i = 0; i < tipos.length; i++)
                if (tipos[i].equals(existing.tipoServicio)) spTipo.setSelection(i);
            for (int i = 0; i < estados.length; i++)
                if (estados[i].equals(existing.estado)) spEstado.setSelection(i);
        } else {
            tvTitle.setText("Nuevo Servicio");
        }

        Dialog dialog = new Dialog(requireContext(), R.style.DialogTheme);
        dialog.setContentView(v);
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        btnCancelar.setOnClickListener(x -> dismiss());

        btnGuardar.setOnClickListener(x -> {
            int pos = spCliente.getSelectedItemPosition();
            Servicio s = existing != null ? existing : new Servicio();
            s.clienteId     = ids[pos];
            s.clienteNombre = nombres[pos];
            s.tipoServicio  = spTipo.getSelectedItem().toString();
            s.estado        = spEstado.getSelectedItem().toString();
            s.descripcion   = etDescripcion.getText().toString().trim();
            try {
                s.manoDeObra = Double.parseDouble(etManoObra.getText().toString());
            } catch (NumberFormatException e) { s.manoDeObra = 0; }
            try {
                s.materiales = Double.parseDouble(etMateriales.getText().toString());
            } catch (NumberFormatException e) { s.materiales = 0; }
            if (s.fecha == null || s.fecha.isEmpty())
                s.fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(new Date());
            if (listener != null) listener.onSave(s);
            dismiss();
        });

        return dialog;
    }
}
