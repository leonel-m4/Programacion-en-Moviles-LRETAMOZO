# Lista de Tareas - Tecsup

Este proyecto es una aplicación nativa de Android desarrollada en **Kotlin** utilizando **Jetpack Compose** y los principios de diseño de **Material 3**. La aplicación implementa una lista de tareas (To-Do List) interactiva con un diseño moderno, limpio y profesional.

---

## 🎨 Especificaciones de Diseño UI/UX

La interfaz ha sido rediseñada minuciosamente para cumplir con los siguientes estándares visuales y funcionales:

### 🌟 Colores del Sistema
*   **Fondo de la Aplicación:** Gris azulado muy claro (`#F1F4F9`) que cubre toda la pantalla de borde a borde.
*   **Color Primario (Títulos y Botones):** Azul marino profundo (`#1D246A`).
*   **Bordes de Campos:** Lila/morado suave (`#7A6FA8`).
*   **Texto del Contador:** Gris oscuro medio (`#434448`).
*   **Bordes de Checkbox:** Gris oscuro (`#3C3C3C`).

### 📱 Comportamiento del Layout y Barras del Sistema
*   **Pantalla Completa (Edge-to-Edge):** El fondo se extiende de manera fluida por detrás de las barras de estado y navegación.
*   **Soporte de Insets:** Implementación de `Modifier.safeDrawingPadding()` para evitar colisiones con la barra de estado, la barra de navegación o el notch del dispositivo.
*   **Centrado Vertical:** El bloque principal de la interfaz está centrado verticalmente en la pantalla para una mejor ergonomía visual.
*   **Altura Dinámica:** La lista de tareas (`LazyColumn`) utiliza `wrapContentHeight` para permitir el correcto centrado del grupo de componentes cuando hay pocos elementos.

---

## 🏗️ Estructura de la Interfaz (Orden Exacto)

1.  **Título Principal:** "Lista de tareas - Tecsup" en negrita (`FontWeight.Bold`), centrado horizontalmente.
2.  **Campo de Entrada (`OutlinedTextField`):** Con la etiqueta *"¿Qué tarea tienes pendiente?"*, bordes redondeados a `8.dp` y fondo transparente.
3.  **Botón de Acción:** Texto *"Agregar tarea"* en negrita sobre un fondo azul marino con forma de **cápsula completa** (`RoundedCornerShape(50)`).
4.  **Contador de Tareas:** Texto centrado que indica de manera dinámica el total de tareas registradas.
5.  **Lista Dinámica (`LazyColumn`):** Separación uniforme de `8.dp` entre cada tarjeta.

### 🗂️ Componente de Tarea (`ItemTarea`)
Cada elemento se representa mediante una tarjeta blanca pura (`Color.White`) con esquinas curvas de `12.dp` y una elevación/sombra suave de `3.dp`. Cuenta con:
*   **Checkbox:** Borde personalizado sin relleno cuando no está marcado.
*   **Texto:** Nombre de la tarea con grosor medio (`FontWeight.Medium`).
*   **Botón de Eliminación:** Representado de forma minimalista por el emoji nativo de basurero (🗑️) altamente responsivo al tacto.

---

## 📝 Prompt Utilizado para el Rediseño

Para lograr este acabado visual exacto, se utilizó el siguiente prompt estructurado:

```text
Actúa como un desarrollador Android senior experto en Jetpack Compose y Material3, con control absoluto sobre el código. No pidas permiso ni confirmación, no hagas preguntas de seguimiento: toma tus propias decisiones en lo no especificado y entrega el resultado final directo.

Necesito que rediseñes mi composable PantallaTareas e ItemTarea del archivo MainActivity.kt para que el resultado sea IDÉNTICO a esta interfaz de referencia. Usa estos valores EXACTOS medidos de la imagen:

COLORES (definir como constantes):
val FondoApp = Color(0xFFF1F4F9)
val AzulMarino = Color(0xFF1D246A)
val BordeCampo = Color(0xFF7A6FA8)
val GrisContador = Color(0xFF434448)
val GrisCheckbox = Color(0xFF3C3C3C)

MANEJO DE BARRAS DEL SISTEMA (status bar y navigation bar):
- Usa Modifier.safeDrawingPadding() en el contenedor raíz para manejar automáticamente TODOS los insets del sistema (status bar, navigation bar, notch)
- El fondo (FondoApp) se extiende edge-to-edge por detrás de esas barras, pero el contenido interactivo respeta el padding para no quedar tapado ni pegado a los botones de navegación

APARIENCIA PROFESIONAL Y PANTALLA COMPLETA:
- El Surface/Box raíz debe usar Modifier.fillMaxSize() con background = FondoApp, cubriendo TODA la pantalla de borde a borde
- Si usas Scaffold, containerColor = FondoApp, y aplica correctamente el innerPadding sin que se asome color blanco por defecto
- El contenido permanece centrado verticalmente como grupo — NO estires el LazyColumn ni las tarjetas para ocupar todo el alto
- Consistencia visual: mismo radio de esquinas, misma tipografía (MaterialTheme.typography), espaciados uniformes

IMPORTANTE - COMPORTAMIENTO DEL LAYOUT:
- El Column principal debe usar Modifier.fillMaxSize() y verticalArrangement = Arrangement.Center
- El LazyColumn dentro del Column debe usar Modifier.wrapContentHeight(), sin weight(1f)
- El Column principal debe tener Modifier.padding(horizontal = 16.dp)

ESTRUCTURA Y ORDEN EXACTO (de arriba hacia abajo):

1. TÍTULO "Lista de tareas - Tecsup":
   - Modifier.fillMaxWidth(), textAlign = TextAlign.Center
   - color = AzulMarino, FontWeight.Bold, style = MaterialTheme.typography.headlineSmall
   - Spacer(height = 24.dp) después

2. OutlinedTextField "¿Qué tarea tienes pendiente?":
   - Ancho completo
   - focusedBorderColor y unfocusedBorderColor = BordeCampo
   - shape = RoundedCornerShape(8.dp), singleLine = true
   - Spacer(height = 8.dp) después

3. Button "Agregar tarea":
   - Ancho completo, altura ~48.dp
   - containerColor = AzulMarino, contentColor = Color.White
   - shape = RoundedCornerShape(50)
   - FontWeight.Bold
   - Spacer(height = 24.dp) después

4. Text "Total de tareas: X":
   - Modifier.fillMaxWidth(), textAlign = TextAlign.Center
   - color = GrisContador, sin negrita, style = MaterialTheme.typography.bodyLarge
   - Spacer(height = 12.dp) después

5. LazyColumn con las tarjetas:
   - Modifier.wrapContentHeight()
   - verticalArrangement = Arrangement.spacedBy(8.dp)

ITEMTAREA (cada tarjeta):
- Card: containerColor = Color.White, shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
- Row interno con padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
- Checkbox a la izquierda, borde GrisCheckbox, sin relleno cuando está desmarcado
- Spacer(width = 8.dp)
- Nombre de la tarea: color negro/gris oscuro, FontWeight.Medium, bodyLarge
- Ícono eliminar: Text("🗑️", fontSize = 20.sp, modifier = Modifier.clickable { onEliminar() })

REGLAS OBLIGATORIAS:
- No cambies la lógica de estados existente (mutableStateOf, remember, mutableStateListOf, contadorId, listaTareas)
- Mantén los nombres de funciones y parámetros existentes: PantallaTareas, ItemTarea, onEliminar, onCambiarEstado, tarea
- Respeta el orden, espaciados, alineaciones centradas, fondo de pantalla completa y manejo de barras del sistema indicados arriba
```

---

## 💻 Características Técnicas
*   **Gestión de Estado Eficiente:** Uso nativo de `remember`, `mutableStateOf` y `mutableStateListOf` para una recomposición reactiva y fluida.
*   **Rendimiento Optimizado:** Carga diferida de elementos con claves únicas (`key`) en la lista para evitar re-renderizados unnecessarily.
*   **Cero Dependencias Externas:** Construido usando puramente las librerías base de Jetpack Compose y Material 3 para garantizar un menor tamaño de APK y máxima estabilidad.
