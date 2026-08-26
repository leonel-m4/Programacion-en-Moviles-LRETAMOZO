# LABORATORIO SIN IA
- ¿Por qué nombre y precio son val pero cantidad es var?
El nombre y precio son val porque esos datos normalmente se mantienen igual. Por ejemplo, si agregamos una galleta que cuesta S/.1.50, no tendría sentido estar cambiando su nombre o precio. En cambio, la cantidad es var porque si se puede cambiar, ya que se puede aumentar su cantidad o disminuir.
- ¿Qué pasaría si intentas cambiar el precio después de crear el producto?
Si se cambia el precio de un producto, Kotlin mostrara un error, porque precio no se puede modificar porque esta definido como val. 