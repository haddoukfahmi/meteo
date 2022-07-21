package com.example.meteo.presentation.viewmodels

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


public interface IBaseViewModel {

    public fun removeObservers(owner: LifecycleOwner, clear: Boolean? = true)
}

public abstract class BaseViewModel : ViewModel(), IBaseViewModel {

    protected val singleError: MutableLiveData<Throwable?> = MutableLiveData()
     val error: LiveData<Throwable?>
        get() = singleError

    protected val singleLoading: MutableLiveData<Boolean?> = MutableLiveData()
     val loading: LiveData<Boolean?>
        get() = singleLoading

    @CallSuper
    override fun removeObservers(owner: LifecycleOwner, clear: Boolean?) {
        singleError.removeObservers(owner)
        error.removeObservers(owner)

        singleLoading.removeObservers(owner)
        loading.removeObservers(owner)

        if (clear == true) {
            clear()
        }
    }

    @CallSuper
    protected open fun clear() {
        singleError.value = null
        singleLoading.value = null
    }
}