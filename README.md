# AppClub - Reserva de canchas

Aplicación web para reservar canchas de un club.
Trabajo integrador de **Desarrollo Backend III**.

**Autor:** Perez Matias · **Año:** 2026

## Funcionalidades

**Socio**
- Se registra e inicia sesión.
- Elige un deporte, una cancha y una fecha, y reserva un turno desde una grilla.
- Ve sus reservas (próximas y finalizadas) y cancela las que todavía no empezaron.

**Administrador**
- ABM (alta, baja, modificación) de deportes y canchas.
- Ve todas las reservas de todos los socios. No reserva.

**Reglas de negocio**
- Turnos fijos de 90 minutos, de 08:00 a 23:00 (10 turnos por día).
- No se puede reservar el mismo turno de una cancha dos veces (control en el EJB y restricción `UNIQUE` en la base).
- No se pueden reservar turnos que ya pasaron.
- Un socio solo puede cancelar sus propias reservas.

## Tecnologías

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Plataforma | Jakarta EE 10 |
| Servidor | GlassFish 7 |
| Persistencia | JPA con EclipseLink |
| Lógica de negocio | EJB Stateless (patrón Session Facade) |
| Web | Servlets, JSP y JSTL |
| Base de datos | MySQL 8 |
| Seguridad | Sesiones HTTP, filtros (`@WebFilter`) y BCrypt (jBCrypt) |

## Arquitectura

Patrón MVC en capas:

```
Navegador  ->  Servlet (controlador)  ->  EJB Facade (negocio/datos)  ->  MySQL
                    |
                    +->  JSP (vista)
```

```
src/main/java/com/club
├── entidades   Usuario, Deporte, Cancha, Reserva (entidades JPA)
├── sesiones    AbstractFacade y un facade por entidad (EJB @Stateless)
├── servlets    AccesoServlet, DeporteServlet, CanchaServlet, ReservaServlet, AdminReservasServlet
├── filtros     FiltroAdmin, FiltroSocio (control de acceso por rol)
└── util        Clave (BCrypt), Acceso (usuario en sesión), Turnos (horarios)

sql/bdclub.sql          Script de creación de la base de datos

src/main/webapp
├── index.jsp
├── css/estilos.css
└── WEB-INF/vista
    ├── menu.jsp
    ├── acceso/    login.jsp, registro.jsp
    ├── deporte/   lista.jsp, form.jsp
    ├── cancha/    lista.jsp, form.jsp
    └── reserva/   reservar.jsp, mis_reservas.jsp, todas.jsp
```

## Modelo de datos

Base de datos `bdclub`:

| Tabla | Columnas principales |
|---|---|
| `usuario` | idusuario, nombre, correo (único), clave (hash BCrypt), rol (`SOCIO` o `ADMIN`) |
| `deporte` | iddeporte, nombre (único) |
| `cancha` | idcancha, nombre, deporte_iddeporte (FK a deporte) |
| `reserva` | idreserva, usuario_idusuario (FK), cancha_idcancha (FK), fecha, hora_inicio, hora_fin |

Relaciones: un deporte tiene muchas canchas, una cancha tiene muchas reservas y un usuario tiene muchas reservas.
`reserva` tiene `UNIQUE (cancha_idcancha, fecha, hora_inicio)`, que impide la doble reserva.

## Rutas

| Ruta | Quién | Qué hace |
|---|---|---|
| `/Registro`, `/Login`, `/Logout` | Todos | Alta de socio, inicio y cierre de sesión |
| `/Reservar` | Socio | Elegir deporte, cancha, fecha y reservar un turno |
| `/MisReservas`, `/CancelarReserva` | Socio | Ver y cancelar las propias reservas |
| `/ListarDeportes`, `/AgregarDeporte`, `/EditarDeporte`, `/EliminarDeporte` | Admin | ABM de deportes |
| `/ListarCanchas`, `/AgregarCancha`, `/EditarCancha`, `/EliminarCancha` | Admin | ABM de canchas |
| `/TodasLasReservas` | Admin | Ver todas las reservas |

El acceso por rol lo controlan `FiltroAdmin` y `FiltroSocio`: sin sesión redirigen al login, y con otro rol al inicio.

## Cómo ejecutarlo

### Requisitos
- JDK 21 (GlassFish 7)
- Apache NetBeans con GlassFish 7 configurado para usar ese JDK
- MySQL 8 y MySQL Workbench

### 1. Crear la base de datos
Abrir `sql/bdclub.sql` en MySQL Workbench y ejecutarlo completo.
El script crea el esquema `bdclub`, las cuatro tablas y algunos deportes y canchas de prueba.

> **Atención:** el script empieza con `DROP SCHEMA IF EXISTS bdclub`, por lo que borra la base si ya existía.

### 2. Configurar la conexión
Editar `src/main/setup/glassfish-resources.xml` y completar la contraseña de MySQL:

```xml
<property name="User" value="root"/>
<property name="Password" value="TU_CLAVE"/>
```

La URL de conexión usa `useSSL=false`, `allowPublicKeyRetrieval=true` y
`serverTimezone=America/Argentina/Buenos_Aires`.
El `persistence.xml` usa el data source `java:app/jdbc/bdclub`.

### 3. Ejecutar
1. Abrir el proyecto en NetBeans.
2. **Clean and Build** y luego **Run**.
3. Abrir `http://localhost:8080/AppClub/`.

### 4. Crear un administrador
Los usuarios que se registran son siempre `SOCIO`. Para crear un administrador, registrar un usuario
y cambiarle el rol en MySQL:

```sql
UPDATE usuario SET rol = 'ADMIN' WHERE correo = 'admin@club.com';
```

Después hay que volver a iniciar sesión.

