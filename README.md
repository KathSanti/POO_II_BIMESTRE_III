# SpeedFast - Sistema de Gestión de Envíos Concurrentes

**SpeedFast** es una aplicación desarrollada para la gestión y despacho de pedidos. El sistema utiliza programación concurrente para simular la entrega de múltiples pedidos simultáneamente, optimizando el tiempo de respuesta mediante el uso de hilos.

##Tecnologías y Conceptos Aplicados

* **Concurrencia:** Implementación de `ExecutorService` y `Runnable` para el manejo de hilos.
* **POO :** Uso de interfaces, clases abstractas y polimorfismo.
* **Simulación en Tiempo Real:** Cálculo dinámico de tiempos de espera basado en distancias y tipos de pedido.


## Arquitectura del Proyecto

El sistema está diseñado bajo una arquitectura modular dividida por responsabilidades:

### 1. Modelo de Negocio (`model`)
Contiene la jerarquía de pedidos y la lógica de los repartidores.
* **Clase Abstracta `Pedido`**: Define la base para cualquier envío.
* **Especializaciones**: 
    * `ComidaPedido`: Maneja logística de alimentos (mochilas térmicas).
    * `ExpressPedido`: Envíos rapidos.
    * `EncomiendaPedido`: Envíos con embalaje especial.
* **`Repartidor`**: Implementa `Runnable` para permitir la ejecución en hilos independientes.



### 2. Capacidades (`capability`)
Interfaces que definen comportamientos específicos que pueden ser adoptados por diferentes clases:
* `Despachable`: Contrato para iniciar la ruta.
* `Rastreable`: Define la capacidad de ver historiales.

### 3. Controlador (`controlador`)
La clase `ControladorEnvios` centraliza el registro de pedidos, la gestión del historial y la cancelación de envíos, actuando como el cerebro administrativo del sistema.

---

## Lógica de Concurrencia

El sistema utiliza un **Pool de hilos fijos** para gestionar a los repartidores de forma eficiente:

1.  Se instancian los repartidores con sus listas de pedidos asignadas.
2.  Se utiliza `ExecutorService` para disparar la ejecución de cada repartidor en un hilo separado.
3.  El sistema calcula el tiempo de espera mediante fórmulas específicas y simula el trayecto con `Thread.sleep()`:

