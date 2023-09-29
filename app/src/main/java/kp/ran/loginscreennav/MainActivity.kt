@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)

package kp.ran.loginscreennav

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kp.ran.loginscreennav.ui.theme.LogInScreenNavTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogInScreenNavTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "mainScreen"
                    ) {
                        composable("mainScreen") {
                            ScreenDemo(navController)
                        }
                        composable(
                            "infoScreen/{name}/{mobile}",
                            arguments = listOf(
                                navArgument("name") { type = NavType.StringType },
                                navArgument("mobile") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val name = backStackEntry.arguments?.getString("name")
                            val mobile = backStackEntry.arguments?.getString("mobile")
                            InfoScreen(name, mobile)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenDemo(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var displayedText by remember { mutableStateOf("") }
    val context = LocalContext.current

    var agreeToTerms by remember { mutableStateOf(false) } // Checkbox state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            fontSize = 32.sp,
            text = "Sign up.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight(700),

            )
        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        )

        TextField(
            value = mobile,
            onValueChange = { mobile = it },
            singleLine = true,
            placeholder = { Text("Mobile") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = agreeToTerms,
                onCheckedChange = { isChecked ->
                    agreeToTerms = isChecked
                },
            )

            Text("I agree to the terms and conditions")
        }
        Button(
            onClick = {
                if (!agreeToTerms) {
                    Toast.makeText(
                        context,
                        "Please agree to the terms and conditions first",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val toastMessage = "Name: $name\nMobile: $mobile"
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Show Toast")
        }

        Button(
            onClick = {
                if (!agreeToTerms) {
                    Toast.makeText(
                        context,
                        "Please agree to the terms and conditions first",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    displayedText = "Name: $name\nMobile: $mobile"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Show Text Element")
        }

        Button(
            onClick = {
                if (!agreeToTerms) {
                    Toast.makeText(
                        context,
                        "Please agree to the terms and conditions first",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    navController.navigate("infoScreen/$name/$mobile")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Go to Info Screen")
        }

        if (displayedText.isNotEmpty()) {
            Text(
                text = displayedText,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
@Composable
fun InfoScreen(name: String?, mobile: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Name: $name")
        Text("Mobile: $mobile")
    }
}
