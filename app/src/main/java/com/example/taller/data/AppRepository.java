package com.example.taller.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppRepository {

    private final ClienteDao clienteDao;
    private final ServicioDao servicioDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public AppRepository(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        clienteDao = db.clienteDao();
        servicioDao = db.servicioDao();
    }

    // --- Clientes ---
    public LiveData<List<Cliente>> getAllClientes() { return clienteDao.getAll(); }
    public LiveData<List<Cliente>> searchClientes(String q) { return clienteDao.search(q); }
    public LiveData<Integer> countClientes() { return clienteDao.count(); }
    public void insertCliente(Cliente c) { executor.execute(() -> clienteDao.insert(c)); }
    public void updateCliente(Cliente c) { executor.execute(() -> clienteDao.update(c)); }
    public void deleteCliente(Cliente c) { executor.execute(() -> clienteDao.delete(c)); }

    // --- Servicios ---
    public LiveData<List<Servicio>> getAllServicios() { return servicioDao.getAll(); }
    public LiveData<Integer> countPendientes() { return servicioDao.countPendientes(); }
    public LiveData<Double> totalIngresos() { return servicioDao.totalIngresos(); }
    public void insertServicio(Servicio s) { executor.execute(() -> servicioDao.insert(s)); }
    public void updateServicio(Servicio s) { executor.execute(() -> servicioDao.update(s)); }
    public void deleteServicio(Servicio s) { executor.execute(() -> servicioDao.delete(s)); }
}