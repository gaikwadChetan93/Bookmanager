package mobileutility.bookmanager;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.exceptions.MockitoLimitations;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.nio.charset.StandardCharsets;

/**
 * Created by hardik on 13/11/17.
 */

@RunWith(JUnit4.class)
public class HttpConnectionTest {
    @Mock
    HttpURLConnection httpURLConnection;

    MockURLStreamHandler mockURLStreamHandler;

    private URL url;

    @Before
    public void init() throws MalformedURLException {
        MockitoAnnotations.initMocks(this);
        mockURLStreamHandler = new MockURLStreamHandler();
        url = new URL("http", "vegfru.com/api/getusers", "");
    }

    @Test
    public void mockApiCall() throws IOException {
        //URL.setURLStreamHandlerFactory(mockURLStreamHandler);
        mockURLStreamHandler.openConnection(url);
        MockHttpURLConnection connection = mockURLStreamHandler.getMockHttpURLConnection();
        /*
            URL url = connection.getURL();
            String host = url.getHost();
            InputStream inputStream = connection.getInputStream();
            String request = getStringFromInputStream(inputStream);
            System.out.print(request);
            System.out.print(host);
        */
        connection.connect();
        OutputStream outputStream = connection.getOutputStream();
        String str = outputStream.toString();
        System.out.print(str);
    }

    private String getStringFromInputStream(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    class MockURLStreamHandler extends URLStreamHandler implements URLStreamHandlerFactory {

        MockHttpURLConnection mockHttpURLConnection;

        public MockHttpURLConnection getMockHttpURLConnection() {
            return mockHttpURLConnection;
        }

        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            mockHttpURLConnection = new MockHttpURLConnection(u);
            return mockHttpURLConnection;
        }

        @Override
        public URLStreamHandler createURLStreamHandler(String protocol) {
            return this;
        }
    }

    class MockHttpURLConnection extends HttpURLConnection {

        /**
         * Constructor for the HttpURLConnection.
         *
         * @param u the URL
         */
        protected MockHttpURLConnection(URL u) {
            super(u);
        }

        @Override
        public InputStream getInputStream() throws IOException {
            String str = "Simple input";
            InputStream is = new ByteArrayInputStream(str.getBytes());
            return is;
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            String str = "Simple output";
            OutputStream os = new ByteArrayOutputStream();
            os.write(str.getBytes(StandardCharsets.UTF_8));
            return os;
        }

        @Override
        public void disconnect() {

        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        @Override
        public void connect() throws IOException {

        }
    }
}
