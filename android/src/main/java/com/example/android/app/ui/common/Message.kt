package com.example.android.app.ui.common

import android.content.Context
import androidx.annotation.StringRes

sealed interface Message {
    fun getString(context: Context): String

    companion object {
        operator fun invoke(
            @StringRes resId: Int,
            vararg formatArgs: Any
        ): Message {
            return ResourceMessage(resId, formatArgs)
        }

        operator fun invoke(message: String): Message {
            return StringMessage(message)
        }
    }
}

data class ResourceMessage(
    @StringRes private val resId: Int,
    private val formatArgs: Array<out Any>
) : Message {
    override fun getString(context: Context): String {
        return if (formatArgs.isEmpty()) {
            context.getString(resId)
        } else {
            context.getString(resId, *formatArgs)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ResourceMessage

        if (resId != other.resId) return false
        if (!formatArgs.contentEquals(other.formatArgs)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = resId
        result = 31 * result + formatArgs.contentHashCode()
        return result
    }
}

@JvmInline
value class StringMessage(val value: String) : Message {
    override fun getString(context: Context) = value
}