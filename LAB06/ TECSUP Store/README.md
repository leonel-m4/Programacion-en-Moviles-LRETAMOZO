## Preguntas de reflexión

### 1. ¿Por qué el `DropdownMenu` se declara dentro de un `Box` junto al ícono que lo activa, y no en cualquier parte de la pantalla?

Porque el Box permite agrupar el ícono con el menú y hace que el DropdownMenu aparezca cerca del botón que lo abre. De esta manera, el menú queda relacionado visualmente con el producto que le corresponde.

### 2. ¿Qué diferencia de alcance hay entre las opciones del `DropdownMenu` y las del `NavigationDrawer`?

El DropdownMenu solo afecta al producto seleccionado, por ejemplo agregarlo a favoritos, compartirlo o reportarlo. En cambio, el NavigationDrawer permite navegar entre las secciones que tiene la aplicación.

### 3. ¿Cómo tuviste que estructurar tu código para que el contador de favoritos del drawer "se entere" de lo que pasa en el `DropdownMenu` de cada producto?

El estado de favoritos se maneja desde un nivel general de la aplicación. De esta forma, cuando un producto se agrega o se quita de favoritos desde el DropdownMenu, el NavigationDrawer también recibe ese cambio y actualiza el contador automáticamente.

### 4. ¿Qué tuviste que corregir del código que te generó la IA para la mejora del badge de favoritos?

Se ajustó la forma en que se comparte el estado de favoritos entre los componentes. También se revisaron algunos imports, los parámetros entre composables y la lógica para evitar que un mismo producto se agregue más de una vez a favoritos.