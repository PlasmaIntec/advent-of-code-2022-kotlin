import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import java.time.Duration

fun measureDuration(name: String, block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    val now = System.currentTimeMillis()
    val elapsed = Duration.ofMillis(now - start).toMillis()
    println("\"$name\" took $elapsed ms")
}

fun <T> CoroutineScope.accumulateProducer(consumer: Channel<T>, block: () -> T) =
    produce<T>(Dispatchers.Default) {
        val item = block()
        consumer.send(item)
    }

fun <R, T> CoroutineScope.accumulateConsumer(consumer: Channel<T>, initialValue: R, mergeBlock: (R, T) -> R) =
    launch(Dispatchers.Default) {
        var accumulator = initialValue
        while (!consumer.isEmpty) {
            val item = consumer.receive()
            accumulator = mergeBlock(accumulator, item)
        }
        println("accumulator: $accumulator")
    }