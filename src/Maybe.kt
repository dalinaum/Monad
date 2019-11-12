sealed class Maybe<T> {
    class None<T> : Maybe<T>() // Nothing을 쓰지 않음. getOrElse 구현을 위해
    data class Some<T>(val value: T) : Maybe<T>()

    infix fun <U> map(functor: (T) -> U): Maybe<U> = flatMap {
        Some(functor(it))
    }

    infix fun <U> flatMap(monad: (T) -> Maybe<U>): Maybe<U> = when (this) {
        is None -> None()
        is Some -> monad(value)
    }

    // 자바에서 어떻게 쓰는게 좋을까 생각하다 그냥 만듬.
    fun process(onSome: (T) -> Unit, onNone: () -> Unit): Unit = when (this) {
        is None -> onNone()
        is Some -> onSome(value)
    }

    infix fun getOrElse(default: () -> T): T = when (this) {
        is None -> default()
        is Some -> value
    }
}

infix fun <T, U> Maybe<(T) -> U>.apply(other: Maybe<T>): Maybe<U> =
    when (this) {
        is Maybe.None -> Maybe.None()
        is Maybe.Some -> other.map(value)
    }

fun <T, U> Maybe<T>.apply(
    f: Maybe<(T) -> U>,
    dummyImplicit: Any? = null
): Maybe<U> =
    when (this) {
        is Maybe.None -> Maybe.None()
        is Maybe.Some -> f.map { it(value) }
    }