package com.example.travelhelper.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class LoginRegistrationViewModel : ViewModel() {

    companion object {
        const val LOG_TAG = "LoginRegistrationVm"
    }

    private val auth = Firebase.auth

    private val _loginSuccess = MutableLiveData<Unit>()
    val loginSuccess: LiveData<Unit>
        get() = _loginSuccess

    private val _loginFail = MutableLiveData<Unit>()
    val loginFail: LiveData<Unit>
        get() = _loginFail

    private val _registrationSuccess = MutableLiveData<Unit>()
    val registrationSuccess: LiveData<Unit>
        get() = _registrationSuccess

    private val _registrationFail = MutableLiveData<Unit>()
    val registrationFail: LiveData<Unit>
        get() = _registrationFail

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) _loginSuccess.value = Unit else _loginFail.value = Unit
        }
    }

    fun registerUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _registrationSuccess.value = Unit
                setupUsername(username = username)
            } else {
                _registrationFail.value = Unit
            }
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    private fun setupUsername(username: String) {
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = username
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) Log.d(LOG_TAG, "Username set.") else Log.d(
                    LOG_TAG,
                    "setupUsername ${task.exception?.message}"
                )
            }
    }
}