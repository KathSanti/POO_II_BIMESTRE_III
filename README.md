# Sistema de Gestión de Envíos - SpeedFast

## Descripción del Proyecto
Este proyecto es una simulación de un sistema logístico concurrente para la empresa **SpeedFast**. El objetivo principal es modelar el problema del **Productor-Consumidor** 
utilizando hilos en Java, garantizando que múltiples repartidores puedan acceder a una **Zona de Carga** compartida sin generar inconsistencias en los datos (Race Conditions) 
ni bloqueos indefinidos (Deadlocks).

El sistema simula la asignación, retiro y entrega de pedidos mediante el uso de **Monitores** y **Colas Bloqueantes**.

## Tecnologías y Conceptos Aplicados

* **Concurrencia:** Uso de la interfaz `Runnable` y la clase `Thread`.
* **Sincronización:** Implementación de bloques `synchronized` para gestionar el acceso a recursos compartidos.
* **Estructuras de Datos Thread-Safe:** Uso de `PriorityBlockingQueue` para manejar la cola de pedidos de forma segura.
* **Manejo de Estados:** Enumeradores (`Enum`) para el ciclo de vida del pedido.

## Arquitectura del Código

El proyecto está organizado en el paquete `speedFast.Concurrente.com` y consta de las siguientes clases:

### 1. `Main.java` (El Orquestador)
Es el punto de entrada. Se encarga de:
* Inicializar la cola de prioridad (`PriorityBlockingQueue`).
* Instanciar la `ZonadeCarga` (recurso compartido).
* Crear los pedidos iniciales (Productor).
* Iniciar los hilos de los repartidores (Consumidores).
* Esperar a que todos los hilos terminen (`join()`) para mostrar el mensaje final de éxito.

### 2. `ZonadeCarga.java` (El Monitor)
Actúa como el recurso compartido seguro.
* **Método `agregarPedido`:** Sincronizado. Añade pedidos a la cola.
* **Método `retirarPedido`:** Sincronizado. Utiliza el método `poll()` de la cola.
    * *Decisión de Diseño:* Se eligió `poll()` en lugar de `take()` para evitar bloqueos eternos. Si la cola está vacía, devuelve `null` inmediatamente,
      permitiendo que los hilos finalicen su ejecución limpiamente.

### 3. `Repartidor.java` 
Implementa `Runnable`. Representa a un trabajador que:
1.  Intenta retirar un pedido de la zona de carga.
2.  Si obtiene un pedido (`null check`), cambia su estado a `EN_REPARTO`.
3.  Simula el tiempo de viaje con `Thread.sleep()`.
4.  Marca el pedido como `ENTREGADO`.
5.  Si no hay pedidos, termina su ciclo de ejecución (`break`).

### 4. `Pedido.java`
Modelo de datos que implementa `Comparable`.
* Permite que la `PriorityBlockingQueue` ordene los elementos (aunque actualmente todos tienen la misma prioridad, la estructura queda lista para escalabilidad).
