package com.example.taller.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(
        tableName = "servicios",
        foreignKeys = @ForeignKey(
                entity = Cliente.class,
                parentColumns = "id",
                childColumns = "clienteId",
                onDelete = ForeignKey.RESTRICT
        )
)
public class Servicio implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int clienteId;
    public String clienteNombre;
    public String tipoServicio;
    public String estado;
    public String descripcion;
    public double manoDeObra;
    public double materiales;
    public String fecha;

    public double getTotal() {
        return manoDeObra + materiales;
    }
}
