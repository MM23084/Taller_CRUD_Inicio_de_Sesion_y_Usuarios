package com.example.taller.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ServicioDao {

    @Insert
    void insert(Servicio servicio);

    @Update
    void update(Servicio servicio);

    @Delete
    void delete(Servicio servicio);

    @Query("SELECT * FROM servicios ORDER BY id DESC")
    LiveData<List<Servicio>> getAll();

    @Query("SELECT COUNT(*) FROM servicios WHERE estado = 'Pendiente'")
    LiveData<Integer> countPendientes();

    @Query("SELECT SUM(manoDeObra + materiales) FROM servicios WHERE estado = 'Completado'")
    LiveData<Double> totalIngresos();
}