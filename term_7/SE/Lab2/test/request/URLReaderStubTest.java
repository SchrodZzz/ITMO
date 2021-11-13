package request;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.UncheckedIOException;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class URLReaderStubTest {
    private static final int PORT = 32453;
    private final URLReader urlReader = new URLReader();

    @Test
    public void readAsText() {
        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/ping"))
                    .then(stringContent("hello"));

            String result = urlReader.readAsText("http://localhost:" + PORT + "/ping");

            assertEquals("hello\n", result);
        });
    }

    @Test
    public void readAsTextWithNotFoundError() {
        assertThrows(UncheckedIOException.class, () -> withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/ping"))
                    .then(status(HttpStatus.NOT_FOUND_404));

            urlReader.readAsText("http://localhost:" + PORT + "/ping");
        }));
    }

    private void withStubServer(int port, Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(port).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
