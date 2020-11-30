package ivera;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 25, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ReadLongBenchmark {

    static void decodeLongBuffer(int count, ByteBuffer in, long[] longs) {
        in.asLongBuffer().get(longs, 0, count);
    }

    static void decodeBuffer(int count, ByteBuffer in, long[] longs) {
        for (int  i = 0; i < count; i++) {
            longs[i] = in.getLong();
        }
    }

    @Benchmark
    public void readLongsBuffer(LongDecodeState state, Blackhole bh) {
        decodeLongBuffer(state.count, state.input, state.outputLongs);
        bh.consume(state.outputLongs);
    }

    @Benchmark
    public void readBytesBuffer(LongDecodeState state, Blackhole bh) {
        decodeBuffer(state.count, state.input, state.outputLongs);
        bh.consume(state.outputLongs);
    }

    public static void main(String[] args) throws Exception {
        Main.main(args);
    }
}
