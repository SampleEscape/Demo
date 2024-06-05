package com.escape.demo.utils.logger

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

private const val loggerTag = "ComponentLogger"

private inline val <T : Any> T.javaClassName: String
    get() = javaClass.name

private fun Fragment.printLifecycle(lifecycleScope: String) {
    Log.d(loggerTag, "[Fragment] $lifecycleScope - $javaClassName(${hashCode()})")
}

private fun Activity.printLifecycle(lifecycleScope: String) {
    Log.d(loggerTag, "[Activity] $lifecycleScope - $javaClassName(${hashCode()})")
}

class ComponentLogger {
    fun initialize(application: Application) {
        application.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    activity.printLifecycle("onCreate")
                    handleActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {
                    activity.printLifecycle("onStart")
                }

                override fun onActivityResumed(activity: Activity) {
                    activity.printLifecycle("onResume")
                }

                override fun onActivityPaused(activity: Activity) {
                    activity.printLifecycle("onPause")
                }

                override fun onActivityStopped(activity: Activity) {
                    activity.printLifecycle("onStop")
                }

                override fun onActivitySaveInstanceState(
                    activity: Activity,
                    savedInstanceState: Bundle
                ) {
                    activity.printLifecycle("onSaveInstance")
                }

                override fun onActivityDestroyed(activity: Activity) {
                    activity.printLifecycle("onDestroy")
                }
            }
        )
    }

    private fun handleActivity(activity: Activity) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentAttached(
                        fm: FragmentManager,
                        f: Fragment,
                        context: Context
                    ) {
                        super.onFragmentAttached(fm, f, context)
                        f.printLifecycle("onAttach")
                    }

                    override fun onFragmentCreated(
                        fm: FragmentManager,
                        f: Fragment,
                        savedInstanceState: Bundle?
                    ) {
                        super.onFragmentCreated(fm, f, savedInstanceState)
                        f.printLifecycle("onCreate")
                    }

                    override fun onFragmentViewCreated(
                        fm: FragmentManager,
                        f: Fragment,
                        v: View,
                        savedInstanceState: Bundle?
                    ) {
                        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                        f.printLifecycle("onViewCreated")
                    }

                    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                        super.onFragmentStarted(fm, f)
                        f.printLifecycle("onStart")
                    }

                    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                        super.onFragmentResumed(fm, f)
                        f.printLifecycle("onResume")
                    }

                    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
                        super.onFragmentPaused(fm, f)
                        f.printLifecycle("onPause")
                    }

                    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
                        super.onFragmentStopped(fm, f)
                        f.printLifecycle("onStop")
                    }

                    override fun onFragmentSaveInstanceState(
                        fm: FragmentManager,
                        f: Fragment,
                        outState: Bundle
                    ) {
                        super.onFragmentSaveInstanceState(fm, f, outState)
                        f.printLifecycle("onSaveInstance")
                    }

                    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                        super.onFragmentViewDestroyed(fm, f)
                        f.printLifecycle("onViewDestroy")
                    }

                    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                        super.onFragmentDestroyed(fm, f)
                        f.printLifecycle("onDestroy")
                    }

                    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
                        super.onFragmentDetached(fm, f)
                        f.printLifecycle("onDetach")
                    }
                },
                true
            )
        }
    }
}