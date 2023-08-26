package test.android.acc

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

internal class FooAccountAuthenticator(private val context: Context) : AbstractAccountAuthenticator(context) {
    private val TAG = "[FAA|${hashCode()}]"

    override fun editProperties(
        response: AccountAuthenticatorResponse?,
        accountType: String?
    ): Bundle {
        TODO("Not yet implemented: editProperties")
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle? {
        when (accountType) {
            context.resources.getString(R.string.account_type) -> {
                val bundle = Bundle()
                val intent = Intent(context, AddAccountActivity::class.java)
                bundle.putParcelable(AccountManager.KEY_INTENT, intent)
                return bundle
            }
        }
        return null
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        options: Bundle?
    ): Bundle {
        TODO("Not yet implemented: confirmCredentials")
    }

    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        TODO("Not yet implemented: getAuthToken")
    }

    override fun getAuthTokenLabel(authTokenType: String?): String {
        TODO("Not yet implemented: getAuthTokenLabel")
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        TODO("Not yet implemented: updateCredentials")
    }

    override fun hasFeatures(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        features: Array<out String>?
    ): Bundle {
        TODO("Not yet implemented: hasFeatures")
    }
}
