import java.io.File
import java.io.FileNotFoundException
import java.lang.IllegalArgumentException

fun openFile(fileName: Maybe<String>): Either<Exception, File> = when (fileName) {
    is Maybe.Some -> Either.Right(File(fileName.value))
    is Maybe.None -> Either.Left(IllegalArgumentException("The file name is not valid."))
}

fun readLines(file: File): Either<Exception, List<String>> = try {
    val lines = file.readLines()
    Either.Right(lines)
} catch (e: FileNotFoundException) {
    Either.Left(e)
}

fun makeLinesLower(lines: List<String>): List<String> =
    lines.map(String::toLowerCase)

fun main() {
    val file = openFile(Maybe.Some("/Users/dalinaum/project/monad/src/Either.kt"))
    val wrongFile = openFile(Maybe.Some("onad/src/Either.kt"))
    println(file)
    println(openFile(Maybe.None()))
    println(file.flatMap(::readLines))
    val wrong1 = wrongFile.flatMap(::readLines)
    println(wrong1)

    val lowerLines = file.flatMap(::readLines)
        .map(::makeLinesLower)
    println(lowerLines)

    println(file.flatMap(::readLines)
        .map { it.map(String::toLowerCase) })

    when (lowerLines) {
        is Either.Left -> {
            println("There is something weird.")
            lowerLines.value.printStackTrace()
        }
        is Either.Right -> {
            println("Good!")
            lowerLines.value.forEach {
                println(it)
            }
        }
    }

    when (val wrong = wrongFile.flatMap(::readLines).map(::makeLinesLower)) {
        is Either.Left -> {
            println("There is something weird.")
            println(wrong.value.message)
        }
        is Either.Right -> {
            println("Good!")
            wrong.value.forEach {
                println(it)
            }
        }
    }
}