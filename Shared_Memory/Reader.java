
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

public class Reader {
    public static void main(String[] args) throws Exception {
        String filePath = "shared_memory.dat";
        long size = 1024;

        RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
        FileChannel channel = raf.getChannel();

        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, size);

        // Read data
        buffer.rewind();
        byte[] bytes = new byte[100];
        buffer.get(bytes);
        String message = new String(bytes).trim();

        System.out.println("Reader received: " + message);

        channel.close();
        raf.close();
    }
}