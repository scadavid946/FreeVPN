package com.info.uitc.freeandr.ui.auth

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.info.uitc.freeandr.R
import com.info.uitc.freeandr.api.FreeAndrApi
import com.info.uitc.freeandr.api.model.EchoRequest
import com.info.uitc.freeandr.api.model.EchoResponse
import com.info.uitc.freeandr.api.model.ValidateRequest
import com.info.uitc.freeandr.api.model.ValidateResponse
import com.info.uitc.freeandr.ui.connect.ConnectActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // progress dialog
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Login...")

        // test
        edtLoginEmail.setText("saddukar@gmail.com")
        edtLoginPassword.setText("fkrhjd2LWdXT7be")

        // button
        btnLogin.setOnClickListener {
            login()
        }

        lblLinkSignup.setOnClickListener {
            gotoSignup()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun login() {
        // validate
        if (!validate())
            return

        // call api
        val email = edtLoginEmail.text.toString()
        val password = edtLoginPassword.text.toString()
        callLoginApi(email, password)
    }

    private fun validate(): Boolean {
        var isValid = true

        var email = edtLoginEmail.text.toString()
        var password = edtLoginPassword.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtLoginEmail.setError("Enter a valid email address")
            isValid = false
        } else {
            edtLoginEmail.setError(null)
        }

        if (password.isEmpty() || password.length < 3) {
            edtLoginPassword.setError("Password should be at least 3 length")
            isValid = false
        } else {
            edtLoginPassword.setError(null)
        }

        return isValid
    }

    private fun callLoginApi(email: String, password: String) {
        mProgressDialog.show()
        btnLogin.isEnabled = false
        lblLinkSignup.isEnabled = false

        val validateRequest = ValidateRequest("validate", email, password)
        compositeDisposable.add(FreeAndrApi.instance.validate(validateRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : ResourceSubscriber<ValidateResponse>() {
                override fun onComplete() {
                    mProgressDialog.dismiss()
                    btnLogin.isEnabled = true
                    lblLinkSignup.isEnabled = true
                }

                override fun onNext(t: ValidateResponse?) {
                    if (t?.success == "success") {
                        // go to the ConnectActivity
                        val intent = Intent(this@LoginActivity, ConnectActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid email or password",
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onError(t: Throwable?) {
                    mProgressDialog.dismiss()
                    btnLogin.isEnabled = true
                    lblLinkSignup.isEnabled = true

                    Toast.makeText(this@LoginActivity, t?.localizedMessage,
                            Toast.LENGTH_LONG).show()
                }
            })
        )
    }

    private fun echo() {
        mProgressDialog.show()

        val echoRequest = EchoRequest("echo")
        compositeDisposable.add(FreeAndrApi.instance.echo(echoRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : ResourceSubscriber<EchoResponse>() {
                override fun onComplete() {
                    mProgressDialog.dismiss()
                }

                override fun onNext(t: EchoResponse?) {
                    Toast.makeText(this@LoginActivity, t?.echo,
                        Toast.LENGTH_LONG).show()
                }

                override fun onError(t: Throwable?) {
                    mProgressDialog.dismiss()
                    Toast.makeText(this@LoginActivity, t?.localizedMessage,
                        Toast.LENGTH_LONG).show()
                }
            })
        )
    }

    private fun gotoSignup() {
        // go to the Signup Activity
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
//        finish()
        overridePendingTransition(
            R.anim.push_left_in,
            R.anim.push_left_out
        )
    }
}
