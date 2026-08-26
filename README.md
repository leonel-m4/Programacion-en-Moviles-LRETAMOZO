# Programacion-en-Moviles-LRETAMOZO

**Nombre y apellido**: Leonel Mathias Retamozo De la Cruz

## Descripción: 
  El programa trata sobre un carrito de compas desarrollado en Kotlin. El programa     permite registar productos, mostrar el detale del carrito y calcular el subototal, IGV y el total de la compra. Las funciones que se implementaron nos sirven para identificar el producto más caro y calcular el descuento.

### Funciones implementadas

- `calcularSubtotal()` → calcula el subtotal de todos los productos.
- `calcularIGV()` → calcula el 18% de IGV.
- `calcularTotal()` → calcula el total sumando subtotal e IGV.
- `mostrarDetalle()` → muestra los productos del carrito con cantidades.
- `calcularDescuento()` → aplica un descuento del 5% o 10% según el total.
- `maxByOrNull` → permite encontrar el producto con mayor precio.

## Captura del proyecto:
  <img width="385" height="465" alt="image" src="https://github.com/user-attachments/assets/9b2cb45e-8827-4073-8d9a-836098da17e7" />
  
## Preguntas:
  . **¿Por qué nombre y precio son val pero cantidad es var?**
  El nombre y precio son val porque esos datos normalmente se mantienen igual. Por ejemplo, si agregamos una galleta que cuesta S/.1.50, no tendría sentido estar cambiando su nombre o precio. En cambio, la cantidad es var porque si se puede cambiar, ya que se puede aumentar su cantidad o disminuir.
  
  . **¿Qué pasaría si intentas cambiar el precio después de crear el producto?**
  Si se cambia el precio de un producto, Kotlin mostrara un error, porque precio no se puede modificar porque esta definido como val.
