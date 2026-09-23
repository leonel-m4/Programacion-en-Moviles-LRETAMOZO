# Registro de Prompts e Iteraciones - Tecsup Fit

Este documento registra cronológicamente las iteraciones, prompts y decisiones de arquitectura tomadas durante el desarrollo, refactorización y overhaul visual de la aplicación **Tecsup Fit**.

---

## Iteración 1: Funcionalidad Core & Estado Reactivo

- **Objetivo del prompt**: Implementar la lógica de negocio central (operaciones CRUD, gestión y cancelación de reservas en listas reactivas usando `mutableStateListOf`) y añadir diálogos de confirmación (`AlertDialog`) con textos requeridos y color destructivo (`MaterialTheme.colorScheme.error`).
- **Análisis de limitaciones previas**: La versión inicial contaba con una lista de reservas estática donde el usuario no podía modificar ni cancelar reservas, lo que limitaba la interactividad y la gestión de estado reactivo.
- **Implementación**:
  - Se añadió `cancelarReserva(reservation)` en `ReservationRepository`.
  - Se implementó un `AlertDialog` de confirmación en `ReservationsScreen` con botones estilizados y color destructivo `MaterialTheme.colorScheme.error`.
- **Verificación y pruebas**:
  - Compilación exitosa de Gradle (`app:assembleDebug`).
  - Commit asociado: `git commit -m "feat(reservations): implementar funcionalidad core con estado reactivo y dialogo"`

---

## Iteración 2: Diseño UI/UX & Edge-to-Edge Insets

- **Objetivo del prompt**: Elevar el diseño de las tarjetas (bordes redondeados de 16.dp, elevación tonal, avatares circulares, iconos y estados vacíos profesionales) y aplicar `Modifier.navigationBarsPadding()` en barras inferiores y listas para evitar solapamientos con las barras de navegación de Android.
- **Análisis de limitaciones previas**: Las tarjetas anteriores carecían de profundidad visual (sombra/elevación tonal) y avatares dedicados; además, el contenido inferior podía solaparse con las barras de navegación del sistema en modo Edge-to-Edge.
- **Implementación**:
  - Rediseño de `ClassCard`, `ReservationCard`, `ProfileScreen` y `RoutinesScreen` con esquinas redondeadas de 16.dp, avatares circulares y estados vacíos informativos con iconos M3.
  - Adición de `Modifier.navigationBarsPadding()` en `Scaffold` y contenedores principales de todas las pantallas.
- **Verificación y pruebas**:
  - Compilación exitosa de Gradle (`app:assembleDebug`).
  - Commit asociado: `git commit -m "style(ui): refinar diseño UI/UX de tarjetas, estados vacios y insets"`

---

## Iteración 3: Overhaul Total, Repositorios Enriquecidos y Sincronización

- **Objetivo del prompt**: Extender las mejoras al resto de pantallas (perfiles, historiales, rutinas con repositorios de datos enriquecidos, menús laterales/inferiores y documentación continua).
- **Análisis de limitaciones previas**: Las pantallas secundarias (Rutinas y Perfil) eran estáticas y con información mínima. Se requería enriquecerlas con modelos de datos detallados y una experiencia de usuario inmersiva acorde a Clean Architecture.
- **Implementación**:
  - Actualización de `ProfileScreen` con tarjetas de estadísticas avanzadas y membresía activa.
  - Creación de colecciones de rutinas en `RoutinesScreen` con duración, niveles y conteo de ejercicios.
  - Creación y mantenimiento de `PROMPTS.md`.
- **Verificación y pruebas**:
  - Compilación exitosa de Gradle (`app:assembleDebug`).
  - Commit asociado: `git commit -m "refactor(app): overhaul total de pantallas, repositorios y documentacion"`

---

## Iteración 4: Corrección de Flujo de Navegación y Prevención de Duplicados

- **Objetivo del prompt**: Resolver el error de navegación donde al hacer una reserva el usuario quedaba atrapado sin poder volver al inicio de forma limpia, y evitar la duplicación de reservas causada por ejecución repetida en `LaunchedEffect`.
- **Análisis de limitaciones previas**:
  - La creación de la reserva se ejecutaba en un `LaunchedEffect` en `ConfirmationScreen`, lo que provocaba que al recomponer o navegar de regreso se duplicara la reserva.
  - El botón único en la pantalla de confirmación solo permitía ir a "Ver mis reservas", obstaculizando el retorno fluido a la pantalla de inicio (`Home`).
- **Implementación**:
  - Traslado de la lógica de reserva y decremento de cupos (`ClassRepository.decrementarCupo`) al evento del botón "Reservar cupo" en `ClassDetailScreen`.
  - Eliminación del `LaunchedEffect` en `ConfirmationScreen`.
  - Incorporación de dos opciones claras en `ConfirmationScreen`: **"Volver al inicio"** (limpiando el stack de navegación con `popUpTo(Screen.Home.route) { inclusive = false }`) y **"Ver mis reservas"**.
- **Verificación y pruebas**:
  - Compilación exitosa de Gradle (`app:assembleDebug`).
  - Commit asociado: `git commit -m "fix(navigation): corregir flujo de reserva, evitar duplicados y habilitar retorno al inicio"`

---

## Iteración 5: Overhaul Extremo del Perfil & Máxima Exigencia UI/UX

- **Objetivo del prompt**: Llevar la aplicación al límite de su diseño y funcionalidad ("llevar la app al límite"), transformando radicalmente la pantalla de perfil e introduciendo métricas avanzadas, insignias de logros, preferencias interactivas y asegurando que la navegación de retorno al inicio sea impecable.
- **Análisis de limitaciones previas**: La pantalla de perfil era demasiado simple y básica (solo un avatar con dos tarjetas de estadísticas), lo que no cumplía con los estándares de un producto de alta gama ni con las expectativas de un overhaul completo.
- **Implementación**:
  - **Overhaul de `ProfileScreen`**:
    - Tarjeta de perfil principal con avatar circular de alto contraste ("DR"), correo institucional y badge de "Miembro Premium Pro".
    - Cuadrícula de estadísticas de rendimiento de 4 métricas clave: Clases asistidas, Racha actual, Calorías quemadas (Kcal) y Tiempo activo.
    - Sección completa de **Insignias y Logros** con iconos distintivos y descripciones de hitos completados ("Madrugador Fit", "Constancia de Acero", "Cross Trainer Pro").
    - Sección de **Preferencias de la cuenta** con controles interactivos (`Switch`) para notificaciones de clases y modo oscuro forzado.
  - **Ajustes de Navegación**: Validación rigurosa del flujo de retorno a `Home` desde `ConfirmationScreen` mediante `popUpTo(Screen.Home.route) { inclusive = false }`.
- **Verificación y pruebas**:
  - Compilación exitosa de Gradle (`app:assembleDebug`).
  - Commit asociado: `git commit -m "feat(profile & navigation): overhaul total de perfil con estadisticas e insignias, y solucion definitiva de navegacion a inicio"`

---

## Iteración 6: Integración de Fotografías Reales con Coil y Refinamiento Absoluto

- **Objetivo del prompt**: Integrar fotografías reales de personas (avatares de alta calidad con Coil) en el perfil y la sección de instructores destacados, eliminar el botón de cerrar sesión por completo y asegurar que el retorno al inicio tras una reserva utilice `popBackStack` para evitar cualquier bloqueo de navegación.
- **Análisis de limitaciones previas**: El usuario reportó que la navegación de retorno al inicio tras reservar seguía presentando bloqueos y que el perfil se veía plano sin fotografías reales. Además, solicitó explícitamente no incluir el botón de cerrar sesión.
- **Implementación**:
  - **Librería Coil**: Adición de `io.coil-kt:coil-compose:2.6.0` y sincronización Gradle.
  - **Fotografías Reales**: Inserción de avatares profesionales de personas en el perfil de usuario y en la nueva sección de **"Instructores Destacados"** (`Carlos Mendoza`, `Lucía Fernández`, `Marco Silva`) usando `AsyncImage`.
  - **Eliminación de Cerrar Sesión**: Remoción completa de la opción de logout.
  - **Navegación Definitiva a Inicio**: Uso de `navController.popBackStack(Screen.Home.route, inclusive = false)` en el botón "Volver al inicio" de `ConfirmationScreen`.
- **Verificación y pruebas**:
  - Sincronización Gradle exitosa (`gradle_sync`).
  - Compilación exitosa de Gradle (`app:assembleDebug`).
  - Commit asociado: `git commit -m "feat(profile & navigation): integrar fotografias reales con coil, eliminar cerrar sesion y asegurar retorno a inicio post-reserva"`

---

## Iteración 7: Solución Crítica de Permisos, Fotografías Inmersivas en Clases y Rutinas Avanzadas

- **Objetivo del prompt**: Solucionar el cierre forzado (crash) por falta de permisos de red al cargar imágenes remotas, enriquecer las tarjetas de clases con fotografías reales de instructores y transformar la sección de rutinas con imágenes y calorías detalladas para cumplir con los más altos estándares de producción.
- **Análisis de limitaciones previas**:
  - La aplicación carecía del permiso `android.permission.INTERNET` en el `AndroidManifest.xml`, lo que provocaba cierres inesperados al intentar cargar avatares e imágenes con Coil.
  - Las tarjetas de clases y la pantalla de rutinas necesitaban un nivel visual superior con fotografías de alta calidad, nombres de instructores y métricas de quema calórica.
- **Implementación**:
  - **Permisos AndroidManifest**: Inserción obligatoria de `<uses-permission android:name="android.permission.INTERNET" />` y `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`.
  - **Clases y Repositorios Enriquecidos**: Actualización de `FitClass` y `ClassRepository` para incluir nombres y fotos de instructores profesionales (`Carlos Mendoza`, `Lucía Fernández`, etc.).
  - **Rutinas Avanzadas (`RoutinesScreen`)**: Rediseño total con tarjetas con imágenes de alta resolución de entrenamiento (`AsyncImage`), duración, niveles, conteo de ejercicios y quema de calorías (`kcal`).
- **Verificación y pruebas**:
  - Compilación y ensamblaje exitoso (`app:assembleDebug`) sin errores.
  - Commit asociado: `git commit -m "feat(advanced & permissions): agregar permiso de internet, imagenes reales con coil en tarjetas y rutinas avanzadas"`

---

## Iteración 8: Solución Definitiva de Crash por Claves Duplicadas en `LazyColumn` (Ver Mis Reservas)

- **Objetivo del prompt**: Corregir el cierre forzado (crash) exacto que ocurría al hacer clic en el botón "Ver mis reservas" tras realizar una reserva.
- **Análisis de limitaciones previas**:
  - Al renderizar `ReservationsScreen`, el componente `LazyColumn` utilizaba claves compuestas (`key = { "${it.claseNombre}-${it.fecha}-${it.hora}" }`). Si se añadía una reserva con la misma clase y hora, se producía una excepción de clave duplicada (`IllegalArgumentException`), sacando al usuario de la aplicación.
- **Implementación**:
  - Inserción de un campo `id: String = UUID.randomUUID().toString()` en el modelo de datos `Reservation`.
  - Actualización de `ReservationsScreen` para utilizar `key = { it.id }` en el `LazyColumn`, garantizando unicidad absoluta y estabilidad en las listas reactivas.
- **Verificación y pruebas**:
  - Compilación y ensamblaje exitoso (`app:assembleDebug`) sin errores.
  - Commit asociado: `git commit -m "fix(crash): agregar IDs unicos UUID a reservas para evitar crash de duplicate keys en LazyColumn al ver mis reservas"`

---

## Iteración 9: Botón Explícito de Inicio en TopAppBar de Mis Reservas

- **Objetivo del prompt**: Garantizar que el usuario nunca quede atrapado sin poder volver al inicio cuando se encuentra visualizando la pantalla de "Mis reservas".
- **Análisis de limitaciones previas**:
  - Al navegar desde la confirmación a "Mis reservas", el usuario requería un acceso directo y evidente hacia la pantalla principal (`Home`), sin depender únicamente de la barra inferior o gestos del sistema.
- **Implementación**:
  - Incorporación de un botón de navegación (`navigationIcon`) con un icono de **Inicio (`Home`)** en el `TopAppBar` de `ReservationsScreen`.
  - Al presionarlo, ejecuta un `navController.navigate(Screen.Home.route)` limpiando el stack de forma limpia.
- **Verificación y pruebas**:
  - Compilación y ensamblaje exitoso (`app:assembleDebug`) sin errores.
  - Commit asociado: `git commit -m "fix(navigation): agregar boton explicitico de inicio (Home) en la TopAppBar de Mis Reservas para garantizar retorno al inicio sin bloqueos"`
