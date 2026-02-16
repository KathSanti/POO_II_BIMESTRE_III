# Sistema de Gestión de Pedidos SpeedFast 

Este proyecto es una aplicación de escritorio desarrollada en **Java** que simula un sistema de gestión de envíos para la empresa "SpeedFast". La aplicación permite registrar pedidos, visualizarlos en tiempo real 
y simular el proceso de entrega mediante el uso de **programación concurrente (Hilos)** e interfaz grafica.

##Características Principales

* **Arquitectura MVC:** Separación clara entre la lógica de negocio, la interfaz gráfica y los controladores.
* **Interfaz Gráfica (Swing):** Ventanas intuitivas para el registro y monitoreo de pedidos.
* **Concurrencia y Hilos:** Simulación de repartidores trabajando simultáneamente utilizando `Threads`.
* **Colas Thread-Safe:** Uso de `PriorityBlockingQueue` y monitores (`ZonadeCarga`) para gestionar el acceso seguro a los pedidos compartidos.

## Estructura del Proyecto

El código está organizado en paquetes para facilitar su mantenimiento y escalabilidad:

### `main`
Contiene el punto de entrada de la aplicación.
* `Main.java`: Inicializa la aplicación y lanza la `VentanaPrincipal` en el hilo de despacho de eventos de Swing.

### `modelo`
Representa los datos y la lógica pura del negocio.
* `Pedido.java`: Define la estructura de un pedido (ID, dirección, tipo, estado). Implementa `Comparable` para priorizar pedidos.
* `Repartidor.java`: Clase que implementa `Runnable`. Simula a un motorista que toma pedidos de la zona de carga y los entrega, esperando un tiempo simulado entre cada acción.

### `vista`
Contiene las interfaces gráficas de usuario (GUI) construidas con **Java Swing**.
* `VentanaPrincipal.java`: Menú principal con acceso a las funcionalidades (Registrar, Listar, Iniciar Entregas).
* `VentanaRegistroPedido.java`: Formulario para ingresar nuevos datos validando la entrada del usuario.
* `VentanaListaPedidos.java`: Visualiza los pedidos registrados y su estado actual (Pendiente, En Reparto, Entregado) mediante una `JTable`.

### `controladores`
Gestiona la comunicación entre la vista y el modelo, y maneja la lógica de control.
* `ControladorPedidos.java`: Coordina la creación de pedidos, actualiza la lista de visual y gestiona el inicio de los hilos de repartidores.
* `ZonadeCarga.java`: Recurso compartido (Monitor) donde se almacenan los pedidos pendientes. Utiliza métodos `synchronized` para asegurar que los repartidores no tomen el mismo pedido al mismo tiempo.
* `Estado.java` (Enum): Define los estados: *PENDIENTE, EN_REPARTO, ENTREGADO*.
* `TipoPedido.java` (Enum): Define los tipos de pedidos: *EXPRESS, COMIDA, ENCOMIENDA*.

