package com.rootbridge.sampleapp.data

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * MemoryStore — Singleton that holds all target values in DIRECT (off-heap) memory.
 *
 * Why DirectByteBuffer?
 * ─────────────────────
 * - Allocated OUTSIDE the JVM heap using native malloc()
 * - Address is FIXED — the Garbage Collector will NEVER move it
 * - Visible in /proc/[pid]/maps as an anonymous mapping
 * - Can be read/written at the exact native address by any memory scanner
 * - No encryption, no obfuscation — raw bytes at a stable location
 *
 * Memory layout (each Int = 4 bytes, Little-Endian on ARM):
 * ┌──────────┬──────────┬─────────────────────────────────────────┐
 * │ Offset   │ Size     │ Variable                                │
 * ├──────────┼──────────┼─────────────────────────────────────────┤
 * │ +0x00    │ 4 bytes  │ coins       (visible on screen)         │
 * │ +0x04    │ 4 bytes  │ shadowValue (hidden, advanced scanning) │
 * │ +0x08    │ 4 bytes  │ score       (reserved for future use)   │
 * │ +0x0C… │ reserved │ (padding)                               │
 * └──────────┴──────────┴─────────────────────────────────────────┘
 */
object MemoryStore {

    // 64-byte direct buffer — off-heap, stable native address, GC-proof
    private val buffer: ByteBuffer =
        ByteBuffer.allocateDirect(64).order(ByteOrder.nativeOrder())

    // ── Accessors ────────────────────────────────────────────────────────

    var coins: Int
        get() = buffer.getInt(OFFSET_COINS)
        set(value) { buffer.putInt(OFFSET_COINS, value) }

    var shadowValue: Int
        get() = buffer.getInt(OFFSET_SHADOW)
        set(value) { buffer.putInt(OFFSET_SHADOW, value) }

    var score: Int
        get() = buffer.getInt(OFFSET_SCORE)
        set(value) { buffer.putInt(OFFSET_SCORE, value) }

    // ── Init defaults ────────────────────────────────────────────────────

    init {
        coins       = 500
        shadowValue = 12345
        score       = 0
    }

    // ── Native address (for display in the UI) ───────────────────────────

    /**
     * Returns the actual native (C malloc) address of this buffer.
     * Use this address in your memory scanner to jump directly to the data.
     */
    val nativeAddress: Long by lazy {
        try {
            // DirectByteBuffer stores the native pointer in the "address" field
            // of java.nio.Buffer (the parent class).
            val field = java.nio.Buffer::class.java.getDeclaredField("address")
            field.isAccessible = true
            field.getLong(buffer)
        } catch (e: Exception) {
            -1L
        }
    }

    fun nativeAddressHex(): String =
        if (nativeAddress > 0) "0x${nativeAddress.toString(16).uppercase()}"
        else "unavailable"

    // ── Offset constants (useful for external documentation) ─────────────

    const val OFFSET_COINS  = 0
    const val OFFSET_SHADOW = 4
    const val OFFSET_SCORE  = 8
}
