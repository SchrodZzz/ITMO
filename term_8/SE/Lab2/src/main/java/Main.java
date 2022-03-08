import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.Document;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

public class Main {
    public static void main(String[] args) {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase("catalog");
        MongoCollection<Document> users = database.getCollection("users");
        MongoCollection<Document> products = database.getCollection("products");

        IHTTPController controller = new HTTPController(new ReactiveDao(users, products));

        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    Observable<String> response = controller.getResponse(req);
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }
}
