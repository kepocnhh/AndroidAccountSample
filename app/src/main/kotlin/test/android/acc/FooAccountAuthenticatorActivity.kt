package test.android.acc

import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

internal abstract class FooAccountAuthenticatorActivity : AppCompatActivity() {
    private var mAccountAuthenticatorResponse: AccountAuthenticatorResponse? = null
    private var mResultBundle: Bundle? = null

    fun setAccountAuthenticatorResult(result: Bundle) {
        mResultBundle = result
    }

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        mAccountAuthenticatorResponse = intent.getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE)
        mAccountAuthenticatorResponse?.onRequestContinued()
    }

    override fun finish() {
        mAccountAuthenticatorResponse?.also {
            val bundle = mResultBundle
            if (bundle == null) {
                it.onError(AccountManager.ERROR_CODE_CANCELED, "canceled")
            } else {
                it.onResult(bundle)
            }
            mAccountAuthenticatorResponse = null
        }
        super.finish()
    }
}
