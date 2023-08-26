package test.android.acc

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

internal class AuthenticatorService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        val context: Context = this
        return FooAccountAuthenticator(context).iBinder
    }
}
