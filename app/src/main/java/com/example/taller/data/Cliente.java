package com.example.taller.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "clientes")
public class Cliente implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre;
    public String telefono;
    public String email;
    public String direccion;
    public String municipio;
    public String notas;
}