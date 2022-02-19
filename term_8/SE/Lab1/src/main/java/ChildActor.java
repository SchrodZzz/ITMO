import akka.actor.UntypedActor;

public class ChildActor extends UntypedActor {
    private final Client client;

    public ChildActor(Client client) {
        this.client = client;
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof Request) {
            Request request = (Request) o;
            getSender().tell(client.searchQuery(request), self());
            getContext().stop(self());
        }
    }
}