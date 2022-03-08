import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

public interface IHTTPController {
    <T> Observable<String> getResponse(HttpServerRequest<T> request);
}
