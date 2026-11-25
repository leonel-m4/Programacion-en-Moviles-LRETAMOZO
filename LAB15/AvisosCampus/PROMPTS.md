# PROMPTS.md · Fase 2 de AvisosCampus

Rama `mejora-ia`. Mejoras elegidas de la tabla de la guía (mínimo 3): **Dos canales**, **Texto largo** y **Receptor del cargador**.

| # | Prompt que escribí | Qué respondió | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- | --- |
| 1 | «Ahora con la fase 2» | Propuso llevar primero los 10 commits de LAB15 de `sprint-2` a `mejora-ia` con `cherry-pick`, como en el LAB14. | Se aceptó el `cherry-pick` y se descartó un `merge` completo de `sprint-2`. | El `merge` habría traído commits de otros laboratorios. El `cherry-pick` solo trae los de LAB15. |
| 2 | «Espera, dame toda la Fase 2 del documento aquí en el chat antes de que hagas algo» | Copió la Fase 2 de la guía: la tabla de 6 mejoras, las reglas, la tabla de `PROMPTS.md` y los entregables. | No se cambió código hasta leer las reglas. | La IA propone, yo decido: primero hay que saber qué pide la guía. |
| 3 | «Mínimo 3, ¿verdad? Pero, ¿qué deben tener?» | Explicó que al menos una mejora debe cambiar el Repository o el ViewModel. Texto largo solo cambia el Notificador, así que no puede ser la única que cuente para esa regla. | — | Para elegir las mejoras sin romper la regla de capas. |
| 4 | «Entonces haz mínimo 3» | Eligió Dos canales, Texto largo y Receptor del cargador. | Se descartó Push de prueba. | Necesita un proyecto en la consola de Firebase con mi cuenta y la Parte B de la guía de Firebase, que no tengo. |
| 5 | Mejora 1 · Dos canales (dentro del prompt 4) | Creó el canal **Urgentes** con `IMPORTANCE_HIGH` junto a **Avisos**. Agregó `urgente` al `Aviso`; el ViewModel emite el recordatorio con `urgente = true` y el `Notificador` elige el canal. | Se descartó que el `Notificador` decidiera por su cuenta mirando el origen. | La guía dice que cambian el Notificador **y el ViewModel**: el ViewModel decide qué es urgente sin usar `Context`, y el `Notificador` solo habla con el sistema. |
| 6 | Mejora 2 · Texto largo (dentro del prompt 4) | Agregó `setStyle(NotificationCompat.BigTextStyle().bigText(aviso.texto))` al builder. | Se dejaron los textos de la guía tal cual («Tu entrega fue registrada»…). | Son los datos ficticios del PDF. `BigTextStyle` muestra el texto completo al expandir aunque sea más largo que una línea. |
| 7 | Mejora 3 · Receptor del cargador (dentro del prompt 4) | Creó `CargadorReceiver`, que escucha `ACTION_POWER_CONNECTED` y `ACTION_POWER_DISCONNECTED`. Se registra en un `DisposableEffect` con su propio switch **Escuchar el cargador** y se quita en `onDispose`. El ViewModel registra **Cargador · Conectado / Desconectado** con el origen nuevo `CARGADOR`. | Para probarlo se descartó `adb shell dumpsys battery unplug`, que no envía esos anuncios, y se usó `adb emu power ac off` / `on`. | Igual que el receptor del modo avión: `onReceive` es corto, el ViewModel no conoce el `BroadcastReceiver` y el receptor se quita al apagar el switch (comprobado: 0 receptores registrados y ningún aviso). |

## Revisión de las reglas de la Fase 2

- **Sin `Context` ni `NotificationManager` en el ViewModel:** `viewmodel/` solo los menciona en el comentario de la guía.
- **Todo receptor se quita en `onDispose`:** el del modo avión y el del cargador.
- **`PendingIntent`:** se mantiene `FLAG_IMMUTABLE`; ninguna mejora necesitó uno mutable.
