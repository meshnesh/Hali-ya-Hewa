package com.munene.haliyahewa.util.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Adds the given observer to the observers list within the lifespan of the given
 * owner. The events are dispatched on the main thread. If LiveData already has data
 * set, it will be delivered to the observer.
 *
 * @param liveData The mutableLiveData to observe.
 * @param observer The observer that will receive the events.
 * @see MutableLiveData.observe
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this) { it?.let { t -> observer(t) } }
}

/**
 * This function helps to observe a [LiveData] once
 */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

/**
 * This functions helps in transforming a [MutableLiveData] of type [T]
 * to a [LiveData] of type [T]
 */
fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
