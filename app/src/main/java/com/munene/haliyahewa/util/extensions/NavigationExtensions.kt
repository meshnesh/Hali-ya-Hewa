package com.munene.haliyahewa.util.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun <T> Fragment.setNavigationResult(key: String, value: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, value)
}

fun <T> Fragment.getNavigationResult(key: String, initialValue: T, onResult: (result: T) -> Unit) {
    findNavController()
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData(key, initialValue)
        ?.observe(viewLifecycleOwner) { it.let(onResult) }
}
