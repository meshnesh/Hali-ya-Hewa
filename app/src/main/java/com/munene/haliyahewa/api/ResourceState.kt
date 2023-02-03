package com.munene.haliyahewa.api

import androidx.annotation.IntDef
import com.munene.haliyahewa.api.ResourceState.Companion.STATE_FAILED
import com.munene.haliyahewa.api.ResourceState.Companion.STATE_LOADING
import com.munene.haliyahewa.api.ResourceState.Companion.STATE_SUCCESS

@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@IntDef(
    STATE_LOADING,
    STATE_SUCCESS,
    STATE_FAILED
)
annotation class ResourceState {
    companion object {
        const val STATE_LOADING = 1
        const val STATE_SUCCESS = 2
        const val STATE_FAILED = 3
    }
}
