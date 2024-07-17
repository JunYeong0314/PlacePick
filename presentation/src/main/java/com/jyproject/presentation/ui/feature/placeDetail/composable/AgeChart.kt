package com.jyproject.presentation.ui.feature.placeDetail.composable


import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.text.DateFormatSymbols
import java.util.Locale

private val monthNames = DateFormatSymbols.getInstance(Locale.US).shortMonths
private val bottomAxisValueFormatter = CartesianValueFormatter { x, _, _ ->
    "${monthNames[x.toInt() % 12]} ’${20 + x.toInt() / 12}"
}
@Composable
fun AgeChart() {
    val modelProducer: CartesianChartModelProducer = remember { CartesianChartModelProducer.build() }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            while(isActive) {
                modelProducer.runTransaction {
                    columnSeries {
                        series(1, 8, 3, 7)
                        series(y = listOf(6, 1, 9, 3))
                        series(x = listOf(1, 2, 3, 4), y = listOf(2, 5, 3, 4))
                    }
                }
            }
        }
    }
    
    CartesianChartHost(
        modifier = Modifier.width(300.dp),
        chart = rememberCartesianChart(
            rememberColumnCartesianLayer(
                ColumnCartesianLayer.ColumnProvider.series(
                    rememberLineComponent(
                        color = Color(0xffff5500),
                        thickness = 16.dp,
                        shape = remember { Shape.rounded(allPercent = 40) },
                    )
                )
            ),
            startAxis = rememberStartAxis(),
            bottomAxis =
                rememberBottomAxis(
                    valueFormatter = bottomAxisValueFormatter,
                    itemPlacer = remember {
                        AxisItemPlacer.Horizontal.default(spacing = 3, addExtremeLabelPadding = true)
                    }
                ),
        ),
        modelProducer = modelProducer,

    )
}