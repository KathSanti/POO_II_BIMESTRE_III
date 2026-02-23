# Sistema de Entregas SpeedFast

Proyecto formativo de Desarrollo Orientado a Objetos II, que simula un sistema logístico para la empresa **SpeedFast**, dedicada a la entrega de pedidos de comida, 
encomiendas y compras express. 

El sistema cuenta con una interfaz gráfica (GUI) e implementa programación concurrente (hilos) para simular motoristas trabajando en tiempo real, 
con persistencia de datos en una base de datos relacional (MySQL) mediante JDBC.

## Características Principales

* **Interfaz Gráfica (Swing):** Formularios para el registro de nuevos pedidos y una tabla dinámica (`JTable`) para visualizar el estado en tiempo real.
* **Concurrencia (Threads):** Uso de hilos para simular múltiples repartidores trabajando de forma simultánea.
* **Gestión de Colas:** Implementación de `PriorityBlockingQueue` para manejar los pedidos de forma segura entre los diferentes hilos (productor-consumidor).
* **Persistencia (JDBC):** Conexión a base de datos MySQL aplicando el patrón de diseño DAO (Data Access Object) para separar la lógica de negocio del acceso a datos.
* **Recuperación Automática:** Al iniciar, el sistema consulta a la base de datos y recupera los pedidos que quedaron en estado `PENDIENTE` para asignarlos automáticamente a la cola de entregas.

## Tecnologías Utilizadas

* **Lenguaje:** Java (JDK 18+)
* **Interfaz Gráfica:** Java Swing
* **Base de Datos:** MySQL
* **Gestor de Dependencias:** Maven (para descargar automáticamente el conector `mysql-connector-java`)
* **IDE:** IntelliJ IDEA

## Configuración e Instalación

### 1. Preparar la Base de Datos

Antes de ejecutar el proyecto, debes crear la base de datos y sus tablas en tu servidor MySQL local. Ejecuta el siguiente script SQL:

```sql
CREATE DATABASE IF NOT EXISTS speedfast_db;
USE speedfast_db;

CREATE TABLE repartidor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(150) NOT NULL,
    tipo VARCHAR(30) NOT NULL,      -- COMIDA | ENCOMIENDA | EXPRESS
    estado VARCHAR(20) NOT NULL     -- PENDIENTE | EN_REPARTO | ENTREGADO
);

CREATE TABLE entrega (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_repartidor INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id),
    FOREIGN KEY (id_repartidor) REFERENCES repartidor(id)
);

-- Insertar repartidores de prueba necesarios para los hilos
INSERT INTO repartidor (nombre) VALUES ('Juan Perez'), ('María Guerrero');
