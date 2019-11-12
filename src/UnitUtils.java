import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import java.util.function.Consumer;

public class UnitUtils {
    public static <T> Function1<T, Unit> fromConsumer(Consumer<T> consumer) {
        return t -> {
            consumer.accept(t);
            return Unit.INSTANCE;
        };
    }

    public static Function0<Unit> fromRunnable(Runnable runnable) {
        return () -> {
            runnable.run();
            return Unit.INSTANCE;
        };
    }
}
