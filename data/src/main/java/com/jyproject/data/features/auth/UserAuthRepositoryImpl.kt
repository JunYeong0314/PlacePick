package com.jyproject.data.features.auth

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jyproject.domain.features.auth.UserAuthRepository
import com.jyproject.domain.models.LoginState
import java.lang.Exception
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(): UserAuthRepository {
    private val db = Firebase.firestore
    override fun checkUser(userId: String, loginState: (LoginState) -> Unit) {

            try {
            db.collection("users")
                .document("${userId}.")
                .collection("userId")
                .get()
                .addOnSuccessListener { doc->
                    if(doc != null) loginState(LoginState.EXIST)
                    else loginState(LoginState.INIT)
                }
                .addOnFailureListener { loginState(LoginState.ERROR) }
        }catch (e: Exception){
            loginState(LoginState.ERROR)
            Log.d("ERROR", e.message.toString())
        }
    }
}