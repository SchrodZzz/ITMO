import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

public class Main {
    public static void main(String[] args) {
        RxNettyAccountHttpServer server = new RxNettyAccountHttpServer(new AccountDao(new MarketClient()));

        HttpServer
                .newServer(8081)
                .start((req, resp) -> {
                    Observable<String> response = server.getResponse(req);
                    return resp.writeString(response.map(r -> r + System.lineSeparator()));
                })
                .awaitShutdown();
    }
}
