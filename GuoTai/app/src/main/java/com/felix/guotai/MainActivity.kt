package com.felix.guotai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowInsetsControllerCompat
import com.felix.guotai.remote.Remote
import com.felix.guotai.ui.theme.GuoTaiTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        runOnIO {
            Remote.loadStock()
        }
        setContent {
            GuoTaiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (Remote.loaded) {
                        StockDisplay()
                    } else {
                        LoadingScreen()
                    }
                }
            }
        }
    }
}

fun runOnIO(lambda: suspend () -> Unit){
    runBlocking {
        launch(Dispatchers.IO) { lambda() }
    }
}