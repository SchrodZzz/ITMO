import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;
import static org.assertj.core.api.Assertions.assertThat;

public class MasterActorTest {
    private ActorSystem system;

    @Before
    public void setUp() {
        system = ActorSystem.create("MasterActorTest");
    }

    @After
    public void tearDown() {
        system.terminate();
    }

    @Test
    public void testMasterActor() {
        ActorRef masterActor = system.actorOf(Props.create(MasterActor.class, new StubClient(0)));

        Result response = (Result) ask(
                masterActor,
                "query",
                Timeout.apply(10, TimeUnit.SECONDS)
        ).toCompletableFuture().join();

        assertThat(response.getSearchEngineResults().keySet()).isEqualTo(EnumSet.allOf(Engine.class));
        assertThat(response.getSearchEngineResults().get(Engine.YANDEX)).isNotEmpty();
    }

    @Test
    public void testMasterActorTimeout() {
        ActorRef masterActor = system.actorOf(Props.create(MasterActor.class, new StubClient(6000)));

        Result response = (Result) ask(
                masterActor,
                "query",
                Timeout.apply(10, TimeUnit.SECONDS)
        ).toCompletableFuture().join();

        assertThat(response.getSearchEngineResults().keySet()).isEqualTo(EnumSet.allOf(Engine.class));
        assertThat(response.getSearchEngineResults().get(Engine.YANDEX)).isEmpty();
    }
}