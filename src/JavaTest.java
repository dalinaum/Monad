import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class JavaTest {
    public static void main(String[] args) {
        System.out.println(new Maybe.Some<>(3).map(i -> i + 1));
        Maybe<Integer> five = new Maybe.Some<>(3).flatMap(i -> new Maybe.Some<>(i + 2));
        System.out.println(five);

        if (five instanceof Maybe.Some) {
            System.out.println(((Maybe.Some<Integer>) five).getValue());
        }

        System.out.println(new Maybe.Some<>(3).getOrElse(() -> 2));
        System.out.println(new Maybe.None<Integer>().getOrElse(() -> 2));

        Function1<Integer, Integer> inc = i -> i + 1;
        Maybe.Some<Function1<Integer, Integer>> withFunctor = new Maybe.Some<>(inc);
        Maybe<Integer> six = MaybeKt.apply(withFunctor, five);
        System.out.println(six);

        six.process(i -> {
            System.out.println(i);
            return Unit.INSTANCE; // It sucks really!
        }, () -> Unit.INSTANCE);

        six.process(UnitUtils.fromConsumer(i -> {
            System.out.println(i);
        }), UnitUtils.fromRunnable(() -> {
        }));
    }
}
