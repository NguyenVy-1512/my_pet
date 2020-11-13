package com.nhat.boiterplate.common

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.nhat.presentation.ViewModelFactory
import com.nhat.presentation.data.ResourceState
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment<T> : DaggerFragment(), HandleState<T> {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    abstract val layoutId: Int

    protected val disposable = CompositeDisposable()

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId, container, false)

    protected fun handleDataState(
        resourceState: ResourceState,
        data: T?,
        message: String?
    ) {
        when (resourceState) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError(message)
            ResourceState.DONE -> TODO()
        }
    }

    protected fun getSupportToolbar(): ActionBar? =
        activity?.let {
            if (it is AppCompatActivity) it.supportActionBar else null
        }

    protected fun hasPermissions(
        context: Context,
        permissions: Array<String>
    ): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }
}

interface HandleState<T> {
    fun setupScreenForLoadingState()
    fun setupScreenForSuccess(t: T?)
    fun setupScreenForError(message: String?)
}