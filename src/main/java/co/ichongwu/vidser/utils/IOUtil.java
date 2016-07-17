package co.ichongwu.vidser.utils;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {

    public static final int BUFFER_SIZE = 1024 * 4;
    
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }
    
    public static int copy(InputStream in, OutputStream out) throws IOException {
        int count = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int length = -1;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
            count += length;
        }
        out.flush();
        return count;
    }
    
    public static int copy(Reader in, Writer out) throws IOException {
        int count = 0;
        char[] buffer = new char[BUFFER_SIZE];
        int length = -1;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
            count += length;
        }
        out.flush();
        return count;
    }
    
    public static byte[] read(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
        copy(in, out);
        return out.toByteArray();
    }
    
    public static String read(InputStream in, Charset charset) throws IOException {
        StringWriter writer = new StringWriter(BUFFER_SIZE);
        copy(new InputStreamReader(in, charset), writer);
        return writer.toString();
    }
    
    public static List<String> readLines(InputStream in, Charset charset) throws IOException {
    	List<String> list = new ArrayList<String>();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            list.add(line);
        }
        return list;
    }
    
}
