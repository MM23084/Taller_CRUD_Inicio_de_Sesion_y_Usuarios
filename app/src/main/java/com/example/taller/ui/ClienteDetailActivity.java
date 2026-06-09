package com.example.taller.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.taller.R;
import com.example.taller.data.Cliente;

public class ClienteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detail);

        Cliente c = (Cliente) getIntent().getSerializableExtra("cliente");
        if (c == null) { finish(); return; }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(c.nombre);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ((TextView) findViewById(R.id.tv_nombre)).setText(c.nombre);
        ((TextView) findViewById(R.id.tv_telefono)).setText(c.telefono);
        ((TextView) findViewById(R.id.tv_email)).setText(c.email);
        ((TextView) findViewById(R.id.tv_direccion)).setText(c.direccion);
        ((TextView) findViewById(R.id.tv_municipio)).setText(c.municipio);
        ((TextView) findViewById(R.id.tv_notas)).setText(c.notas);

        findViewById(R.id.btn_llamar).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + c.telefono));
            startActivity(intent);
        });

        findViewById(R.id.btn_whatsapp).setOnClickListener(v -> {
            String url = "https://wa.me/503" + c.telefono.replaceAll("[^0-9]", "");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}