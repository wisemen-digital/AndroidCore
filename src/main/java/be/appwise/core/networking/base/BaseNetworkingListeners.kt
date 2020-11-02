package be.appwise.core.networking.base

import android.app.PendingIntent
import android.content.Intent
import be.appwise.core.core.CoreApp
import be.appwise.core.networking.Networking
import com.orhanobut.hawk.Hawk
import io.realm.Realm

interface BaseNetworkingListeners {
    companion object {
        val DEFAULT = object :
            BaseNetworkingListeners {}
    }

    /**
     * This logout function can be used to cleanup any resources the app is using.
     * i.e. remove all entries from Hawk, delete all data from Realm, ...
     *
     * After that it will call a Deeplink in the app to return to the 'Starting Activity' without any backstack.
     * For this to work, don't forget to add the intent filter to your 'Starting Activity' to make the deep
     *
     * ```
     *    <intent-filter>
     *        <action android:name="${applicationId}.logout" />
     *        <category android:name="android.intent.category.DEFAULT" />
     *    </intent-filter>
     * ```
     */
    fun logout() {
        // Using packageName for this so the application can differentiate between a develop, staging or production build and won't ask the user which to use
        val errorActivity = Intent("${Networking.getPackageName()}.logout")
        errorActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(CoreApp.getContext(), 22, errorActivity, 0)

//        Networking.getUnProtectedClient().dispatcher().cancelAll()

        try {
            if (Hawk.isBuilt()) {
                Hawk.deleteAll()
            }

            Realm.getDefaultInstance().executeTransaction { it.deleteAll() }
            pendingIntent.send()

            //OneSignal.deleteTag(Constants.ONESIGNAL_USER_ID)
        } catch (e: PendingIntent.CanceledException) {
            e.printStackTrace()
        }
    }
}