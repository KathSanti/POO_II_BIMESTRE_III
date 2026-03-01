# SISTEMA DE GESTIÓN SPEEDFAST


**SpeedFast** es una aplicación de escritorio desarrollada en **Java** diseñada para gestionar las operaciones diarias de una empresa de despachos. El sistema permite administrar pedidos, repartidores y entregas a través de una interfaz gráfica, implementando una arquitectura orientada a objetos y persistencia de datos relacional.

## Características Principales
---

* **Gestión de Pedidos (CRUD):** Registro, actualización, visualización y eliminación de pedidos. Incluye filtros dinámicos por tipo de pedido, estado y repartidor asignado.
* **Gestión de Repartidores (CRUD):** Administración completa de la flota de motoristas.
* **Gestión de Entregas (CRUD):** Asignación manual de pedidos a repartidores, registrando automáticamente la fecha y hora de la transacción.
* **Entregas Automáticas (Multihilo):** Simulación de entregas en tiempo real utilizando hilos (`Threads`) y colas concurrentes (`PriorityBlockingQueue`). Los repartidores toman los pedidos pendientes automáticamente desde una "Zona de Carga" y actualizan su estado.
* **Validaciones de Integridad:** Prevención de eliminación de registros que mantienen dependencias (llaves foráneas) en la base de datos, con alertas claras para el usuario.

## Diseño del proyecto
---

* **Lenguaje:** Java
* **Interfaz Gráfica:** Java Swing (`JFrame`, `JTable`, `JComboBox`, `TableRowSorter` para filtros en vivo).
* **Base de Datos:** MySQL
* **Conexión a BD:** JDBC (`PreparedStatement`, `ResultSet`, manejo de transacciones seguras con `try-with-resources`).
* **Arquitectura:** * **Patrón DAO (Data Access Object):** Separación estricta entre la lógica de negocio y el acceso a la base de datos mediante interfaces (`PedidoDAO`, `RepartidorDAO`,

## Estructura proyecto
---

* `/conexion`       : Configuración centralizada de la conexión JDBC (`ConexionBD`).
* `/controladores`  : Lógica central del sistema, gestor de colas (`ZonadeCarga`) y enumeradores (`Estado`, `TipoPedido`).
* `/dao`            : Implementación de las operaciones CRUD hacia la base de datos MySQL.
* `/interfacesDAO`  : Contratos e interfaces para la persistencia de datos.
* `/modelo`         : Clases base del dominio de la aplicación (`Pedido`, `Repartidor`, `Entrega`).
* `/vista`          : Interfaces gráficas interactivas.
* `/main`           : Punto de entrada principal de la aplicación.

## Configurar la Base de Datos:**

* Asegúrate de tener instalado MySQL.
* Ejecuta el siguiente script SQL en tu gestor de base de datos para crear las tablas necesarias:

```sql
CREATE TABLE repartidores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(100) NOT NULL,
    tipo ENUM('COMIDA','ENCOMIENDA','EXPRESS'),
    estado ENUM('PENDIENTE','EN_REPARTO','ENTREGADO')
);

CREATE TABLE entregas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    id_repartidor INT,
    fecha DATE,
    hora TIME,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id),
    FOREIGN KEY (id_repartidor) REFERENCES repartidores(id)
);

```

**3. Configurar Credenciales:**

* Navega al paquete `Speedfast.com.conexion`, abre la clase `ConexionBD` y ajusta las variables `USER` y `PASSWORD` según tu configuración local de MySQL.

**4. Ejecución:**

* Añade el driver `mysql-connector-j` a las dependencias deL proyecto
* Ejecuta la clase `Main.java`.
