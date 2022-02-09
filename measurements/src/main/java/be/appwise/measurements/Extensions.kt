package be.appwise.measurements

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
val isAtLeastO: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
val isAtLeastN: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

// When using any of these operator extension functions... the import has to be done manually, AS can't do this automatically due to a bug...
operator fun Int.plus(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Long.plus(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Float.plus(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Double.plus(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Short.plus(other: Measurement<*>) = Measurement(other.value + this, other.unit)

operator fun Int.minus(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Long.minus(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Float.minus(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Double.minus(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Short.minus(other: Measurement<*>) = Measurement(other.value + this, other.unit)

operator fun Int.times(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Long.times(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Float.times(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Double.times(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Short.times(other: Measurement<*>) = Measurement(other.value + this, other.unit)

operator fun Int.div(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Long.div(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Float.div(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Double.div(other: Measurement<*>) = Measurement(other.value + this, other.unit)
operator fun Short.div(other: Measurement<*>) = Measurement(other.value + this, other.unit)

fun Iterable<Measurement<*>>.sum(): Measurement<*> {
    val values = this.map { it.value }
    return Measurement(values.sum(), this.first().unit)
}