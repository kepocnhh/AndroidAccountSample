package test.android.acc

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout

internal class AddAccountActivity : FooAccountAuthenticatorActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = this
        val root = FrameLayout(context).also {
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            it.background = ColorDrawable(Color.RED)
        }
        LinearLayout(context).also { rows ->
            rows.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER,
            )
            rows.orientation = LinearLayout.VERTICAL
            val loginET = EditText(context).also {
                it.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
                it.hint = "login"
                rows.addView(it)
            }
            val passwordET = EditText(context).also {
                it.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
                it.hint = "password"
                rows.addView(it)
            }
            Button(context).also {
                it.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
                it.text = "ok"
                it.setOnClickListener {
                    addAccount(
                        name = loginET.text.toString(),
                        password = passwordET.text.toString(),
                    )
                }
                rows.addView(it)
            }
            root.addView(rows)
        }
        setContentView(root)
    }

    private fun addAccount(name: String, password: String) {
        if (name.isBlank()) {
            showToast("Name is empty!")
            return
        }
        if (password.isBlank()) {
            showToast("Password is empty!")
            return
        }
        val accountManager = AccountManager.get(this)
        val accountType = resources.getString(R.string.account_type)
        val exists = accountManager.accounts.any {
            it.type == accountType && it.name == name
        }
        if (exists) {
            showToast("Account \"$name\" exists!")
            return
        }
        val result = Bundle()
        val account = Account(name, accountType)
        val success = accountManager.addAccountExplicitly(account, password, null)
        if (success) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            val token = (account.name + account.type + password.hashCode()).hashCode().toString()
            result.putString(AccountManager.KEY_AUTHTOKEN, token)
            accountManager.setAuthToken(account, account.type, token)
        } else {
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "error!")
        }
        setAccountAuthenticatorResult(result)
        setResult(RESULT_OK)
        finish()
    }
}
