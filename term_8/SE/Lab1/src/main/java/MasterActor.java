import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MasterActor extends UntypedActor {
    private final Set<Engine> SEARCH_ENGINES = EnumSet.allOf(Engine.class);

    private final Client client;
    private ActorRef requestSender;
    private final Result response;

    public MasterActor(Client client) {
        this.response = new Result();
        this.client = client;
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof String) {
            requestSender = getSender();
            String queryText = (String) o;

            for (Engine engine : SEARCH_ENGINES) {
                ActorRef childActor = getContext().actorOf(Props.create(ChildActor.class, client));
                childActor.tell(new Request(engine, queryText), self());
            }

            getContext().setReceiveTimeout(Duration.create(5, TimeUnit.SECONDS));
        } else if (o instanceof Result) {
            Result result = (Result) o;
            response.mergeSearchResults(result);
            SEARCH_ENGINES.removeAll(result.getSearchEngineResults().keySet());

            if (SEARCH_ENGINES.isEmpty()) {
                requestSender.tell(response, self());
                getContext().stop(self());
            }
        } else if (o instanceof ReceiveTimeout) {
            for (Engine engine : SEARCH_ENGINES) {
                response.mergeSearchResults(new Result(engine, Collections.emptyList()));
            }

            requestSender.tell(response, self());
            getContext().stop(self());
        }
    }
}