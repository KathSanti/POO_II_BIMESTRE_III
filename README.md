# Sistema de Gestión SpeedFast

SpeedFast es una aplicación de escritorio desarrollada en **Java** diseñada para gestionar las operaciones diarias de una empresa de despachos. 
El sistema permite administrar pedidos, repartidores y entregas a través de una interfaz gráfica, implementando una arquitectura robusta orientada a objetos
y persistencia de datos relacional.

## Características Principales

* **Gestión de Pedidos (CRUD):** Registro, actualización, visualización y eliminación de pedidos. Incluye filtros dinámicos por tipo de pedido, estado y repartidor asignado.
* **Gestión de Repartidores (CRUD):** Administración completa de la flota de motoristas.
* **Gestión de Entregas (CRUD):** Asignación manual de pedidos a repartidores, registrando automáticamente la fecha y hora de la transacción.
* **Entregas Automáticas (Multihilo):** Simulación de entregas en tiempo real utilizando hilos (`Threads`) y colas concurrentes (`PriorityBlockingQueue`). Los repartidores toman los pedidos pendientes automáticamente desde una "Zona de Carga" y actualizan su estado.
* **Validaciones de Integridad:** Prevención de eliminación de registros que mantienen dependencias (llaves foráneas) en la base de datos, con alertas claras para el usuario.

## Tecnologías y Patrones de Diseño

* **Lenguaje:** Java
* **Interfaz Gráfica:** Java Swing (`JFrame`, `JTable`, `JComboBox`, `TableRowSorter` para filtros en vivo).
* **Base de Datos:** MySQL
* **Conexión a BD:** JDBC (`PreparedStatement`, `ResultSet`, manejo de transacciones con `try-with-resources`).
* **Arquitectura:** * **Patrón DAO (Data Access Object):** Separación estricta entre la lógica de negocio y el acceso a la base de datos mediante interfaces (`PedidoDAO`, `RepartidorDAO`, `EntregaDAO`).
    * **Modelo-Vista-Controlador (MVC):** Desacoplamiento de las entidades (`modelo`), la interfaz (`vista`) y la lógica de asignación (`controladores`).

## Estructura del Proyecto

* `conexion`: Configuración centralizada de la conexión JDBC (`ConexionBD`).
* `controladores`: Lógica central del sistema, gestor de colas (`ZonadeCarga`) y enumeradores (`Estado`, `TipoPedido`).
* `dao` / `interfacesDAO`: Implementación de las operaciones CRUD hacia la base de datos MySQL.
* `modelo`: Clases base del dominio (`Pedido`, `Repartidor`, `Entrega`).
* `vista`: Interfaces gráficas 
* `main`: Punto de entrada principal de la aplicación.

## Instalación y Configuración

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/TuUsuario/SpeedFast.git](https://github.com/TuUsuario/SpeedFast.git)
    ```
2.  **Configurar la Base de Datos:**
    * Asegúrate de tener instalado MySQL.
    * Ejecuta el script SQL incluido en el proyecto (o crea una base de datos llamada `speedfast_db_s8` con las tablas `repartidores`, `pedidos` y `entregas`).
      ```sql

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

3.  **Configurar Credenciales:**
    * Ve al paquete `Speedfast.com.conexion`, abre la clase `ConexionBD` y ajusta el `USER` y `PASSWORD` según tu configuración local de MySQL.
4.  **Ejecutar:**
    * Añade el driver `mysql-connector-j` a las dependencias de tu proyecto (vía Maven o añadiendo el `.jar` al Build Path).
    * Ejecuta la clase `Main.java`.

## Manejo de Errores
El sistema incluye manejo de excepciones SQL capturadas desde el DAO y propagadas hacia la interfaz gráfica (`JOptionPane`). Esto asegura que el programa no se detenga inesperadamente ante errores de integridad relacional
(ej. intentar eliminar un pedido que ya está en ruta).
