package com.jyproject.presentation.ui.feature.placeDetail.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.placeDetail.PlaceDetailContract
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.VicoZoomState
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

@Composable
fun AgeChart(state: PlaceDetailContract.State) {
    val modelProducer: CartesianChartModelProducer = remember { CartesianChartModelProducer.build() }
    val ageGroups = listOf("10대", "20대", "30대", "40대", "50대", "60대")
    val bottomAxisValueFormatter = CartesianValueFormatter { x, _, _ ->
        ageGroups.getOrElse(x.toInt()) { "" }
    }
    val ageValueList = remember { mutableStateListOf<Float>() }

    LaunchedEffect(state.placeInfo?.ageRateList) {
        ageValueList.clear()

        state.placeInfo?.ageRateList?.let { list->
            list.map { element->
                element?.let { ageValueList.add(it) }
            }
        }

        withContext(Dispatchers.Default) {
            while(isActive) {
                modelProducer.tryRunTransaction {
                    columnSeries {
                        series(y = ageValueList)
                    }
                }
            }
        }
    }
    
    if(ageValueList.isNotEmpty()){
        CartesianChartHost(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            chart = rememberCartesianChart(
                rememberColumnCartesianLayer(
                    ColumnCartesianLayer.ColumnProvider.series(
                        rememberLineComponent(
                            color = colorResource(id = R.color.app_base),
                            thickness = 16.dp,
                            shape = remember { Shape.rounded(allPercent = 40) },
                        )
                    ),
                    dataLabel = TextComponent.Builder().build(),
                    axisValueOverrider = AxisValueOverrider.fixed(maxY = ageValueList.max()+10)
                ),
                bottomAxis =
                rememberBottomAxis(
                    valueFormatter = bottomAxisValueFormatter,
                    itemPlacer = remember {
                        AxisItemPlacer.Horizontal.default(spacing = 1, addExtremeLabelPadding = true)
                    }
                ),
            ),
            modelProducer = modelProducer,
            zoomState = VicoZoomState(false, Zoom.Content, Zoom.Content, Zoom.Content)
        )
    }
}