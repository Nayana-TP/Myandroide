package com.example.myandroide

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class AuthFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var auth: FirebaseAuth
    private val email = "testuser_${UUID.randomUUID()}@example.com"
    private const val password = "password123"

    @Before
    fun setup() {
        auth = FirebaseAuth.getInstance()
    }

    @Test
    fun testAuthFlow() {
        // 1. Sign Up
        composeTestRule.onNodeWithText("Don't have an account? Sign up").performClick()
        composeTestRule.onNodeWithText("Email").performTextInput(email)
        composeTestRule.onNodeWithText("Password").performTextInput(password)
        composeTestRule.onNodeWithText("Sign Up").performClick()

        // Wait for home screen and logout
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Logout").performClick()

        // 2. Login
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Email").performTextInput(email)
        composeTestRule.onNodeWithText("Password").performTextInput(password)
        composeTestRule.onNodeWithText("Login").performClick()

        // 3. Verify home screen and logout
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Welcome to Myandroide!").assertExists()
        composeTestRule.onNodeWithText("Logout").performClick()

        // 4. Verify back on login screen
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Login").assertExists()
    }

    @After
    fun cleanup() {
        auth.currentUser?.delete()
    }
}