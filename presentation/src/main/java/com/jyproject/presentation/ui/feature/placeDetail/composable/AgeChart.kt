package com.jyproject.presentation.ui.feature.placeDetail.composable


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailContract
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
import okhttp3.internal.toImmutableList
import java.text.DateFormatSymbols
import java.util.Locale

@Composable
fun AgeChart(state: PlaceDetailContract.State,) {
    val modelProducer: CartesianChartModelProducer = remember { CartesianChartModelProducer.build() }
    val ageGroups = listOf("10대", "20대", "30대", "40대", "50대")
    val bottomAxisValueFormatter = CartesianValueFormatter { x, _, _ ->
        ageGroups.getOrElse(x.toInt()) { "" }
    }
    val ageValue = state.placeInfo!!.ageRateList!! ?: emptyList()
    val a = listOf(0.1f, 0.2f, 0.3f, 0.4f)

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            while(isActive) {
                modelProducer.runTransaction {
                    columnSeries {
                        series(y = a)
                    }
                }
            }
        }
    }
    
    CartesianChartHost(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
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
                        AxisItemPlacer.Horizontal.default(spacing = 1, addExtremeLabelPadding = true)
                    }
                ),
        ),
        modelProducer = modelProducer,

    )
}