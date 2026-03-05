import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    isMainScreen: Boolean,
    onBackClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF448AFF),
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            if (!isMainScreen) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar"
                    )
                }
            }
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = onLogoutClick) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Cerrar Sesión"
                    )
                }
            }
        }
    )
}