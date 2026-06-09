package com.example.taller.ui;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlaceholderFragment extends Fragment {
    private final String titulo;

    public PlaceholderFragment(String titulo) { this.titulo = titulo; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setText(titulo + "\n(Próximamente)");
        tv.setTextSize(20);
        tv.setGravity(android.view.Gravity.CENTER);
        tv.setTextColor(0xFFFFFFFF);
        tv.setBackgroundColor(0xFF1A2035);
        return tv;
    }
}
