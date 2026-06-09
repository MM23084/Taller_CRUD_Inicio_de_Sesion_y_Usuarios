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

public class ClienteDialogFragment extends DialogFragment {

    public interface OnSaveListener { void onSave(Cliente c); }

    private static final String ARG = "cliente";
    private OnSaveListener listener;

    public static ClienteDialogFragment newInstance(@Nullable Cliente c) {
        ClienteDialogFragment f = new ClienteDialogFragment();
        if (c != null) {
            Bundle b = new Bundle();
            b.putSerializable(ARG, c);
            f.setArguments(b);
        }
        return f;
    }

    public void setOnSaveListener(OnSaveListener l) { this.listener = l; }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_cliente, null);

        Cliente existing = getArguments() != null
                ? (Cliente) getArguments().getSerializable(ARG) : null;

        TextView tvTitle    = v.findViewById(R.id.tv_title);
        EditText etNombre   = v.findViewById(R.id.et_nombre);
        EditText etTelefono = v.findViewById(R.id.et_telefono);
        EditText etEmail    = v.findViewById(R.id.et_email);
        EditText etDireccion= v.findViewById(R.id.et_direccion);
        EditText etMunicipio= v.findViewById(R.id.et_municipio);
        EditText etNotas    = v.findViewById(R.id.et_notas);
        Button  btnCancelar = v.findViewById(R.id.btn_cancelar);
        Button  btnGuardar  = v.findViewById(R.id.btn_guardar);

        if (existing != null) {
            tvTitle.setText("Editar Cliente");
            etNombre.setText(existing.nombre);
            etTelefono.setText(existing.telefono);
            etEmail.setText(existing.email);
            etDireccion.setText(existing.direccion);
            etMunicipio.setText(existing.municipio);
            etNotas.setText(existing.notas);
        } else {
            tvTitle.setText("Nuevo Cliente");
        }

        Dialog dialog = new Dialog(requireContext(), R.style.DialogTheme);
        dialog.setContentView(v);
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        btnCancelar.setOnClickListener(x -> dismiss());

        btnGuardar.setOnClickListener(x -> {
            String nombre = etNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                etNombre.setError("Nombre requerido");
                return;
            }
            Cliente c = existing != null ? existing : new Cliente();
            c.nombre    = nombre;
            c.telefono  = etTelefono.getText().toString().trim();
            c.email     = etEmail.getText().toString().trim();
            c.direccion = etDireccion.getText().toString().trim();
            c.municipio = etMunicipio.getText().toString().trim();
            c.notas     = etNotas.getText().toString().trim();
            if (listener != null) listener.onSave(c);
            dismiss();
        });

        return dialog;
    }
}
