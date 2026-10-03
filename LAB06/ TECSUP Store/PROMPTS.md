# Prompts usados en la Fase 2

**Fecha:** 2026-10-02

### Prompt principal usado
> "Actúa como un desarrollador Android senior experto en Jetpack Compose, arquitectura limpia, UI/UX premium y rendimiento móvil. Implementa la Fase 2 completa de TECSUP Store con un catálogo controlado de exactamente 12 productos numerados, TopAppBar limpia sin icono de carrito, navegación principal mediante NavigationDrawer profesional con badge contador de favoritos y carrito, estado centralizado en memoria, modo oscuro dinámico, buscador y categorías en tiempo real, carrito funcional y generación dinámica de pedidos."

### Resumen de la mejora obligatoria
- Estado compartido de favoritos mediante *state hoisting* (`favoritosIds` y `onToggleFavorito`).
- Menú desplegable (`DropdownMenu`) interactivo en cada `TarjetaProducto` con opción dinámica ("Agregar a favoritos" / "Quitar de favoritos").
- Badge dinámico en el `NavigationDrawer` asociado al ítem "Favoritos" que refleja la cantidad exacta de productos seleccionados en tiempo real.

### Resumen de funciones adicionales agregadas
- Catálogo controlado de exactamente 12 productos numerados para garantizar rendimiento y estabilidad óptima en listas (`LazyColumn`).
- TopAppBar limpia y moderna **sin** icono de carrito (acceso exclusivo desde el drawer).
- Alternancia de modo claro y oscuro (`isDarkMode`) reactiva en toda la interfaz.
- Buscador de productos en tiempo real combinado con filtros por categorías (`LazyRow` con chips).
- Carrito de compras funcional con control de ítems, cálculo automático de subtotal, delivery y total.
- Pantalla dedicada de "Mis pedidos" que genera pedidos reales de forma dinámica al confirmar la compra.
- Pantalla de "Perfil de usuario" totalmente editable que actualiza el estado y las iniciales del cajón de navegación al instante.

### Archivos modificados
- `app/src/main/java/com/tecsup/tecsupstore/MainActivity.kt`
- `app/src/main/java/com/tecsup/tecsupstore/Producto.kt`
- `app/src/main/java/com/tecsup/tecsupstore/navigation/Pantalla.kt`
- `app/src/main/java/com/tecsup/tecsupstore/navigation/CajonNavegacion.kt`
- `app/src/main/java/com/tecsup/tecsupstore/navigation/AppNavegacion.kt`
- `app/src/main/java/com/tecsup/tecsupstore/screens/TarjetaProducto.kt`
- `app/src/main/java/com/tecsup/tecsupstore/screens/PantallaInicio.kt`
- `app/src/main/java/com/tecsup/tecsupstore/screens/PantallaPedidos.kt`
- `app/src/main/java/com/tecsup/tecsupstore/screens/PantallaPerfil.kt`

### Archivos creados
- `app/src/main/java/com/tecsup/tecsupstore/screens/PantallaFavoritos.kt`
- `app/src/main/java/com/tecsup/tecsupstore/screens/PantallaCarrito.kt`
- `PROMPTS.md`

### Qué hizo la IA
La IA optimizó el catálogo a 12 productos numerados, eliminó el carrito de la TopBar dejando la navegación centralizada en el NavigationDrawer, implementó un estado global reactivo en memoria para comercio electrónico completo (carrito, pedidos dinámicos, perfil editable, búsqueda avanzada) y aplicó un diseño UI/UX premium en Material 3 con modo oscuro.

### Qué se corrigió o ajustó manualmente si fue necesario
Se ajustaron los imports, se eliminaron elementos redundantes en la barra superior y se realizaron pruebas de compilación y rendimiento con Gradle.

### Comandos Git usados
- `git checkout main`
- `git checkout -B mejora-ia`
- `git add ...`
- `git commit -m "..."`
- `./gradlew build`
- `git push -f origin mejora-ia`
