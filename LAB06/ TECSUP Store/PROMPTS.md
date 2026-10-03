# Prompts usados en la Fase 2

**Fecha:** 2026-10-02

### Prompt principal usado
> "Implementa exactamente la Fase 2 del Laboratorio 06 para la app TECSUP Store respetando estrictamente el alcance académico. Eleva el estado de favoritos (`favoritosIds`) a AppNavegacion usando state hoisting, conecta el DropdownMenu de cada TarjetaProducto (Audífonos, Smartwatch, Funda celular) para alternar entre Agregar y Quitar de favoritos sin duplicados, e integra el Badge dinámico en el NavigationDrawer (CajonNavegacion) asociado al destino Favoritos."

### Resumen de la mejora obligatoria
- Estado compartido de favoritos mediante *state hoisting*.
- Menú desplegable (`DropdownMenu`) con opciones de Favoritos, Compartir y Reportar.
- Badge dinámico en el `NavigationDrawer` reflejando el contador real de favoritos.

### Archivos modificados
- `app/src/main/java/com/tecsup/tecsupstore/Producto.kt`
- `app/src/main/java/com/tecsup/tecsupstore/navigation/Pantalla.kt`
- `app/src/main/java/com/tecsup/tecsupstore/navigation/CajonNavegacion.kt`
- `app/src/main/java/com/tecsup/tecsupstore/navigation/AppNavegacion.kt`
- `app/src/main/java/com/tecsup/tecsupstore/screens/TarjetaProducto.kt`
- `app/src/main/java/com/tecsup/tecsupstore/screens/PantallaInicio.kt`
- `PROMPTS.md`

### Qué hizo la IA
Se ajustó la aplicación para acoger estrictamente los lineamientos y componentes académicos oficiales del Laboratorio 06, eliminando cualquier componente extra fuera de alcance (como carritos o múltiples productos adicionales) y asegurando el funcionamiento preciso del flujo de favoritos y badge en el drawer.

### Comandos Git usados
- `git checkout main`
- `git checkout -B mejora-ia`
- `git add ...`
- `git commit -m "..."`
- `./gradlew build`
- `git push -f origin mejora-ia`
