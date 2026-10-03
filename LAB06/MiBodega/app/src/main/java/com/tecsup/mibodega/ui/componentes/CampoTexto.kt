package com.tecsup.mibodega.ui.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Campo de entrada estilizado con soporte para íconos, contraseñas y mensajes de error.
 */
@Composable
fun CampoTexto(
    etiqueta: String,
    valor: String,
    onValorCambia: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    teclado: KeyboardType = KeyboardType.Text,
    iconoLeading: ImageVector? = null,
    esPassword: Boolean = false,
    esError: Boolean = false
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value = valor,
            onValueChange = onValorCambia,
            modifier = Modifier.fillMaxWidth(),
            placeholder = placeholder?.let { { Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) } },
            leadingIcon = iconoLeading?.let {
                { Icon(imageVector = it, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
            },
            singleLine = true,
            isError = esError,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = teclado),
            visualTransformation = if (esPassword) PasswordVisualTransformation() else VisualTransformation.None,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorContainerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}
