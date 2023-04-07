package ua.glumaks.util;

import lombok.extern.slf4j.Slf4j;
import ua.glumaks.exception.DecompressionException;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
public class CompressionUtil {

    private final static int COMPRESSION_LEVEL = Deflater.BEST_COMPRESSION;


    public static byte[] compress(byte[] input) {
        Deflater compressor = new Deflater(COMPRESSION_LEVEL, true);
        compressor.setInput(input);
        compressor.finish();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (!compressor.finished()) {
            int len = compressor.deflate(buffer);
            baos.write(buffer, 0, len);
        }

        compressor.end();
        byte[] compressed = baos.toByteArray();
        log.debug("Compressed size: {}", compressed.length);
        return compressed;
    }

    public static byte[] decompress(byte[] input) {
        Inflater decompressor = new Inflater(true);
        decompressor.setInput(input);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try {
            while (!decompressor.finished()) {
                int len = decompressor.inflate(buffer);
                baos.write(buffer, 0, len);
            }
        } catch (DataFormatException e) {
            log.warn("Can't decompress bytes");
            throw new DecompressionException(e);
        } finally {
            decompressor.end();
        }

        byte[] compressed = baos.toByteArray();
        log.debug("Compressed size: {}", compressed.length);
        return compressed;
    }

}
