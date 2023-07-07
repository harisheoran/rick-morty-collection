package com.example.rickmorty

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.firebase.crashlytics.FirebaseCrashlytics

class BaseFragment(@LayoutRes layoutRes: Int): Fragment(layoutRes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseCrashlytics.getInstance().log(
            this.javaClass.simpleName
        )
    }
}