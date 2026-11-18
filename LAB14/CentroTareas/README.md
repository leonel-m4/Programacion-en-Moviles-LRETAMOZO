# CentroTareas

Aplicación del Laboratorio 14 de Programación en Móviles: tareas largas en segundo plano con corrutinas, sin congelar la pantalla.

## Observación del Paso 1

Al pulsar **Tarea larga (MAL)** y enseguida **Tócame**, la pantalla deja de responder durante 4 segundos: el contador no suma, los botones no reaccionan y nada se vuelve a dibujar. Cuando termina la espera, los toques pendientes se procesan de golpe y el texto pasa directamente a **Terminó**.

**Trabajando...** no llega a verse porque `tareaLargaMala()` corre en el hilo principal, que es el mismo que dibuja la pantalla. El valor cambia a «Trabajando...», pero `Thread.sleep(4000)` bloquea el hilo antes de que Compose pueda dibujar un nuevo cuadro. Cuando el hilo se libera, el valor ya es «Terminó», así que solo se dibuja el estado final.

Si la espera fuera de 10 segundos, Android detectaría que la app no responde a los toques (pasados unos 5 segundos) y mostraría el diálogo **ANR: «CentroTareas no responde»**, con las opciones de esperar o cerrar la app.
