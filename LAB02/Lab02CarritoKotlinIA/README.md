# Laboratorio 02: Carrito de Compras Ultra Pro (Arquitectura SOLID & POO Avanzado)

Esta versión eleva la calidad técnica al implementar **Polimorfismo Real** y una estructura de clases altamente desacoplada.

## 🚀 El "Arquitecto Senior Prompt" (Mejorado)
Para obtener este nivel de código, puedes usar este prompt:

> "Actúa como un **Arquitecto de Software Senior en Kotlin**. Diseña un sistema de carrito de compras que sea una obra maestra de la **Programación Orientada a Objetos**.
> 
> **Requerimientos de Élite:**
> 1. **Polimorfismo Real:** Crea una jerarquía de productos (`TechProduct`, `OfficeProduct`) que sobrescriban métodos abstractos para definir su comportamiento y categoría.
> 2. **Desacoplamiento Total:** Usa el **Strategy Pattern** para descuentos e **Interface Injection** para impuestos, asegurando que el núcleo del carrito sea agnóstico a las reglas de negocio.
> 3. **Precisión Financiera:** Usa obligatoriamente `BigDecimal` para evitar errores de redondeo binario.
> 4. **Encapsulamiento de Datos:** Implementa *Backing Properties* en la sesión de compra para proteger la integridad de la lista de productos.
> 5. **Clean Code:** Usa extensiones de Kotlin para simplificar operaciones matemáticas complejas.
>
> Entrega un código listo para producción que sea un ejemplo de escalabilidad y mantenibilidad."

## 🧠 Innovaciones Técnicas en esta Versión

1. **Jerarquía Polimórfica:** Ahora los productos no son solo datos, tienen comportamiento. El método `getCategoryName()` es abstracto, obligando a cada subclase a identificarse.
2. **Cálculo Distribuido:** El cálculo del total por línea es `open`, lo que permite que en el futuro un producto pueda redefinir cómo se suma al carrito sin tocar la clase base.
3. **Identificación Visual:** La salida en consola ahora etiqueta los productos por su categoría técnica (`[TECH]`, `[OFFICE]`), demostrando el uso de métodos sobrescritos.

## 🛠 Guía de Ejecución Garantizada

Si el IDE muestra errores visuales, usa la terminal para una ejecución limpia:

```bash
# Compilar y ejecutar (Ruta raíz)
"/Applications/Android Studio.app/Contents/plugins/Kotlin/kotlinc/bin/kotlinc" CarritoIA.kt -include-runtime -d CarritoIA.jar && java -jar CarritoIA.jar
```

## Estudiante
*   **Nombre:** Leonel Retamozo
*   **Laboratorio:** 02 - Carrito Kotlin IA (Edición SOLID Avanzada)
