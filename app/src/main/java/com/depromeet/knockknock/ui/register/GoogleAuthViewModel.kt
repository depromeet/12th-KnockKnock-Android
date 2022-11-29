package com.depromeet.knockknock.ui.register

import android.app.Application
import androidx.lifecycle.*
import com.depromeet.knockknock.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GoogleAuthViewModel(application: Application, private val listener: OnSignInStartedListener): AndroidViewModel(application) {
    private var auth: FirebaseAuth = Firebase.auth
    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(application.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val googleSignInClient = GoogleSignIn.getClient(application, gso)

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                _currentUser.value = auth.currentUser
            } else {
                _currentUser.value = null
            }
        }
    }

    fun signIn() {
        listener.onSignInStarted(googleSignInClient)
    }
    fun signOut(){
        auth.signOut()
    }
}

@Suppress("UNCHECKED_CAST")
class GoogleAuthViewModelFactory(
    private val application: Application,
    private val listener: OnSignInStartedListener
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoogleAuthViewModel::class.java)) {

            return GoogleAuthViewModel(application, listener) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

interface OnSignInStartedListener {
    fun onSignInStarted(client: GoogleSignInClient?)
}