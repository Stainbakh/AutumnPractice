package com.example.myapp
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    private val signInLauncher = registerForActivityResult( // создание объекта авторизации
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res) // запуск экрана
    }

    private lateinit var database: DatabaseReference //объект для записи в бд

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        database = Firebase.database.reference // инициализация бд
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()) // список регистраций

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()  // интент для экрана fb
        signInLauncher.launch(signInIntent)

    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) { // проверка результата
            Log.d("testLogs", "RegistrationActivity registration success ${response?.email}")
            val authUser = FirebaseAuth.getInstance().currentUser  // объект текущего пользователя fb
            authUser?.let{
                val email = it.email.toString()
                val uid = it.uid
                val firebaseUser = User(email, uid) // новый объект юзер с параметрами uid email
                database.child("users").setValue(firebaseUser) //сохраняем в fb
                super.onBackPressed()
            }
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}