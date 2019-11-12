sealed class Either<L, R> {
    // flatMap을 메서드로 구현하기 위해 Left의 R과 Right의 L을 Nothing으로 구현하지 않음.
    data class Left<L, R>(val value: L): Either<L, R>()
    data class Right<L, R>(val value: R): Either<L, R>()

    // L을 out으로 구현하면 monad 타입이 in이라 문제가 됨.
    infix fun <T> flatMap(monad: (R) -> Either<L, T>): Either<L, T> = when (this) {
        is Left -> Left(value)
        is Right -> monad(value)
    }

    infix fun <T> map(functor: (R) -> T): Either<L, T> = flatMap {
        Right<L, T>(functor(it))
    }
}