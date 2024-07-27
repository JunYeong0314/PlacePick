package com.jyproject.presentation.ui.feature.home.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.home.HomeContract
import org.junit.Rule
import org.junit.Test

class PlaceCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPlaceCardClick() {
        // Given
        val place = "Test Place"
        var clicked = false
        val painterId = R.drawable.ic_app

        composeTestRule.setContent {
            PlaceCard(
                place = place,
                painterId = painterId,
                onEventSend = {event ->
                    if (event is HomeContract.Event.NavigationToPlaceDetail && event.place == place) {
                        clicked = true
                    }
                }
            )
        }

        // When
        composeTestRule.onNodeWithText(place).performClick()

        // Then
        assert(clicked)
    }
}