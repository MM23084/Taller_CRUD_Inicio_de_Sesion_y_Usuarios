package com.example.taller.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ClienteDao {

    @Insert
    void insert(Cliente cliente);

    @Update
    void update(Cliente cliente);

    @Delete
    void delete(Cliente cliente);

    @Query("SELECT * FROM clientes ORDER BY nombre ASC")
    LiveData<List<Cliente>> getAll();

    @Query("SELECT * FROM clientes WHERE nombre LIKE '%' || :query || '%' ORDER BY nombre ASC")
    LiveData<List<Cliente>> search(String query);

    @Query("SELECT COUNT(*) FROM clientes")
    LiveData<Integer> count();
}
