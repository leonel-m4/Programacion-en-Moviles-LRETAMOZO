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

## 🛠 Refactorización Senior: Precisión y Mantenibilidad

En la última iteración de refactorización, hemos elevado el estándar técnico abordando tres pilares críticos:

1. **Implementación de `BigDecimal`:** Se migró de `Double` a `BigDecimal` para todos los cálculos monetarios. Los números de punto flotante (`Double`) introducen errores de redondeo binario inaceptables en finanzas. `BigDecimal` garantiza precisión absoluta con redondeo `HALF_UP`.
2. **Eliminación de "Magic Numbers":** Centralizamos las tasas de impuestos y umbrales de descuento en el objeto `Constants`. Esto permite cambiar la lógica de negocio (como subir el IGV del 18% al 19%) modificando una sola línea de código.
3. **Optimización de Cálculos:** La clase `ShoppingSession` ahora consolida el resumen financiero en un solo paso (`calculateSummary`), mejorando el rendimiento al evitar múltiples iteraciones sobre la lista de productos.

## 🚀 Guía de Ejecución

Debido a que este proyecto utiliza **Kotlin Puro** con integración nativa de **Java**, la forma más robusta de ejecutarlo si el IDE presenta problemas de configuración es mediante la terminal:

```powershell
# Compilar y ejecutar en un solo paso (desde la raíz del proyecto)
& "C:\Program Files\Android\Android Studio\plugins\Kotlin\kotlinc\bin\kotlinc.bat" CarritoIA.kt -include-runtime -d CarritoIA.jar; java -jar CarritoIA.jar
```

*Nota: También puede ejecutarse haciendo clic derecho en la función `main()` y seleccionando **Run 'CarritoIAKt'**.*

## 📈 Conclusión Técnica
Esta arquitectura demuestra un desacoplamiento total. El sistema es **fácilmente testable** mediante Mocking de interfaces y está listo para escalar a nuevos tipos de productos o estrategias de descuento sin riesgo de regresiones en el núcleo del negocio.

## Estudiante
*   **Nombre:** Leonel Retamozo
*   **Laboratorio:** 02 - Carrito Kotlin IA
