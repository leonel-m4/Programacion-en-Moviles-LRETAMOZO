# Prompts utilizados

## Prompt 1
**Texto enviado a Gemini:** Agrega en el panel de soporte el tiempo promedio de resolución de los tickets cerrados calculando (fechaCierre - fecha). La consulta va en TicketDao, la función en AyudaRepository y su implementación, y el valor se expone como StateFlow en PanelViewModel. La View solo lo muestra.
**Que genero la IA:** Código de DAO, repositorio, ViewModel y UI para el tiempo promedio de resolución.
**Que tuve que corregir yo:** Ajusté el tipo de retorno a `Flow<Long>` y aseguré las importaciones de `kotlinx.coroutines.flow.map`.
**Respeta MVVM?:** Si. La View solo recolecta el `StateFlow`, el ViewModel procesa la lógica y el Repository/DAO manejan los datos.

## Prompt 2
**Texto enviado a Gemini:** Agrega un contador de SLA por prioridad en las tarjetas de tickets (Alta 4h, Media 24h, Baja 72h) evaluando el tiempo transcurrido desde la creación del ticket.
**Que genero la IA:** Lógica de cálculo de SLA dentro de `TicketCard.kt`.
**Que tuve que corregir yo:** Formateé la visualización del tiempo restante y los colores de alerta si el SLA está vencido.
**Respeta MVVM?:** Si. Es una función de presentación pura basada en los datos del ticket.

## Prompt 3
**Texto enviado a Gemini:** Implementa búsqueda avanzada en la bandeja de soporte permitiendo filtrar simultáneamente por texto, estado y prioridad.
**Que genero la IA:** Filtrado múltiple en `BandejaViewModel` combinando flujos y controles de filtrado en `BandejaScreen`.
**Que tuve que corregir yo:** Conecté correctamente los estados reactivos `_texto`, `_prioridad` y `_estado` usando `combine`.
**Respeta MVVM?:** Si. La View emite eventos de filtrado al ViewModel y observa el estado filtrado (`StateFlow`).

## Reflexion (5 lineas)
- La IA ayudó a estructurar rápidamente las consultas SQL, flujos y componentes de Compose.
- Fue necesario verificar la consistencia de tipos entre la base de datos y la UI.
- Aprendí a mantener estrictamente la separación de responsabilidades en la arquitectura MVVM.
- Room y Kotlin Flows facilitan la reactividad en tiempo real de los datos.
- El uso de Repository como única fuente de verdad simplifica la ampliación de funcionalidades.
