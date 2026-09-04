# REGISTRO DE NOTAS

**Nombre y apellido**: Leonel Mathias Retamozo De la Cruz

# DESCRIPCIÓN
El programa trata sobre un registro de productos desarrollado en Kotlin. El programa permite registrar productos, ingresar su nombre, precio y cantidad, mostrar un resumen con los datos registrados y calcular el importe de la compra. Las funciones que se implementaron nos sirven para trabajar con estados, validar los datos ingresados y mostrar un mensaje de confirmación cuando el producto es registrado correctamente.

# CAPTURAS
![Screenshot_20260903-183923.png](../../../Screenshot_20260903-183923.png)

![Screenshot_20260903-183948.png](../../../Screenshot_20260903-183948.png)

# PREGUNTA PLANTEADA POR EL DOCENTE
- **¿Qué pasaría si declaras las variables de los campos SIN remember?**
  - Si se declara las variables de los campos sin remenber, el valor que escribas se pueda reiniciar cuando ocurre una recomposición, osea cuando volteas la pantalla del celular.

# MEJORA CON IA
| Prompt que usé | Qué generó Gemini | Qué acepté o corregí (y por qué) |
| :--- | :--- | :--- |
| "Agregue validación de campos vacíos (si falta un dato al presionar AGREGAR, mostrar un mensaje de error en rojo en lugar de la Card) y un botón Limpiar que vacíe el formulario. Sé específico en tu prompt: dónde (PantallaRegistro), qué (el comportamiento exacto) y qué NO tocar." | Generó un nuevo estado `mensajeError`, implementó la validación de campos vacíos en el botón Agregar, añadió un botón "LIMPIAR" y corrigió el error ortográfico "PRODCUTO". | Acepté la estructura de botones y la lógica de validación básica. Corregí agregando validación numérica estricta (`toDoubleOrNull`), configuré teclados específicos (`KeyboardType`) y usé el parámetro `isError` en los campos para mejor feedback visual. |
