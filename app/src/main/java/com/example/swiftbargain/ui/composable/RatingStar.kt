package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipPath
import com.example.swiftbargain.ui.theme.colors
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun RatingStar(
    modifier: Modifier = Modifier,
    isOutLineVisible: Boolean = true,
    fillFraction: Float,
    spikes: Int = 5,
    outerRadiusFraction: Float = 0.5f,
    innerRadiusFraction: Float = 0.25f,
    outlineColor: Color = MaterialTheme.colors.textLight,
    color: Color = MaterialTheme.colors.yellow,
    isOnClickOn: Boolean = false,
    onClick: () -> Unit = {}
) {
    Canvas(
        modifier = if (isOnClickOn) modifier.clickable(
            enabled = true,
            onClick = onClick
        ) else modifier
    ) {
        val cx = size.width / 2
        val cy = size.height / 2
        val outerRadius = min(size.width, size.height) * outerRadiusFraction
        val innerRadius = min(size.width, size.height) * innerRadiusFraction

        val path = Path().apply { drawStar(cx, cy, spikes, outerRadius, innerRadius) }

        drawPath(path, outlineColor, style = Fill)

        if (isOutLineVisible) {
            val clipPath = Path().apply {
                addRect(Rect(0f, 0f, size.width * fillFraction, size.height))
            }

            clipPath(clipPath, ClipOp.Intersect) {
                drawPath(path, color, style = Fill)
            }
        }
    }
}


fun Path.drawStar(cx: Float, cy: Float, spikes: Int, outerRadius: Float, innerRadius: Float) {
    val angleStep = PI / spikes
    var currentAngle = -PI / 2

    for (i in 0 until spikes * 2) {
        val radius = if (i % 2 == 0) outerRadius else innerRadius
        val x = cx + (cos(currentAngle) * radius).toFloat()
        val y = cy + (sin(currentAngle) * radius).toFloat()
        if (i == 0) {
            moveTo(x, y)
        } else {
            lineTo(x, y)
        }
        currentAngle += angleStep
    }

    close()
}
