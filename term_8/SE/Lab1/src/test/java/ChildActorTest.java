import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;
import static org.assertj.core.api.Assertions.assertThat;

public class ChildActorTest {
    private ActorSystem system;

    @Before
    public void setUp() {
        system = ActorSystem.create("ChildActorTest");
    }

    @After
    public void tearDown() {
        system.terminate();
    }

    @Test
    public void testChildActor() {
        ActorRef childActor = system.actorOf(Props.create(ChildActor.class, new StubClient(0)));

        Result response = (Result) ask(
                childActor,
                new Request(Engine.YANDEX, "query"),
                Timeout.apply(10, TimeUnit.SECONDS)
        ).toCompletableFuture().join();

        assertThat(response.getSearchEngineResults()).containsOnlyKeys(Engine.YANDEX);
        assertThat(response.getSearchEngineResults().get(Engine.YANDEX)).isNotEmpty();
    }
}