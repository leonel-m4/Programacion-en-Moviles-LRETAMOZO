# Registro de Prompts e Iteraciones - Mejora IA: Cancelar Citas

Este documento registra los prompts evaluados durante el proceso de desarrollo de la funcionalidad para cancelar citas en la pantalla "Mis citas" (`MyAppointmentsScreen`).

---

## Prompt 1 (Borrador Inicial)
> **Prompt:** "Agrega un botón para cancelar citas en la pantalla de Mis citas."

* **Por qué no era definitivo / Limitaciones:**
  - Demasiado genérico. No especificaba qué estado de cita ("Confirmada" vs "Completada") debía mostrar el botón de cancelación.
  - No definía los textos exactos ni el comportamiento del `AlertDialog` de confirmación.
  - No especificaba el uso de colores destructivos (`MaterialTheme.colorScheme.error`) para el botón de confirmación ni el botón "Volver".
* **Prueba / Verificación:** Rechazado por falta de detalle en los requerimientos de Material 3 y lógica de negocio.

---

## Prompt 2 (Intermedio)
> **Prompt:** "En MyAppointmentsScreen, añade un ícono de basurero en las AppointmentCard confirmadas que abra un diálogo de confirmación para eliminar la cita del repositorio."

* **Por qué no era definitivo / Limitaciones:**
  - Aunque especificaba el ícono y la acción general, omitía el texto exacto requerido por los requisitos de usuario (`¿Seguro que deseas cancelar esta cita con [nombre del doctor]?`).
  - Faltaba precisar los nombres exactos de los botones del diálogo ("Cancelar cita" con color de error y "Volver").
* **Prueba / Verificación:** Probado mentalmente y conceptualmente insuficiente frente a las especificaciones estrictas de la tarea.

---

## Prompt 3 (Prompt Definitivo)
> **Prompt:** "Actúa como un desarrollador Android senior experto en Jetpack Compose y Material3. Agrega una mejora funcional en MyAppointmentsScreen para permitir CANCELAR una cita:
> - Cada AppointmentCard con estado 'Confirmada' debe mostrar un ícono de basurero.
> - Al presionarlo, mostrar un AlertDialog con el texto '¿Seguro que deseas cancelar esta cita con [nombre del doctor]?', botón 'Cancelar cita' (color error) y 'Volver'.
> - Al confirmar, eliminar la cita de AppointmentRepository.citas (usando mutableStateListOf).
> - Las citas con estado 'Completada' NO deben mostrar la opción de cancelar.
> - Mantener integridad de navegación y cero errores en el editor."

* **Por qué es definitivo:**
  - Cumple estrictamente con todos los requisitos funcionales, de diseño Material 3, manejo de estado reactivo con Compose y pruebas de compilación exitosas.
* **Prueba / Verificación:** Compilado exitosamente con Gradle (`app:assembleDebug`) y validado en la UI.
