# Sistema de Gestión de Envíos - SpeedFast

Este proyecto es una solución que Simula un sistema logístico para la empresa "SpeedFast", gestionando diferentes tipos de pedidos, asignación de repartidores y cálculo de tiempos de entrega mediante principios sólidos de arquitectura de software.

## Decisiones de Diseño y Arquitectura

Para cumplir con los requerimientos de escalabilidad y mantenibilidad, se tomaron las siguientes decisiones clave en la estructura del código:

### 1. Abstracción y Jerarquía de Clases (`Model`)
Se definió `Pedido` como una **Clase Abstracta**.
* **¿Por qué?** Todos los pedidos comparten atributos base (`id`, `direccion`, `distancia`), pero la forma de calcular el tiempo de entrega es única para cada tipo.
* **Beneficio:** Obliga a las clases hijas (`ComidaPedido`, `EncomiendaPedido`, `ExpressPedido`) a implementar sus propias reglas de negocio para el cálculo de tiempos, garantizando integridad en los datos.

### 2. Desacoplamiento mediante Interfaces (`Capability`)
Se implementó un diseño modular separando las capacidades funcionales en interfaces (`capabilities`), distribuyéndolas según la responsabilidad del objeto:

* **Interfaz `Despachable`:** Implementada en la clase **Modelo (`Pedido`)**.
    * *Razón:* El cambio de estado a "Despachado" es intrínseco a la entidad del pedido individual.
* **Interfaces `Cancelable` y `Rastreable`:** Implementadas en el **Controlador (`ControladorEnvios`)**.
    * *Razón:* Cancelar un pedido o ver el historial requiere acceso a la colección completa de datos (la lista de pedidos). Esta responsabilidad recae sobre el gestor (Controlador) y no sobre el pedido individual.

### 3. Polimorfismo (Sobrecarga y Sobrescritura)
El sistema aprovecha el polimorfismo para adaptar comportamientos:
* **Sobrescritura (`@Override`):** El método `asignarRepartidor()` tiene un comportamiento distinto en cada subclase (ej. confirmar "mochila térmica" en comida vs "embalaje especial" en encomiendas).
* **Sobrecarga:** Se ofrece flexibilidad al permitir asignar un repartidor automáticamente o manualmente pasando un `String nombre` como argumento.

---

## Estructura del Proyecto



    com.speedfast.sistema
    ├── app
    │   └── Main.java            # Punto de entrada y simulación del sistema
    ├── capability               # Interfaces (Contratos de comportamiento)
    │   ├── Cancelable.java
    │   ├── Despachable.java
    │   └── Rastreable.java
    ├── controlador              # Lógica de gestión y manipulación de listas
    │   └── ControladorEnvios.java
    └── model                    # Entidades y lógica de negocio específica
        ├── Pedido.java          # Clase padre Abstracta
        ├── ComidaPedido.java
        ├── EncomiendaPedido.java
        └── ExpressPedido.java
    
