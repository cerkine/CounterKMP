package com.example.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource

import demo.shared.generated.resources.Res
import demo.shared.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var counter by remember { mutableIntStateOf(0) }
        val increment: (Int) -> Unit = { value -> counter += value }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }

            AnimatedVisibility(!showContent) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CounterCard(
                        modifier = Modifier.fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}



@Composable
fun Clicker(onIncrement: (Int) -> Unit, value: Int) {
    Button(onClick = { onIncrement(value) }) {
        Text(" + $value ")
    }
}

@Composable
fun NumberView(counter: Int) {
    Text(text = "$counter")
}

@Preview(showBackground = true)
@Composable
fun CounterCard(modifier: Modifier = Modifier, viewModel: CounterViewModel = viewModel { CounterViewModel()}) {
    val counter by viewModel.counter.collectAsState()
    val lambdaIncrement :(Int) -> Unit  = {viewModel.increment(it)}
    Column(modifier = modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        NumberView(counter = counter)
        Row {
            Clicker(onIncrement = lambdaIncrement, value = 1)
            Clicker(onIncrement = lambdaIncrement, value = 10)
            Clicker(onIncrement = lambdaIncrement, value = 100)
        }
    }
}