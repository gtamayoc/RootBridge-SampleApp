package com.rootbridge.sampleapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * MainViewModel
 *
 * Acts as a simple in-memory data holder designed to be easily
 * detectable and modifiable by RAM analysis tools (e.g. RootBridge).
 *
 * Design rules:
 * - No Room, DataStore, or SharedPreferences
 * - Values stored as plain @Volatile primitives (Int = 4 bytes in JVM heap)
 * - A polling loop reads the raw value every 100ms and emits it to the UI
 * - This ensures that external memory writes are reflected on screen
 */
class MainViewModel : ViewModel() {

    // ─────────────────────────────────────────────────────────────────────
    // RAW VALUES — these are the targets for your RAM scanner.
    //
    //  @Volatile prevents JVM register caching so the value is always
    //  read from/written to actual heap memory.
    //
    //  Type: Int (32-bit signed integer, 4 bytes)
    //  Initial: 500
    // ─────────────────────────────────────────────────────────────────────

    @Volatile
    var coins: Int = 500           // Primary target — shown on screen

    @Volatile
    var shadowValue: Int = 12345   // Secondary target — hidden, for advanced tests

    // ─────────────────────────────────────────────────────────────────────
    // UI STATE — driven by the polling loop below
    // ─────────────────────────────────────────────────────────────────────

    private val _coinsDisplay = MutableStateFlow(coins)
    val coinsDisplay: StateFlow<Int> = _coinsDisplay.asStateFlow()

    init {
        // Polling loop: reads `coins` every 100ms.
        // When a RAM tool writes directly to the `coins` memory address,
        // the next poll cycle will catch the new value and update the screen.
        viewModelScope.launch {
            while (true) {
                _coinsDisplay.value = coins
                delay(100L)
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // USER ACTIONS
    // ─────────────────────────────────────────────────────────────────────

    fun addOne() {
        coins += 1
        _coinsDisplay.value = coins
    }

    fun addTen() {
        coins += 10
        _coinsDisplay.value = coins
    }

    fun reset() {
        coins = 0
        shadowValue = 0
        _coinsDisplay.value = 0
    }
}
