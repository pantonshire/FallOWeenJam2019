package com.game.maths

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.game.maths.Approximatable
import kotlin.math.*

class Vec(val x: Float = 0f, val y: Float = 0f): Approximatable<Vec>, Comparable<Vec> {

    companion object {
        val X = Vec(1f, 0f)
        val Y = Vec(0f, 1f)
        val NULL = Vec(0f, 0f)
    }

    constructor(magnitude: Float, angle: Angle):
            this(magnitude * angle.cos(), magnitude * angle.sin())

    constructor(libGDXVector: Vector2):
            this(libGDXVector.x, libGDXVector.y)

    constructor(libGDXVector: Vector3):
            this(libGDXVector.x, libGDXVector.y)

    fun toVector2() =
            Vector2(this.x, this.y)

    fun toVector3(z: Float = 0f) =
            Vector3(this.x, this.y, z)

    fun magSq() =
            this.x * this.x + this.y * this.y

    fun mag() =
            sqrt(this.magSq())

    fun angle() =
            Angle(atan2(y, x))

    /**
     * Returns a normalised version of this vector.
     * @return the normal vector in the same direction as this vector
     */
    fun norm() =
            this / this.mag()

    infix fun distSq(other: Vec) =
            (this - other).magSq()

    /**
     * Finds the Euclidean distance between the two points, P' and P'', which any point P is translated to by
     * the two vectors. This is useful if you are using the vectors to store positional data.
     * @param other the vector to find the distance to
     * @return the Euclidean distance between the vectors
     */
    infix fun dist(other: Vec) =
            (this - other).mag()

    infix fun dot(other: Vec) =
            this.x * other.x + this.y * other.y

    infix fun proj(other: Vec) =
            other * ((this dot other) / other.magSq())

    /**
     * Finds the [angle][Angle] between the two vectors.
     * @param other the vector to find the angle to
     * @return the angle between the two vectors
     */
    infix fun theta(other: Vec) =
            Angle(acos((this dot other) / (this.mag() * other.mag())))

    fun snapTo(target: Vec, xSnap: Float, ySnap: Float = xSnap) = Vec(
            if (abs(target.x - this.x) < xSnap) { target.x } else { this.x },
            if (abs(target.y - this.y) < ySnap) { target.y } else { this.y }
    )

    fun lerp(target: Vec, percentage: Float) =
            this + ((target - this) * percentage)

    fun lerpSnap(target: Vec, percentage: Float, xSnap: Float, ySnap: Float = xSnap) =
            this.lerp(target, percentage).snapTo(target, xSnap, ySnap)

    fun slerp(target: Vec, percentage: Float): Vec {
        val dotP = Maths.clamp(this dot target, -1f, 1f)
        val theta = acos(dotP) * percentage
        val relativeVector = (target - (this * dotP)).norm()
        return (this * cos(theta)) + (relativeVector * sin(theta))
    }

    fun nlerp(target: Vec, percentage: Float) =
            this.lerp(target, percentage).norm()

    fun withAngle(angle: Angle) =
            Vec(this.mag(), angle)

    fun pointingTo(target: Vec) =
            Vec(this.mag(), (target - this).angle())

    fun rotate(angle: Angle) =
            Vec(this.mag(), this.angle() + angle)

    fun rotateAbout(pivot: Vec, angle: Angle) =
            (this - pivot).rotate(angle) + pivot

    /**
     * Returns a vector with the x and y coordinates switched.
     * @return a new vector (y, x)
     */
    fun reverse() =
            Vec(this.y, this.x)

    fun xComponent() =
            Vec(this.x, 0f)

    fun yComponent() =
            Vec(0f, this.y)

    fun floor() =
            Vec(floor(this.x), floor(this.y))

    fun round() =
            Vec(round(this.x), round(this.y))

    fun transform(transformation: (coordinate: Float) -> Float) =
            Vec(transformation(this.x), transformation(this.y))

    fun isInRect(min: Vec, max: Vec) =
            this.x in min.x..max.x && this.y in min.y..max.y

    fun plus(x: Float = 0f, y: Float = 0f) =
            Vec(this.x + x, this.y + y)

    fun minus(x: Float = 0f, y: Float = 0f) =
            Vec(this.x - x, this.y - y)

    operator fun unaryMinus() =
            Vec(-this.x, -this.y)

    operator fun plus(other: Vec) =
            Vec(this.x + other.x, this.y + other.y)

    operator fun minus(other: Vec) =
            Vec(this.x - other.x, this.y - other.y)

    operator fun times(scalar: Float) =
            Vec(this.x * scalar, this.y * scalar)

    operator fun Float.times(vector: Vec) =
            Vec(vector.x * this, vector.y * this)

    operator fun div(denominator: Float) =
            Vec(this.x / denominator, this.y / denominator)

    override operator fun compareTo(other: Vec) =
            this.magSq().compareTo(other.magSq())

    operator fun compareTo(magnitude: Float) =
            this.magSq().compareTo(magnitude * magnitude)

    override fun approx(other: Vec, error: Float) =
            abs(this.x - other.x) < error && abs(this.y - other.y) < error

    override fun toString() =
            "(${this.x}, ${this.y})"

}