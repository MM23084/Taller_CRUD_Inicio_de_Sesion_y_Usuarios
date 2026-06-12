# Taller CRUD - Inicio de Sesión y Usuarios

## 📱 Descripción

Aplicación móvil Android desarrollada en **Java** que implementa un sistema completo de gestión de clientes y servicios para un taller. La aplicación incluye funcionalidades CRUD (Crear, Leer, Actualizar, Eliminar) con autenticación de usuarios, almacenamiento persistente mediante Room Database, y una interfaz intuitiva con navegación por pestañas.

## 🚀 Características Principales

### 1. **Gestión de Clientes**
- ✅ Crear nuevos clientes
- ✅ Ver lista completa de clientes
- ✅ Editar información de clientes existentes
- ✅ Eliminar clientes
- ✅ Ver detalles completos de cada cliente

### 2. **Detalles del Cliente**
- 📞 Información de contacto (nombre, teléfono, email)
- 📍 Datos de ubicación (dirección, municipio)
- 📝 Notas adicionales
- **Acciones rápidas:**
  - Llamar directamente
  - Enviar mensaje por WhatsApp

### 3. **Gestión de Servicios**
- ✅ Crear servicios para clientes registrados
- ✅ Asignar tipo de servicio y descripción
- ✅ Definir precio y estado (Pendiente, En proceso, Completado)
- ✅ Editar servicios existentes
- ✅ Eliminar servicios con validaciones

### 4. **Dashboard Home**
- 📊 Contador de clientes registrados
- ⏳ Servicios pendientes
- 💰 Total de ingresos acumulados
- 🔗 Acceso rápido a clientes y servicios

### 5. **Características Adicionales**
- 📋 Agenda (placeholder para expansiones futuras)
- 📈 Reportes (placeholder para expansiones futuras)
- 🔒 Almacenamiento seguro en base de datos local

## 🛠️ Stack Tecnológico

| Componente | Tecnología |
|-----------|-----------|
| **Lenguaje** | Java 11 |
| **Plataforma** | Android 36 (API 36) |
| **Mínimo Soportado** | Android 7.0 (API 24) |
| **Base de Datos** | Room (SQLite) |
| **UI Framework** | AndroidX, Material Design 3 |
| **Arquitectura** | Repository Pattern + LiveData |
| **Build Tool** | Gradle 8+ |

## 📦 Dependencias Principales

```gradle
// AndroidX y Material Design
- androidx.appcompat
- com.google.android.material
- androidx.activity
- androidx.constraintlayout

// Base de Datos
- androidx.room:room-runtime
- androidx.room:room-compiler

// Lifecycle
- androidx.lifecycle:lifecycle-livedata
- androidx.lifecycle:lifecycle-viewmodel

// UI Components
- androidx.recyclerview
- androidx.cardview
```

## 🏗️ Estructura del Proyecto

```
app/
├── src/main/java/com/example/taller/
│   ├── data/
│   │   ├── AppDatabase.java          # Configuración de Room Database
│   │   ├── Cliente.java              # Entity - Modelo de Cliente
│   │   ├── Servicio.java             # Entity - Modelo de Servicio
│   │   ├── ClienteDao.java           # Data Access Object para Clientes
│   │   ├── ServicioDao.java          # Data Access Object para Servicios
│   │   └── AppRepository.java        # Repository Pattern
│   │
│   └── ui/
│       ├── MainActivity.java         # Activity principal con navegación
│       ├── HomeFragment.java         # Dashboard con estadísticas
│       ├── ClientesFragment.java     # Gestión de clientes
│       ├── ServiciosFragment.java    # Gestión de servicios
│       ├── ClienteDetailActivity.java # Detalles y acciones de cliente
│       ├── PlaceholderFragment.java  # Plantilla para futuras vistas
│       ├── dialogs/
│       │   ├── ClienteDialogFragment.java    # Diálogo CRUD de clientes
│       │   └── ServicioDialogFragment.java   # Diálogo CRUD de servicios
│       └── adapters/
│           ├── ClienteAdapter.java   # RecyclerView para clientes
│           └── ServicioAdapter.java  # RecyclerView para servicios
│
└── src/main/res/
    ├── layout/                       # Archivos XML de interfaz
    ├── drawable/                     # Recursos gráficos
    ├── values/                       # Strings, colores, estilos
```

## 💾 Modelos de Datos

### Cliente
```java
@Entity(tableName = "clientes")
public class Cliente {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre;
    public String telefono;
    public String email;
    public String direccion;
    public String municipio;
    public String notas;
}
```

### Servicio
```java
@Entity(tableName = "servicios")
public class Servicio {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int clienteId;
    public String clienteNombre;
    public String tipoServicio;
    public String descripcion;
    public double manoObra;
    public double costo;
    public String fecha;
    public String estado;  // "Pendiente", "En proceso", "Completado"
    
    public double getTotal() {
        return manoObra + costo;
    }
}
```

## 🔄 Patrones de Arquitectura

### Repository Pattern
La clase `AppRepository` centraliza el acceso a datos, proporcionando métodos observables con LiveData:
- `getAllClientes()` - LiveData observable
- `countClientes()` - Contador de clientes
- `insertCliente()`, `updateCliente()`, `deleteCliente()`
- `getAllServicios()` - LiveData observable
- `countPendientes()` - Servicios pendientes
- `totalIngresos()` - Ingresos totales

### Navigation Pattern
Uso de Bottom Navigation View para navegación entre fragmentos principales

### MVVM Concepts
Implementación de ViewModel y LiveData para gestión de ciclo de vida y datos

## 🚀 Instalación y Configuración

### Requisitos Previos
- Android Studio (versión reciente)
- JDK 11 o superior
- SDK de Android 36 instalado

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/MM23084/Taller_CRUD_Inicio_de_Sesion_y_Usuarios.git
   cd Taller_CRUD_Inicio_de_Sesion_y_Usuarios
   ```

2. **Abrir en Android Studio**
   - File → Open → Seleccionar la carpeta del proyecto

3. **Sincronizar Gradle**
   - Android Studio sincronizará automáticamente las dependencias

4. **Ejecutar la aplicación**
   - Conectar dispositivo Android o usar emulador
   - Click en "Run" (Shift + F10)

## 📱 Uso de la Aplicación

### Gestión de Clientes
1. Navegar a la pestaña "Clientes"
2. Presionar el botón flotante (+) para agregar nuevo cliente
3. Completar formulario con datos del cliente
4. Para editar o eliminar: mantener presionado el cliente en la lista

### Gestión de Servicios
1. Navegar a la pestaña "Servicios"
2. Presionar el botón flotante (+) para crear nuevo servicio
3. Seleccionar cliente asociado
4. Definir tipo, descripción, costos y estado
5. Para editar o eliminar: mantener presionado el servicio

### Dashboard
- Visualizar estadísticas en tiempo real
- Acceso rápido a clientes y servicios
- Total de ingresos acumulados

### Detalles de Cliente
- Visualizar toda la información del cliente
- Realizar llamadas directas
- Enviar mensajes por WhatsApp
- Ver servicios asociados

## 🔐 Validaciones

- ✅ Nombre de cliente es obligatorio
- ✅ No se pueden eliminar servicios "En proceso"
- ✅ Debe existir al menos un cliente antes de crear servicios
- ✅ Validación de formatos de email (opcional)
- ✅ Campos numéricos validados

## 🎨 Interfaz de Usuario

- **Tema:** Material Design 3
- **Colores:** 
  - Verde (#4CAF50) - Servicios Completados
  - Naranja (#FF9800) - Servicios En Proceso
  - Gris (#9E9E9E) - Servicios Pendientes

- **Componentes:**
  - RecyclerView para listados
  - CardView para items
  - FloatingActionButton para acciones principales
  - Dialog Fragments para formularios

## 🔄 Flujo de Datos

```
UI (Activities/Fragments)
    ↓
Repository (AppRepository)
    ↓
DAO (ClienteDao, ServicioDao)
    ↓
Room Database (SQLite)
```

## 🐛 Troubleshooting

### La app no inicia
- Verificar que el mínimo SDK sea 24 o superior
- Limpiar caché de Gradle: Build → Clean Project

### Errores de base de datos
- Eliminar datos de la app: Settings → Apps → Borrar datos
- Desinstalar y reinstalar

### Permisos de llamadas no funcionan
- Conceder permisos en Configuración del dispositivo
- Re-ejecutar la app

## 🚧 Expansiones Futuras

- [ ] Implementar pantalla de login/autenticación
- [ ] Agregar funcionalidades en Agenda
- [ ] Crear reportes PDF/Excel
- [ ] Integración con servidor en la nube
- [ ] Backup automático de datos
- [ ] Soporte para múltiples usuarios
- [ ] Búsqueda y filtrados avanzados
- [ ] Estadísticas gráficas

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo licencia MIT.

## 👤 Autor

**MM23084** - Desarrollo inicial

## 📞 Contacto y Soporte

Para reportar bugs o sugerencias, abre un issue en el repositorio.

---

**Última actualización:** Junio 2026  
**Estado:** En desarrollo activo
