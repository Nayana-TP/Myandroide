package com.example.myandroide

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myandroide.auth.AuthViewModel
import com.example.myandroide.utils.MainCoroutineRule
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.junit.Assert.*
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mockFirebaseAuth: FirebaseAuth

    @Mock
    private lateinit var mockFirebaseUser: FirebaseUser

    @Mock
    private lateinit var mockAuthResult: AuthResult

    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        authViewModel = AuthViewModel()
        val firebaseAuthField = AuthViewModel::class.java.getDeclaredField("auth")
        firebaseAuthField.isAccessible = true
        firebaseAuthField.set(authViewModel, mockFirebaseAuth)
    }

    @Test
    fun `test login success`() = runTest {
        val mockTask = Mockito.mock(Task::class.java) as Task<AuthResult>
        whenever(mockTask.isSuccessful).thenReturn(true)
        whenever(mockTask.addOnCompleteListener(any())).then { invocation ->
            val listener = invocation.getArgument<com.google.android.gms.tasks.OnCompleteListener<AuthResult>>(0)
            listener.onComplete(mockTask)
            null
        }
        whenever(mockFirebaseAuth.signInWithEmailAndPassword(any(), any())).thenReturn(mockTask)
        whenever(mockFirebaseAuth.currentUser).thenReturn(mockFirebaseUser)

        authViewModel.login("test@example.com", "password")

        assertNotNull(authViewModel.user.value)
    }

    @Test
    fun `test signup success`() = runTest {
        val mockTask = Mockito.mock(Task::class.java) as Task<AuthResult>
        whenever(mockTask.isSuccessful).thenReturn(true)
        whenever(mockTask.addOnCompleteListener(any())).then { invocation ->
            val listener = invocation.getArgument<com.google.android.gms.tasks.OnCompleteListener<AuthResult>>(0)
            listener.onComplete(mockTask)
            null
        }
        whenever(mockFirebaseAuth.createUserWithEmailAndPassword(any(), any())).thenReturn(mockTask)
        whenever(mockFirebaseAuth.currentUser).thenReturn(mockFirebaseUser)

        authViewModel.signUp("test@example.com", "password")

        assertNotNull(authViewModel.user.value)
    }

    @Test
    fun `test logout`() = runTest {
        authViewModel.logout()
        assertNull(authViewModel.user.value)
    }
}