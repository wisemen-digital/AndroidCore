package be.appwise.core.extensions.primitives


val Float.degreesToRadians: Float
    get(){
        return this * (Math.PI / 180.0).toFloat()
    }

val Float.topDegree: Float
    get(){
        return this - 90.0f
    }