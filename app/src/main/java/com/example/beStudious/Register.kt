package com.example.beStudious

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.beStudious.feed.UserHelperClass
import androidx.appcompat.app.AppCompatActivity
import com.example.beStudious.feed.feedMain
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth


   lateinit var autoCompleteTextView: AutoCompleteTextView

   lateinit var rootNode: FirebaseDatabase
   lateinit var reference: DatabaseReference

    public override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        val editTextEmail: TextInputEditText = findViewById(R.id.email)
        val editTextPassword: TextInputEditText = findViewById(R.id.password)
        val buttonReg: Button = findViewById(R.id.btn_register)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val textView: TextView = findViewById(R.id.loginNow)


        textView.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
        buttonReg.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()

            if(TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gets the database instance
            rootNode = FirebaseDatabase.getInstance()

            // Gets the reference from where users is located, to add users under this path.
            reference = rootNode.getReference("users")

            // Creates object for helper class to add the email/password together
            val helperClass = UserHelperClass(email, password)

            // Allows the addition of individual users to the database.
            reference.child(password).setValue(helperClass)

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                   progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        val user = mAuth.currentUser
                        Toast.makeText(baseContext, "Account created.",
                            Toast.LENGTH_SHORT).show()

                        val intent = Intent(applicationContext, feedMain::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }

            }


        }


    }
