
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

public class Writer {
    public static void main(String[] args) throws Exception {
        String filePath = "shared_memory.dat";
        long size = 1024;

        // Create or open the file
        RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
        FileChannel channel = raf.getChannel();

        // Map file into memory (shared)
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, size);

        // Write some data
        String message = "Hello from Writer! Time: " + System.currentTimeMillis();
        buffer.put(message.getBytes());

        System.out.println("Writer wrote: " + message);
        System.out.println("Writer finished. Reader can now read.");

        Thread.sleep(15000); // Keep program alive so reader can run
        channel.close();
        raf.close();
    }
}
