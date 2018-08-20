package pl.applover.android.mvvmtest.util.language

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import java.util.*


/**
 * Created by Janusz Hain on 2018-03-07.
 */
class LanguageContextWrapper(base: Context) : ContextWrapper(base) {
    companion object {

        @TargetApi(Build.VERSION_CODES.N)
        fun wrap(context: Context, newLocale: Locale): ContextWrapper {
            var context = context
            val res = context.resources
            val configuration = res.configuration

            if (Build.VERSION.SDK_INT >= 24) {
                configuration.setLocale(newLocale)

                val localeList = LocaleList(newLocale)
                Locale.setDefault(newLocale)
                LocaleList.setDefault(localeList)
                configuration.locales = localeList
                configuration.setLocale(newLocale)
                context = context.createConfigurationContext(configuration)

            }

            return ContextWrapper(context)
        }

        /**
         * Not working, any solution found on the internet doesn't work. this is for api less than 24

        private fun updateResourcesLegacy(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)

        val resources = context.getResources()

        val configuration = resources.getConfiguration()
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        configuration.setLayoutDirection(locale)
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics())

        return context
        }
         */
    }


}