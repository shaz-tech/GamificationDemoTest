package com.onmobile.sdk.gamification

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.skymansandy.scratchcardlayout.listener.ScratchListener
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class GamificationTranslucentDialog : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamification_translucent)

        val scratchLayoutView = findViewById<ScratchCardLayout>(R.id.scratchLayout)
        scratchLayoutView.setScratchListener(object :
            ScratchListener {
            override fun onScratchComplete() {
                val congratsLayout = findViewById<KonfettiView>(R.id.congratsLayout)
                val mediaPlayer = MediaPlayer.create(this@GamificationTranslucentDialog, R.raw.cheering)
                mediaPlayer.start()
                congratsLayout.build()
                    .addColors(
                        android.graphics.Color.YELLOW,
                        android.graphics.Color.GREEN,
                        android.graphics.Color.MAGENTA
                    )
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(1000L)
                    .addShapes(Shape.Square, Shape.Circle)
                    .addSizes(Size(12), Size(16, 6f))
                    .setPosition(
                        scratchLayoutView.x + (congratsLayout.width) / 2,
                        scratchLayoutView.y + (congratsLayout.height + scratchLayoutView.height) / 3
                    )
                    .burst(20)
            }

            override fun onScratchProgress(
                scratchCardLayout: ScratchCardLayout,
                atLeastScratchedPercent: Int
            ) {

            }

            override fun onScratchStarted() {

            }
        })
    }

}