package com.tecsup.mibodega

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ClienteNavigationTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun cambiarSeccionesMantieneLasBarrasEnLaMismaPosicion() {
        iniciarSesion()
        val superior = limitesBarra("barra_superior_cliente")
        val inferior = limitesBarra("barra_inferior_cliente")

        val secciones = listOf(
            "favoritos" to "Mis Favoritos",
            "pedidos" to "Mis Pedidos",
            "perfil" to "Mi Perfil",
            "inicio" to "Categorías de productos"
        )
        repeat(2) {
            secciones.forEach { (seccion, titulo) ->
                composeRule.onNodeWithTag("nav_$seccion").performClick().assertIsSelected()
                composeRule.onNodeWithText(titulo).assertIsDisplayed()
                comprobarBarras(superior, inferior)
            }
        }
    }

    @Test
    fun volverAlCatalogoConservaLaBusqueda() {
        iniciarSesion()
        composeRule.onNode(hasSetTextAction()).performTextInput("Arroz")
        closeSoftKeyboard()

        composeRule.onNodeWithTag("nav_favoritos").performClick()
        composeRule.onNodeWithTag("nav_perfil").performClick()
        composeRule.onNodeWithTag("nav_inicio").performClick().assertIsSelected()

        composeRule.onNode(hasSetTextAction()).assertTextContains("Arroz")
        composeRule.onNodeWithText("Arroz Costeño").assertIsDisplayed()
        composeRule.onNodeWithText("Aceite Primor").assertDoesNotExist()
    }

    @Test
    fun detalleYCompraCambianSoloElCuerpoSinAcumularSecciones() {
        iniciarSesion()
        val superior = limitesBarra("barra_superior_cliente")
        val inferior = limitesBarra("barra_inferior_cliente")

        composeRule.onNodeWithText("Arroz Costeño").performClick()
        composeRule.onNodeWithText("Agregar al carrito").assertIsDisplayed()
        comprobarBarras(superior, inferior)

        composeRule.onNodeWithText("Agregar al carrito").performClick()
        composeRule.onNodeWithText("Mi Carrito").assertIsDisplayed()
        comprobarBarras(superior, inferior)

        composeRule.onNodeWithText("Continuar Pedido").performClick()
        composeRule.onNodeWithText("Datos de Pedido").assertIsDisplayed()
        comprobarBarras(superior, inferior)

        composeRule.onNodeWithTag("nav_perfil").performClick().assertIsSelected()
        composeRule.onNodeWithText("Mi Perfil").assertIsDisplayed()
        pressBack()
        composeRule.onNodeWithTag("nav_inicio").assertIsSelected()
        composeRule.onNodeWithText("Categorías de productos").assertIsDisplayed()
        comprobarBarras(superior, inferior)
    }

    @Test
    fun salirDeConfirmacionPorLaBarraConservaElPedidoYVaciaElCarrito() {
        iniciarSesion()
        val superior = limitesBarra("barra_superior_cliente")
        val inferior = limitesBarra("barra_inferior_cliente")

        composeRule.onNodeWithContentDescription("Agregar Arroz Costeño").performClick()
        composeRule.onNodeWithContentDescription("Carrito").performClick()
        composeRule.onNodeWithText("Continuar Pedido").performClick()
        composeRule.onNodeWithText("Recojo en Tienda").performClick()
        composeRule.onAllNodes(hasSetTextAction())[0].performTextInput("Cliente Demo")
        composeRule.onAllNodes(hasSetTextAction())[1].performTextInput("987654321")
        closeSoftKeyboard()
        composeRule.onNodeWithText("Confirmar Pedido").performScrollTo().performClick()

        composeRule.onNodeWithText("¡Pedido Confirmado!").assertIsDisplayed()
        comprobarBarras(superior, inferior)
        composeRule.onNodeWithTag("nav_pedidos").performClick().assertIsSelected()
        composeRule.onNodeWithText("Pedido #01").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Carrito").performClick()
        composeRule.onNodeWithText("Tu carrito está vacío").assertIsDisplayed()
        comprobarBarras(superior, inferior)
        composeRule.onNodeWithText("Explorar productos").performClick()
        composeRule.onNodeWithTag("nav_inicio").assertIsSelected()
        composeRule.onNodeWithText("Categorías de productos").assertIsDisplayed()
    }

    private fun iniciarSesion() {
        composeRule.onNodeWithText("Iniciar sesión").performClick()
        composeRule.onAllNodes(hasSetTextAction())[0].performTextInput("cliente")
        composeRule.onAllNodes(hasSetTextAction())[1].performTextInput("1234")
        closeSoftKeyboard()
        composeRule.onNodeWithText("Ingresar a Mi Bodega").performClick()
        composeRule.onNodeWithTag("nav_inicio").assertIsSelected()
    }

    private fun limitesBarra(etiqueta: String): Rect =
        composeRule.onNodeWithTag(etiqueta).fetchSemanticsNode().boundsInRoot

    private fun comprobarBarras(superior: Rect, inferior: Rect) {
        composeRule.onAllNodesWithTag("barra_superior_cliente").assertCountEquals(1)
        composeRule.onAllNodesWithTag("barra_inferior_cliente").assertCountEquals(1)
        composeRule.onNodeWithTag("barra_superior_cliente").assertIsDisplayed()
        composeRule.onNodeWithTag("barra_inferior_cliente").assertIsDisplayed()
        assertEquals(superior, limitesBarra("barra_superior_cliente"))
        assertEquals(inferior, limitesBarra("barra_inferior_cliente"))
    }
}
