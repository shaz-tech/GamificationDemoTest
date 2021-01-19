package com.onmobile.sdk.gamification

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import dev.skymansandy.scratchcardlayout.listener.ScratchListener
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


internal class GamificationTranslucentPage : AppCompatActivity() {
    private var resultData: String? = null

    companion object {
        var dataCallback: Callback? = null
            set(value) {
                field = value
            }
    }

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setTheme(R.style.Theme_Translucent)
        //theme.applyStyle(R.style.Theme_Translucent, true)
        setContent {
            MyApp()
        }
        /*val window: Window = this.window
        window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))*/
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val w = window // in Activity's onCreate() for instance
            w.decorView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            w.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            w.attributes.format = PixelFormat.TRANSLUCENT
            w.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )
            w.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }*/
    }

    @ExperimentalMaterialApi
    @Composable
    private fun MyApp() {
        var congratsLayout: KonfettiView? = null
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = {
                    //onBackPressed()
                }, indication = null),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                shape = RoundedCornerShape(8.dp)
                //backgroundColor = Color(0xffffb300)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        imageResource(id = R.drawable.pattern),
                        modifier = Modifier
                            .width(300.dp)
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Scratch to win",
                            style = TextStyle(
                                color = Color(android.graphics.Color.BLACK),
                                fontWeight = FontWeight.Bold,
                                shadow = Shadow(
                                    color = Color(android.graphics.Color.WHITE),
                                    offset = Offset(1),
                                    blurRadius = 1.0F
                                ),
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ScratchCardLayout(this@GamificationTranslucentPage)
                        AndroidView(
                            viewBlock = ::ScratchCardLayout,
                            modifier = Modifier
                                .height(100.dp)
                                .width(200.dp)
                        ) {
                            //it.setCardBackgroundColor(android.graphics.Color.MAGENTA)
                            it.setScratchDrawable(
                                ContextCompat.getDrawable(
                                    this@GamificationTranslucentPage,
                                    R.drawable.scratch
                                )
                            )
                            it.setRevealFullAtPercent(40)
                            //it.setScratchEnabled(false)
                            //it.cardElevation = 8.0F
                            //it.setCardBackgroundColor(android.graphics.Color.BLACK)
                            it.setContent {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        imageResource(id = R.drawable.trophy),
                                        modifier = Modifier
                                            .size(32.dp, 32.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "You won",
                                            style = TextStyle(
                                                color = Color(android.graphics.Color.GRAY),
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "$10",
                                            style = TextStyle(
                                                color = Color(android.graphics.Color.GRAY),
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                            }
                            it.setScratchListener(object : ScratchListener {
                                override fun onScratchComplete() {
                                    println("Scratch: onScratchComplete")
                                    congratsLayout?.let { konfettiView ->
                                        konfettiView.visibility = View.VISIBLE
                                        konfettiView.build()
                                            .addColors(
                                                android.graphics.Color.YELLOW,
                                                android.graphics.Color.GREEN,
                                                android.graphics.Color.MAGENTA
                                            )
                                            .setDirection(0.0, 359.0)
                                            .setSpeed(1f, 5f)
                                            .setFadeOutEnabled(true)
                                            .setTimeToLive(2000L)
                                            .addShapes(Shape.Square, Shape.Circle)
                                            .addSizes(Size(12))
                                            .setPosition(-50f, konfettiView.width + 50f, -50f, -50f)
                                            .streamFor(300, 5000L)
                                    }
                                }

                                override fun onScratchProgress(
                                    scratchCardLayout: ScratchCardLayout,
                                    atLeastScratchedPercent: Int
                                ) {
                                    println("Scratch: $atLeastScratchedPercent")
                                }

                                override fun onScratchStarted() {
                                    println("Scratch: onScratchStarted")
                                }

                            })
                        }
                    }
                    KonfettiView(this@GamificationTranslucentPage)
                    AndroidView(
                        viewBlock = ::KonfettiView,
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                    ) {
                        it.visibility = View.GONE
                        congratsLayout = it
                    }
                }
            }
        }
    }

    fun AppSnackbar(message: String, view: View) {
        com.google.android.material.snackbar.Snackbar.make(
            view,
            message,
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).show()
    }

    /*@OptIn(ExperimentalMaterialApi::class)
    @Preview("Screen preview")
    @Composable
    fun DefaultPreview() {
        MyApp()
    }*/

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