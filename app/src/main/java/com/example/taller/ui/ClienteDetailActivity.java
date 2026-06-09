package com.example.taller.ui;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.taller.R;
import com.example.taller.data.Cliente;
import android.widget.Toast;

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
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + c.telefono));
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            }
        });

        findViewById(R.id.btn_whatsapp).setOnClickListener(v -> {
            String url = "https://wa.me/503" + c.telefono.replaceAll("[^0-9]", "");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso concedido, toca Llamar de nuevo", Toast.LENGTH_SHORT).show();

        }

    }
}