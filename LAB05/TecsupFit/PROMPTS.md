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
  - Incorporación de dos opciones claras en `ConfirmationScreen`: **"Volver al inicio"** (limpiando el stack de navegación con `popUpTo(Screen.Home.route) { inclusive = true }`) y **"Ver mis reservas"**.
- **Verificación y pruebas**:
  - Compilación exitosa de Gradle (`app:assembleDebug`).
  - Commit asociado: `git commit -m "fix(navigation): corregir flujo de reserva, evitar duplicados y habilitar retorno al inicio"`
