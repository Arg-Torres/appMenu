// MainActivity.kt
package com.example.menurapido
import android.content.res.Configuration
import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.FontScalingLinear
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.menurapido.ui.theme.MenuRapidoTheme
import org.intellij.lang.annotations.PrintFormat





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MenuRapidoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

//Clase de que muestra un elemento del menu

data class MenuItem(
    val name:String, //Nombre del item
    val price: String, //Precio del producto
    val imageRes: Int,
    val description:String
)

//Enumeracion que define las diferentes pantallas de la app
enum class Screen {
    Home,Menu,About,Contact
}


@Composable
fun MainScreen(){
//Estado que controla si se muestra el menu
    var showMenu by remember { mutableStateOf(false) }
//Estado que almacena el itme seleccionado
    var selectedItem by remember { mutableStateOf<MenuItem?>(null)}
//Estado que rastrea la pantalla actual
    var currentScreen by remember { mutableStateOf(Screen.Home) }


//Navegacion entre pantallas
    when {
//Si hay un item selecciona, muestra la pantalla detalles
        selectedItem != null -> {
            MenuItemDetailScreen(menuItem = selectedItem!!, onBackClick ={selectedItem=null})
        }
//Si se debe mostrar el menu, muestra esa pantalla
        showMenu-> {
            FastFoodMenuScreen(onBackClick = {showMenu = false}, onItemClick = {selectedItem = it})
        }
// De lo contrario, muestra la pantalla correspondiente al estado actual.
        else -> {when (currentScreen){
            Screen.Home -> HomeScreen (
                onStartClick = {currentScreen = Screen.Menu},
                onAboutClick = {currentScreen = Screen.About},
                onContactClick = {currentScreen = Screen.Contact}
            )
            Screen.Menu -> FastFoodMenuScreen(
                onBackClick = {currentScreen = Screen.Home},
                onItemClick = {selectedItem = it}
            )
            Screen.About -> AboutScreen(onBackClick = {currentScreen = Screen.Home})
            Screen.Contact -> ContactScreen(onBackClick = {currentScreen = Screen.Home})


        }
        }


    }
}

@Composable
fun HomeScreen (onStartClick : () -> Unit, //Accion al ejecutar ver menu
                onAboutClick : () -> Unit, //Accion al ejecutar Ver nosotros
                onContactClick:() -> Unit){ //Accion al ejecutar contactos

//Columna que nos permite organizar los elementos verticales
    Column(modifier = Modifier.fillMaxSize(), //Ocupa todo el espacio disnible
        verticalArrangement = Arrangement.Center, //Centra los elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally) { //Centra los elementos horizontalmente
//TextoBienvenida
        Text(text = "Bienvenidos a FastFood APP", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))//Espacio vertical de 16 dp

        MyImage()//Imagen correspondiente al logo
        Spacer(modifier = Modifier.height(16.dp))//Espacio vertical de 16 dp


//Boton Contactos
        Button(onClick = onStartClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Ver Menú")
        }
//Boton Nosotros
        Button(onClick = onAboutClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Nosotros")
        }

//Boton Contactos
        Button(onClick = onContactClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Contáctanos")
        }


    }
}

@Composable
fun MyImage() {
    Image(
        painter = painterResource(R.drawable.logooo),
        contentDescription = "Este es mi logo",
        modifier = Modifier
            .size(300.dp)
            .padding(8.dp)
    )
}


@Composable
fun AboutScreen(onBackClick:()->Unit){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Acerca de nosotros", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))//Espacio vertical de 16 dp
        MyImage()//Imagen correspondiente al logo
        Text(text = "Somos una empresa dedicada a ofrecer la mejor comida rapida a nuestros clientes", fontSize = 16.sp,
            modifier = Modifier.padding(50.dp),
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))//Espacio vertical de 16 dp
        Button(onClick = onBackClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Volver a inicio")
        }
    }
}
@Composable
fun ContactScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contactos",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E88E5)
        )

        Spacer(modifier = Modifier.height(8.dp))

        MyImage() // Imagen del logo de la app

        ContactCard(
            name = "Argenis de Jesús Torres Rodríguez",
            phone = "829-805-7226",
            email = "20220847@miucateci.edu.do"
        )

        ContactCard(
            name = "Enmanuel Maria Gonzalez Bautista",
            phone = "809-297-7420",
            email = "20220228@miucateci.edu.do"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBackClick,
            modifier = Modifier
                .width(220.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
        ) {
            Text(text = "Volver al Inicio", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun ContactCard(name: String, phone: String, email: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Teléfono: $phone", fontSize = 14.sp)
            Text(text = "Correo: $email", fontSize = 14.sp)
        }
    }
}


@Composable
fun FastFoodMenuScreen(onBackClick: () -> Unit, onItemClick: (MenuItem) -> Unit) {
    val menuItems = remember {
        listOf(
            MenuItem("Hamburguesa Clásica", "$350.00", R.drawable.hamburguesa, "Deliciosa Hamburguesa con carne jugosa y pan suave"),
            MenuItem("Papas Fritas", "$250.00", R.drawable.papasfritas, "Muy Crujientes"),
            MenuItem("Refresco", "$50.00", R.drawable.coca, "Bebida refrescante"),
            MenuItem("Sandwich", "$350.00", R.drawable.sand, "Muy Ricos"),
            MenuItem("Refresco", "$50.00", R.drawable.fanta, "Bebida refrescante")
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text="Nuestro Menú", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(menuItems.size){
                    index -> MenuItemCard(menuItems[index], onItemClick)
            }
        }
        Button(onClick = onBackClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Volver a inicio")
        }
    }
}

@Composable
fun MenuItemCard(menuItem: MenuItem, onItemClick: (MenuItem) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemClick(menuItem) }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = menuItem.imageRes),
                contentDescription = menuItem.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = menuItem.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = menuItem.price, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}


@Composable
fun MenuItemDetailScreen(menuItem: MenuItem, onBackClick: () -> Unit) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(id = menuItem.imageRes),
            contentDescription = menuItem.name,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = menuItem.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = menuItem.price, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = menuItem.description, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Volver a inicio")
        }
    }


}


@Preview
@Composable
fun PreviewMenu(){
    MainScreen()
}