package ivera;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;

@State(Scope.Benchmark)
public class LongDecodeState {

    private FileChannel channel;
    ByteBuffer input;

    int count  = 128; 
    
    long[] outputLongs;
    
    @Param({"LE", "BE"})
    String byteOrder;

    @Setup(Level.Trial)
    public void setupTrial() throws IOException {
        Path path = Files.createTempFile("LongDecodeState", ".bench");
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.APPEND, StandardOpenOption.WRITE)) {
            byte[] data = new byte[count * Long.BYTES];
            new Random(0).nextBytes(data);
            channel.write(ByteBuffer.wrap(data));
        }
        channel = FileChannel.open(path, StandardOpenOption.READ);
        input = channel.map(FileChannel.MapMode.READ_ONLY, 0, count * Long.BYTES);
        if ("BE".equals(byteOrder)) {
            input.order(ByteOrder.BIG_ENDIAN);
        } else if ("LE".equals(byteOrder)) {
            input.order(ByteOrder.LITTLE_ENDIAN);
        } else {
            throw new IllegalArgumentException();
        }
        
        outputLongs = new long[count];
    }

    @Setup(Level.Invocation)
    public void setupInvocation() {
        // Reset the position of the buffer
        input.position(0);
    }

    @TearDown(Level.Trial)
    public void tearDownTrial() throws IOException {
        input = null;
        if (channel != null) {
            channel.close();
        }
    }

}
