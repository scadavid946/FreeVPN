package com.info.uitc.freeandr.ui.auth

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.info.uitc.freeandr.R
import com.info.uitc.freeandr.api.FreeAndrApi
import com.info.uitc.freeandr.api.model.CreateRequest
import com.info.uitc.freeandr.api.model.CreateResponse
import com.info.uitc.freeandr.ui.connect.ConnectActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // progress dialog
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Registering...")

        // test
        edtSignupEmail.setText("ab@gmail.com")
        edtSignupPassword.setText("123")
        edtConfirmPassword.setText("123")

        // button
        btnSignup.setOnClickListener {
            signup()
        }

        lblLinkLogin.setOnClickListener {
            gotoLogin()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun signup() {
        // validate
        if (!validate())
            return

        // call api
        val email = edtSignupEmail.text.toString()
        val password = edtSignupPassword.text.toString()
        val confirmPassword = edtConfirmPassword.text.toString()
        val firstName = edtFirstName.text.toString()
        val lastName = edtLastName.text.toString()

        callSignupApi(email, password, firstName, lastName)
    }

    private fun validate(): Boolean {
        var isValid = true

        var email = edtSignupEmail.text.toString()
        var password = edtSignupPassword.text.toString()
        var confirmPassword = edtConfirmPassword.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtSignupEmail.setError("Enter a valid email address")
            isValid = false
        } else {
            edtSignupEmail.setError(null)
        }

        if (password.isEmpty() || password.length < 3) {
            edtSignupPassword.setError("Password should be at least 3 length")
            isValid = false
        } else {
            edtSignupPassword.setError(null)
        }

        if (!confirmPassword.equals(password)) {
            edtConfirmPassword.setError("Password does not match")
            isValid = false
        } else {
            edtConfirmPassword.setError(null)
        }

        return isValid
    }

    private fun callSignupApi(email: String, password: String, firstName: String, lastName: String) {
        mProgressDialog.show()
        btnSignup.isEnabled = false
        lblLinkLogin.isEnabled = false

        val createRequest = CreateRequest("create", email, password)
        compositeDisposable.add(FreeAndrApi.instance.create(createRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : ResourceSubscriber<CreateResponse>() {
                override fun onComplete() {
                    mProgressDialog.dismiss()
                    btnSignup.isEnabled = true
                    lblLinkLogin.isEnabled = true
                }

                override fun onNext(t: CreateResponse?) {
                    if (t?.success == "success") {
                        // go to the ConnectActivity
                        val intent = Intent(this@SignupActivity, ConnectActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignupActivity, "Error: " + t?.error,
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onError(t: Throwable?) {
                    mProgressDialog.dismiss()
                    btnSignup.isEnabled = true
                    lblLinkLogin.isEnabled = true

                    Toast.makeText(this@SignupActivity, t?.localizedMessage,
                        Toast.LENGTH_LONG).show()
                }
            })
        )
    }

    private fun gotoLogin() {
        // go to the Login Activity
        val intent = Intent(this, LoginActivity::class.java)

        onBackPressed()
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(
            R.anim.push_right_in,
            R.anim.push_right_out
        )
    }
}
