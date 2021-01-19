package com.onmobile.sdk.gamification

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

internal class GamificationPage : AppCompatActivity() {
    private var resultData: String? = null

    companion object{
        var dataCallback: Callback? = null
        set(value){
            field = value
        }
    }

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }

    @ExperimentalMaterialApi
    @Composable
    private fun MyApp() {

        val data = if(intent.hasExtra("data"))
            intent.getSerializableExtra("data") as GameInputData
        else
            GameInputData()

        val scaffoldState = rememberScaffoldState()
        /*val snackbarVisibleState = remember { mutableStateOf(false) }
        val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
        val lifecycleScope = rememberCoroutineScope()*/

        var text by savedInstanceState { data.message ?: "" }
        resultData = data.message

        MaterialTheme {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        title = { Text(data.title ?: "Gamification SDK") },
                        navigationIcon = {
                            IconButton(onClick = {
                                onBackPressed()
                            }) {
                                Icon(Icons.Filled.ArrowBackIos)
                            }
                        }
                    )
                },
                //snackbarHost = snackbarHostState,
                bodyContent = {
                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.padding(16.dp).fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Card {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    TextField(
                                        value = text,
                                        label = { Text("Message") },
                                        placeholder = { Text("Type here") },
                                        onValueChange = { value ->
                                            text = value
                                        })
                                    Spacer(modifier = Modifier.padding(top = 16.dp))
                                    Row {
                                        Button(onClick = {
                                            resultData = text
                                            dataCallback?.onSetData(text)
                                            /*lifecycleScope.launch {
                                                snackbarHostState.value.showSnackbar(
                                                    message = "Hey look a snackbar",
                                                    actionLabel = "Hide",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }*/
                                        }) {
                                            Text("Save")
                                        }
                                        Spacer(modifier = Modifier.padding(start = 8.dp))
                                        Button(onClick = {
                                            text = ""
                                            dataCallback?.onSetData(text)
                                        }) {
                                            Text("Clear")
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //AppSnackbar(snackbarVisibleState, "Saved")
                    /*SnackbarHost(
                        modifier = Modifier.padding(16.dp),
                        hostState = snackbarHostState.value,
                        snackbar = {
                            Snackbar(
                                action = {
                                    TextButton(onClick = {
                                        snackbarHostState.value.currentSnackbarData?.dismiss()
                                    }) {
                                        Text(
                                            text = "Hide"
                                        )
                                    }
                                }
                            ) {
                                Text("hey look a snackbar")
                            }
                        }
                    )*/
                })
        }
    }

    fun AppSnackbar(message: String, view: View) {
        com.google.android.material.snackbar.Snackbar.make(
            view,
            message,
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).show()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Preview("Screen preview")
    @Composable
    fun DefaultPreview() {
        MyApp()
    }

    override fun onBackPressed() {
        resultData?.let {
            val resultIntent = Intent()
            resultIntent.putExtra("data", resultData)
            setResult(Activity.RESULT_OK, resultIntent)
        } ?: kotlin.run {
            setResult(Activity.RESULT_CANCELED)
        }
        dataCallback = null
        super.onBackPressed()
    }
}