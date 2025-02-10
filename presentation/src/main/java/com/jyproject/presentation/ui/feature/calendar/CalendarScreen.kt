package com.jyproject.presentation.ui.feature.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.defines.BaseComposable
import com.jyproject.presentation.ui.defines.FontDefines
import com.jyproject.presentation.ui.theme.PlacePickTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    navController: NavController
) {
    val currentDateWithDayOfMonthFirst = LocalDate.now().withDayOfMonth(1)
    val currentMonth = rememberSaveable { mutableStateOf(currentDateWithDayOfMonthFirst) }
    val pageCount = 2400
    val initialPage = pageCount / 2
    val pageState = rememberPagerState(initialPage = initialPage, pageCount = { pageCount })
    val selectedDays = rememberSaveable {
        mutableStateOf(LocalDate.now())
    }

    LaunchedEffect(pageState) {
        snapshotFlow { pageState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                val offset = page - initialPage
                currentMonth.value = currentDateWithDayOfMonthFirst.plusMonths(offset.toLong())
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            state = pageState
        ) { page ->
            val offset = page - initialPage
            val targetMonth = currentDateWithDayOfMonthFirst.plusMonths(offset.toLong())
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                CalendarMonthHeader(date = targetMonth)
                Spacer(modifier = Modifier.height(8.dp))
                CalendarDayOfWeekHeader()
                CalendarDateGrid(
                    date = targetMonth,
                    selectedDay = selectedDays.value,
                    onSelectDay = {
                        selectedDays.value = it
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarMonthHeader(date: LocalDate) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "${date.year}년 ${date.monthValue}월",
            style = FontDefines.body18BlackBold
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDayOfWeekHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")
        daysOfWeek.forEach { dayOfWeek->
            Text(text = dayOfWeek, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, style = FontDefines.body14BlackBlack())
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDateGrid(date: LocalDate, selectedDay: LocalDate, onSelectDay: (LocalDate) -> Unit) {
    val firstDayOfMonth = date.withDayOfMonth(1)
    val lastDayOfMonth = date.lengthOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val daysInMonth = (1..lastDayOfMonth).toList()
    val daysWithBlanks = MutableList(firstDayOfWeek) { 0 } + daysInMonth

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(daysWithBlanks.size) { index ->
            val day = daysWithBlanks[index]
            Box(
                modifier = Modifier.aspectRatio(1f).padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                if(day != 0) {
                    BaseComposable.RoundedBox(
                        color = colorResource(R.color.light_gray_weak1)
                    ) {
                        Text(
                            modifier = Modifier.clickable(
                                onClick = { onSelectDay(date.withDayOfMonth(day)) }
                            ),
                            text = day.toString(),
                            color = if(date.withDayOfMonth(day) == selectedDay) colorResource(R.color.app_base) else colorResource(R.color.light_gray_hard2),
                            style = FontDefines.body14BlackHardGray()
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun Preview() {
    PlacePickTheme {
        CalendarScreen(navController = NavController(LocalContext.current))
    }
}