package com.jyproject.presentation.ui.feature.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    navController: NavController
) {
    val currentMonth = remember { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }
    val pageState = rememberPagerState(initialPage = 0, pageCount = { Int.MAX_VALUE })

    LaunchedEffect(pageState.currentPage) {
        currentMonth.value = LocalDate.now().withDayOfMonth(1).plusMonths(pageState.currentPage.toLong())
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pageState
        ) { page->
            val targetMonth = currentMonth.value.plusMonths(page.toLong())
            CalendarMonthHeader(date = targetMonth)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarMonthHeader(date: LocalDate) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${date.year}년 ${date.monthValue}월",
        )
        Spacer(modifier = Modifier.height(8.dp))

    }
}