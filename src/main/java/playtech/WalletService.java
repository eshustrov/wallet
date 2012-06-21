package playtech;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class WalletService {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello World!";
    }
}
