# Prompts de la Fase 2 — Mejora con IA (Rediseño Visual Completo)

## Prompt 1: Rediseño Visual Completo y Búsqueda en Tiempo Real

```text
Analiza completamente el proyecto Android Jetpack Compose “MiBodega” que actualmente tengo abierto.

Estoy trabajando en la FASE 2 — MEJORA CON IA.

La Fase 1 ya está terminada y funcional. En esta Fase 2 quiero que la aplicación tenga una mejora visible y significativa, no solamente un pequeño cambio de código.

IMPORTANTE:
El proyecto ya está abierto en la rama mejora-ia.
NO crees ramas.
NO hagas commits.
NO hagas push.

==================================================
OBJETIVO PRINCIPAL
==================================================

Mejora de forma significativa TODA la interfaz y experiencia visual de la aplicación MiBodega utilizando Jetpack Compose.

La aplicación debe dejar de verse como un prototipo básico de laboratorio y pasar a verse como una aplicación de bodega / minimarket moderna, limpia, consistente y profesional.

Puedes modificar la interfaz de todas las pantallas existentes.

NO quiero que simplemente cambies uno o dos colores.

Quiero una mejora visual real y claramente visible entre la Fase 1 y la Fase 2.

Debes conservar el flujo y propósito de la aplicación, pero puedes rediseñar ampliamente su presentación visual.

==================================================
MEJORA OBLIGATORIA DE LA FASE 2
==================================================

Además del rediseño general, hay un requisito que DEBE implementarse sí o sí:

“El campo de búsqueda de la Pantalla 3 (Inicio) debe filtrar la lista de productos en tiempo real a medida que el usuario escribe, combinándose correctamente con el filtro de categoría ya existente.”

ESTO ES OBLIGATORIO.

La búsqueda debe:

- funcionar en tiempo real;
- actualizar la lista mientras el usuario escribe;
- buscar por nombre del producto;
- ignorar mayúsculas y minúsculas;
- utilizar el buscador existente o uno rediseñado que conserve la misma función;
- funcionar junto con el filtro de categorías;
- NO reemplazar el filtro por categoría.

La condición conceptual debe ser:

coincideCategoria && coincideBusqueda

Comportamiento obligatorio:

Todos + buscador vacío
→ todos los productos.

Todos + “arroz”
→ productos cuyo nombre contenga “arroz”.

Bebidas + buscador vacío
→ solamente Bebidas.

Bebidas + “coca”
→ solamente productos de Bebidas cuyo nombre contenga “coca”.

Abarrotes + texto
→ buscar dentro de Abarrotes.

Snacks + texto
→ buscar dentro de Snacks.

Sin coincidencias
→ mostrar un estado vacío visualmente agradable.

Al borrar el texto
→ volver a mostrar los productos de la categoría seleccionada.

==================================================
REDISEÑO VISUAL COMPLETO
==================================================

Revisa todas las pantallas existentes y mejóralas visualmente.

NO debes crear una aplicación diferente, pero sí puedes cambiar ampliamente su diseño.

Quiero una interfaz moderna inspirada en aplicaciones actuales de delivery, minimarket y comercio electrónico.

Usa un estilo consistente en toda la aplicación:

- jerarquía visual clara;
- buen uso del espacio;
- márgenes y paddings consistentes;
- tipografías bien diferenciadas;
- cards modernas;
- colores coherentes;
- esquinas redondeadas;
- botones visualmente atractivos;
- imágenes de productos;
- iconografía clara;
- estados vacíos;
- feedback visual;
- separación correcta entre secciones.

==================================================
1. PANTALLA DE BIENVENIDA
==================================================

Mejora completamente la presentación.

Debe incluir:

- logo o representación visual de Mi Bodega;
- nombre “Mi Bodega” destacado;
- mensaje corto de bienvenida;
- composición moderna y centrada;
- botón principal claro;
- botón secundario;
- mejor uso del espacio;
- fondo agradable y coherente con el resto de la app.

Debe verse como una pantalla inicial real de una aplicación móvil.

==================================================
2. CREAR CUENTA
==================================================

Mejora:

- encabezado;
- campos de texto;
- espaciados;
- iconos dentro de los campos si corresponde;
- botón Crear cuenta;
- organización visual;
- legibilidad.

Debe verse limpia, moderna y profesional.

==================================================
3. INICIO
==================================================

Esta debe ser la pantalla visualmente más trabajada.

Debe incluir:

- header moderno con nombre de la aplicación;
- icono del carrito;
- contador del carrito si ya existe;
- buscador moderno;
- categorías visualmente atractivas;
- lista o grid de productos bien diseñado;
- imágenes reales/locales o recursos visuales para cada producto;
- nombre del producto;
- precio destacado;
- botón de agregar claramente visible;
- buena separación entre productos.

Puedes rediseñar las tarjetas completamente.

El usuario debe poder distinguir claramente cada producto.

==================================================
IMÁGENES DE PRODUCTOS
==================================================

Actualmente varios productos pueden tener placeholders.

Quiero mejorar esto.

Agrega imágenes para los productos.

Preferencia:

Usar imágenes LOCALES dentro de:

res/drawable

y mostrarlas utilizando:

painterResource(...)

Evita depender de URLs externas.

Si es necesario, modifica Producto.kt para agregar algo como:

val imagenRes: Int

y actualiza los productos de ejemplo en DatosFake.kt para asignar una imagen diferente a cada producto.

Ejemplo conceptual:

Producto(
    id = 1,
    nombre = "Arroz Costeño",
    precio = 4.50,
    categoria = "Abarrotes",
    imagenRes = R.drawable.arroz_costeno
)

Haz lo mismo con productos como:

- Arroz Costeño
- Aceite Primor
- Leche Gloria
- Galleta Oreo
- Coca-Cola

Si el entorno permite crear recursos gráficos, agrégalos al proyecto.

Si no puede generar fotografías directamente, crea recursos visuales locales coherentes, drawables o imágenes representativas y deja la estructura preparada correctamente.

NO uses un único icono genérico para todos los productos.

==================================================
4. CATEGORÍAS
==================================================

Mejora visualmente las categorías.

Pueden utilizar:

- icono;
- nombre;
- contenedor visual;
- estado seleccionado claramente distinguible.

Deben conservar el funcionamiento de:

Todos
Bebidas
Abarrotes
Snacks

Y deben seguir trabajando junto con el buscador obligatorio.

==================================================
5. DETALLE DEL PRODUCTO
==================================================

Rediseña la pantalla para que se parezca a una página real de producto.

Debe destacar:

- imagen grande del producto;
- nombre;
- precio;
- descripción;
- selector de cantidad;
- botón “Agregar al carrito”;
- botón de volver.

Haz que la imagen del producto sea protagonista.

==================================================
6. CARRITO
==================================================

Mejora completamente las filas del carrito.

Cada producto debe mostrar:

- miniatura;
- nombre;
- precio;
- cantidad;
- controles + y -;
- eliminar.

El resumen debe mostrar claramente:

Subtotal
Costo de delivery
Total

El Total debe tener mayor jerarquía visual.

El botón “Continuar pedido” debe ser claro y moderno.

Si el carrito queda vacío, muestra un estado vacío agradable con:

- icono o ilustración;
- mensaje;
- indicación para agregar productos.

==================================================
7. DATOS DE ENTREGA
==================================================

Mejora:

- formulario;
- títulos;
- campos;
- métodos de pago;
- RadioButtons;
- separación de secciones;
- botón Confirmar pedido.

Debe ser fácil de leer y llenar.

==================================================
8. CONFIRMACIÓN DEL PEDIDO
==================================================

Debe verse como una pantalla de éxito real.

Incluye:

- check visual destacado;
- mensaje “¡Pedido realizado!”;
- resumen;
- total;
- dirección;
- número del pedido;
- botón para volver al inicio.

Haz que la pantalla tenga una sensación clara de confirmación exitosa.

==================================================
TEMA VISUAL
==================================================

Crea un estilo coherente para toda la app.

Usa una paleta relacionada con Mi Bodega.

Puedes mejorar:

Color.kt
Theme.kt
Type.kt

si es necesario.

Mantén Material 3.

Utiliza un color verde como identidad principal, pero puedes agregar tonos secundarios y neutros para conseguir una interfaz más profesional.

Evita:

- colores extremadamente saturados;
- elementos enormes;
- interfaces infantiles;
- exceso de sombras;
- exceso de gradientes;
- elementos inconsistentes entre pantallas.

==================================================
COMPONENTES REUTILIZABLES
==================================================

Revisa los componentes existentes.

Si existen componentes como:

ProductoCard
BotonPrimario
CampoTexto
SelectorCantidad

mejóralos para que el nuevo estilo se aplique consistentemente en toda la app.

Puedes crear nuevos componentes reutilizables SOLO si ayudan realmente a evitar duplicación.

==================================================
ANIMACIONES
==================================================

Puedes agregar animaciones pequeñas y naturales usando APIs nativas de Compose.

Por ejemplo:

- AnimatedVisibility;
- animateContentSize;
- cambios suaves al seleccionar categorías;
- feedback al agregar productos.

No uses animaciones exageradas.

==================================================
ESTADOS VACÍOS
==================================================

Agrega una presentación visual correcta cuando:

- la búsqueda no encuentra productos;
- el carrito está vacío.

Evita simplemente dejar una pantalla en blanco.

==================================================
BUENAS PRÁCTICAS
==================================================

Además del diseño:

- elimina imports innecesarios;
- evita código duplicado;
- conserva state hoisting;
- mantén el código legible;
- evita recomposiciones innecesarias evidentes;
- usa remember correctamente;
- mantén componentes pequeños y reutilizables cuando tenga sentido.

==================================================
RESTRICCIONES
==================================================

NO cambies el package principal.

NO agregues backend.

NO agregues Room.

NO agregues Retrofit.

NO conviertas el proyecto completo a una arquitectura distinta.

NO elimines las funcionalidades de la Fase 1.

NO rompas:

- navegación;
- carrito;
- cantidades;
- categorías;
- creación de cuenta;
- detalle;
- entrega;
- confirmación.

NO agregues funcionalidades que cambien completamente el alcance del laboratorio.

La prioridad es:

MEJORAR LA INTERFAZ + UX + IMPLEMENTAR LA BÚSQUEDA OBLIGATORIA.

==================================================
IMPORTANTE SOBRE EL DISEÑO
==================================================

La nueva versión debe tener una diferencia VISUAL CLARA respecto a la Fase 1.

Cuando se comparen ambas versiones debe notarse que la rama mejora-ia recibió una mejora real mediante IA.

No quiero que la Fase 2 se vea exactamente igual a la Fase 1 con únicamente un buscador funcionando.

==================================================
ARCHIVOS
==================================================

Antes de modificar:

1. analiza todos los archivos relevantes;
2. identifica cuáles realmente necesitan cambios;
3. modifica directamente esos archivos.

Puedes modificar varios archivos si es necesario para conseguir un resultado consistente.

==================================================
AL TERMINAR
==================================================

NO hagas commits.

NO hagas push.

Dame un resumen detallado indicando:

1. todos los archivos modificados;
2. mejoras visuales realizadas;
3. recursos/imágenes agregados;
4. cambios realizados en Producto y DatosFake, si existen;
5. cómo funciona la búsqueda en tiempo real;
6. cómo funciona búsqueda + categorías;
7. mejoras realizadas en cada pantalla;
8. mejoras realizadas en componentes reutilizables;
9. pruebas manuales que debo realizar;
10. posibles errores que deba revisar en Android Studio.

También divide todos los cambios realizados en un plan de mínimo 3 commits descriptivos para que yo pueda hacerlos manualmente después.

La mejora obligatoria del buscador debe estar implementada sí o sí aunque realices todo el rediseño.
```
