fun sumThree(n: Int) = n + 3

fun curriedAddition(a: Int) = { b: Int ->
    a + b
}

fun main() {
    println(Maybe.Some(2) map ::sumThree)
    println(Maybe.Some(2) map { it + 3 })
    println(Maybe.None<Int>() map { x: Int -> x + 3 })
    println(Maybe.Some { a: Int -> a + 3 } apply Maybe.Some(2))
    println(Maybe.Some(2).apply(Maybe.Some { a: Int -> a + 3 }))
    println(Maybe.Some(3) map ::curriedAddition apply Maybe.Some(2))
    println(Maybe.Some(3).flatMap { Maybe.Some(it + 2) })
    println(Maybe.None<Int>().apply(Maybe.Some { a: Int -> a + 3 }))
    println(Maybe.Some { a: Int -> a + 3 }.apply(Maybe.None<Int>()))
    println(Maybe.Some(3).getOrElse { 2 })
    println(Maybe.None<Int>().getOrElse { 2 })
    Maybe.Some(1).process({
        println(it)
    }, {
    })
}