package pl.applover.enegivetest.util.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import pl.applover.android.mvvmtest.App
import pl.applover.android.mvvmtest.R
import java.util.concurrent.TimeUnit


/**
 * Created by Janusz Hain on 2018-01-08.
 */

fun showToast(rId: Int, isLong: Boolean = true, context: Context = App.instance) {
    Toast.makeText(context, rId, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun showToast(text: String, isLong: Boolean = true, context: Context = App.instance) {
    Toast.makeText(context, text, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

/**
 * Important for apps with changing language!!!
 * If you change language in the app (not using language defined by system) in the activity, then you have to use activity context,
 * otherwise this will not return correct language strings!
 */
fun getString(resId: Int, context: Context = App.instance) = context.getString(resId)

fun getColor(resId: Int, context: Context = App.instance) = ContextCompat.getColor(context, resId)


fun TimeUnit.delayed(delay: Long, closure: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(closure, this.toMillis(delay))
}

infix fun Int.with(x: Int) = this.or(x)

fun <T : Activity> Activity.goToActivity(className: Class<T>, saveActivityOnBackstack: Boolean = true, bundle: Bundle? = null) {
    val intent = Intent(this, className).apply {
        bundle?.let {
            putExtras(it)
        }
    }

    if (!saveActivityOnBackstack) {
        intent.flags = intent.flags with Intent.FLAG_ACTIVITY_NO_HISTORY
    }

    startActivity(intent)
}

fun <T : Activity> Activity.goToActivityAndClearActivityBackstack(className: Class<T>, saveActivityOnBackstack: Boolean = true, bundle: Bundle? = null) {
    val intent = Intent(this, className).apply {
        bundle?.let {
            putExtras(it)
        }
    }

    intent.flags = intent.flags with Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

    if (!saveActivityOnBackstack) {
        intent.flags = intent.flags with Intent.FLAG_ACTIVITY_NO_HISTORY
    }

    startActivity(intent)
}

fun AppCompatActivity.showFragment(fragment: Fragment, into: Int, push: Boolean = true, animIn: Int? = android.R.anim.fade_in, animOut: Int? = android.R.anim.fade_out, tag: String? = null) {
    if (push) {
        supportFragmentManager.beginTransaction()
                .addToBackStack(tag)
                .setCustomAnimations(
                        animIn ?: 0,
                        animOut ?: 0)
                .replace(into, fragment)
                .commit()
    } else {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                        animIn ?: 0,
                        animOut ?: 0)
                .replace(into, fragment)
                .commit()
    }
}

/**
 * Note that it shouldn't be called when fragment gets detached
 */
fun Fragment.replaceDialogFragment(dialogFragment: DialogFragment, tag: String? = null) {

    tag?.let {
        val currentDialogFragment = fragmentManager?.findFragmentByTag(tag) as DialogFragment?
        currentDialogFragment?.dismiss()
    }

    dialogFragment.show(fragmentManager, getString(R.string.key_single_dialog))
}

fun AppCompatActivity.replaceDialogFragment(dialogFragment: DialogFragment, tag: String? = null) {

    tag?.let {
        val currentDialogFragment = fragmentManager.findFragmentByTag(tag) as DialogFragment?
        currentDialogFragment?.dismiss()
    }

    dialogFragment.show(supportFragmentManager, getString(R.string.key_single_dialog))
}

fun Fragment.showNestedFragment(fragment: Fragment, into: Int, push: Boolean = true, animIn: Int? = android.R.anim.fade_in, animOut: Int? = android.R.anim.fade_out, tag: String? = null) {
    if (push) {
        childFragmentManager.beginTransaction()
                .addToBackStack(tag)
                .setCustomAnimations(
                        animIn ?: 0,
                        animOut ?: 0)
                .replace(into, fragment)
                .commitAllowingStateLoss()
    } else {
        childFragmentManager.beginTransaction()
                .setCustomAnimations(
                        animIn ?: 0,
                        animOut ?: 0)
                .replace(into, fragment)
                .commitAllowingStateLoss()
    }
}

fun AppCompatActivity.addWorkerFragmentIfNotExists(fragment: Fragment, tag: String) {
    if (supportFragmentManager.findFragmentByTag(tag) == null) {
        supportFragmentManager.beginTransaction()
                .add(fragment, tag)
                .commitNowAllowingStateLoss()
    }
}

fun AppCompatActivity.removeWorkerFragment(tag: String) {
    supportFragmentManager.findFragmentByTag(tag)?.let {
        supportFragmentManager.beginTransaction()
                .remove(it)
                .commitNowAllowingStateLoss()
    }
}

fun <T : Fragment> FragmentManager.onStackTop(closure: T.() -> Unit, onFailure: (() -> Unit)? = null) {
    (peekBackStack() as? T)?.closure() ?: onFailure?.invoke()
}

fun FragmentManager.peekBackStack() =
        if (backStackEntryCount > 0) {
            getBackStackEntryAt(backStackEntryCount - 1).name?.let {
                findFragmentByTag(it)
            }
        } else null


fun FragmentManager.clearBackstack() {
    if (backStackEntryCount > 0) {
        popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}

fun FragmentManager.popChildFragment(): Boolean {
    for (frag in this.fragments) {
        if (frag.isVisible) {
            val childFm = frag.childFragmentManager
            if (childFm.backStackEntryCount > 0) {
                childFm.popBackStack()
                return true
            }
        }
    }

    return false
}

fun FragmentManager.popFragment(): Boolean {

    if (this.backStackEntryCount > 0) {
        this.popBackStackImmediate()
        return true
    }

    return false
}

fun isEmailValid(target: CharSequence): Boolean {
    return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
}

fun Activity.reload() {
    finish()
    startActivity(intent)
}