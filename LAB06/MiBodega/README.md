## Preguntas de reflexión

### 1. ¿Por qué `Producto.kt` y `MainActivity.kt` se entregaron completos, y las pantallas no? ¿Qué tienen en común los archivos que sí se dejaron como esqueleto?

Porque Producto.kt y MainActivity.kt ya tenían la base necesaria para que el proyecto funcione. En cambio, las pantallas eran la parte que se tenía que completar para practicar la interfaz, la navegación y el manejo de datos dentro de la aplicación.

### 2. ¿Cómo lograste que el filtro de categoría (`LazyRow`) y el cálculo del carrito reaccionen automáticamente sin que tú "actualices" nada a mano?

Se usaron variables de estado en Compose. Cuando cambiaba una categoría o la cantidad de un producto, Compose detectaba el cambio y volvía a mostrar la información actualizada automáticamente.

### 3. ¿Qué diferencia notaste entre `navigate()` normal (Inicio→Detalle) y el que usa `popUpTo` (Datos de entrega→Confirmación)?

Con navigate() normal se puede ir a otra pantalla y luego regresar con el botón atrás. En cambio, con popUpTo se limpia parte del recorrido anterior, evitando volver al carrito o a los datos de entrega después de confirmar el pedido.

### 4. ¿Qué tuviste que corregir del código que te generó la IA para el buscador en tiempo real?

Se revisó que el buscador no reemplazara el filtro de categorías. La lógica se corrigió para que ambos filtros funcionen juntos y solo se muestren los productos que coincidan con la búsqueda y con la categoría seleccionada.

### 5. Compara el `NavigationDrawer` del Laboratorio 6 con el `NavigationBar` de esta tarea: ¿en qué caso usarías cada uno en un proyecto propio?

Usaría NavigationDrawer cuando una aplicación tenga varias secciones u opciones que no necesitan estar siempre visibles. Usaría NavigationBar cuando existan pocas secciones principales y se quiera que el usuario pueda acceder a ellas rápidamente desde la parte inferior de la pantalla.
