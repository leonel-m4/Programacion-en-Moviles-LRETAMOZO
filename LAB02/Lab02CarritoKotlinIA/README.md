# Laboratorio 02: Carrito de Compras Pro (Arquitectura SOLID & POO)

Este proyecto representa una implementación de alta calidad técnica, moviéndose de un script simple a un sistema orientado a objetos robusto y escalable.

## 🚀 El "Ultra-Prompt" utilizado
Para esta versión, se empleó un prompt diseñado para simular una revisión de arquitectura senior:

> "Actúa como un **Líder Técnico de Desarrollo Android**. Refactoriza el carrito de compras de Kotlin siguiendo una arquitectura de **Clean Code** y **Principios SOLID**.
> 
> **Requerimientos Técnicos:**
> 1. **Abstracción Total:** Usa una `interface` o `abstract class` para definir un contrato de impuestos (`Taxable`).
> 2. **Patrón Strategy:** Implementa los descuentos mediante una interfaz `DiscountStrategy`, permitiendo que el sistema sea 'Abierto para la extensión pero cerrado para la modificación'.
> 3. **Encapsulamiento Robusto:** La clase `ShoppingSession` debe gestionar el estado interno usando *backing properties* para proteger la mutabilidad.
> 4. **Inyección de Dependencias (Manual):** El carrito no debe calcular el descuento por sí mismo, debe recibir una 'estrategia' para hacerlo.
> 5. **Polimorfismo de Interfaz:** Demuestra cómo diferentes productos (Electrónicos, Alimentos) manejan sus propios cálculos de manera polimórfica.
>
> Explica detalladamente cómo esta estructura elimina el acoplamiento y mejora la testabilidad para una defensa ante expertos."

## 🧠 Conceptos Avanzados de POO Aplicados

1. **Principio de Responsabilidad Única (SRP):** El producto solo sabe sus datos, el carrito solo gestiona la lista, y la estrategia de descuento solo sabe calcular rebajas.
2. **Patrón Strategy:** Si el profesor pregunta cómo cambiar el descuento del 5% al 20%, puedes decirle: *"Solo creo una nueva clase que implemente `DiscountStrategy`, no toco el código del Carrito"*. Eso es **Polimorfismo** avanzado.
3. **Backing Properties:** En `ShoppingSession`, la lista es privada y mutable por dentro, pero pública e inmutable por fuera. Esto protege tus datos de ser borrados por error desde el `main`.

## Estudiante
*   **Nombre:** Leonel Retamozo
*   **Laboratorio:** 02 - Carrito Kotlin IA
