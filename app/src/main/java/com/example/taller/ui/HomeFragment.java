package com.example.taller.ui;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.taller.R;
import com.example.taller.data.AppRepository;
import android.app.Application;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        AppRepository repo = new AppRepository((Application) requireContext().getApplicationContext());

        TextView tvClientes   = v.findViewById(R.id.tv_count_clientes);
        TextView tvPendientes = v.findViewById(R.id.tv_count_pendientes);
        TextView tvIngresos   = v.findViewById(R.id.tv_ingresos);

        repo.countClientes().observe(getViewLifecycleOwner(), n ->
                tvClientes.setText(n != null ? String.valueOf(n) : "0"));

        repo.countPendientes().observe(getViewLifecycleOwner(), n ->
                tvPendientes.setText(n != null ? String.valueOf(n) : "0"));

        repo.totalIngresos().observe(getViewLifecycleOwner(), total ->
                tvIngresos.setText(String.format("$%.2f", total != null ? total : 0.0)));

        v.findViewById(R.id.btn_quick_clientes).setOnClickListener(x -> navigateTo(R.id.nav_clientes));
        v.findViewById(R.id.btn_quick_servicios).setOnClickListener(x -> navigateTo(R.id.nav_servicios));

        return v;
    }

    private void navigateTo(int navId) {
        if (getActivity() != null) {
            BottomNavigationView nav = getActivity().findViewById(R.id.bottom_nav);
            nav.setSelectedItemId(navId);
        }
    }
}