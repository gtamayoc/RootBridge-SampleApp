package com.rootbridge.sampleapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rootbridge.sampleapp.data.MemoryStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * MainViewModel
 *
 * Reads and writes values through MemoryStore (DirectByteBuffer).
 * A polling loop runs every 100ms to detect external memory modifications
 * and push them to the UI via StateFlow.
 */
class MainViewModel : ViewModel() {

    // ── UI state (driven by polling loop) ────────────────────────────────

    private val _coins = MutableStateFlow(MemoryStore.coins)
    val coins: StateFlow<Int> = _coins.asStateFlow()

    private val _nativeAddress = MutableStateFlow(MemoryStore.nativeAddressHex())
    val nativeAddress: StateFlow<String> = _nativeAddress.asStateFlow()

    init {
        // Poll MemoryStore every 100ms.
        // If any external tool writes to the native address, the next
        // poll cycle will pick up the change and update the screen.
        viewModelScope.launch {
            while (true) {
                _coins.value = MemoryStore.coins
                delay(100L)
            }
        }
    }

    // ── User actions ─────────────────────────────────────────────────────

    fun addOne() {
        MemoryStore.coins += 1
        _coins.value = MemoryStore.coins
    }

    fun addTen() {
        MemoryStore.coins += 10
        _coins.value = MemoryStore.coins
    }

    fun reset() {
        MemoryStore.coins       = 0
        MemoryStore.shadowValue = 0
        MemoryStore.score       = 0
        _coins.value = 0
    }
}
