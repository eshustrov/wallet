package playtech;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("/")
public class WalletService {
    private static final BigDecimal HUNDRED = new BigDecimal(100);

    @PUT
    @Path("balance/{username}/{transactionId}/{balanceChange}")
    @Produces(MediaType.APPLICATION_JSON)
    public WalletStatus changeBalance(@PathParam("username") String username,
                                      @PathParam("transactionId") long transactionId,
                                      @PathParam("balanceChange") BigDecimal balanceChange) {
        WalletStatus status = new WalletStatus();
        status.transactionId = transactionId;
        status.errorCode = 0;
        status.balanceVersion = 1;
        status.balanceChange = balanceChange;
        status.balanceAfterChange = HUNDRED.add(balanceChange);
        return status;
    }
}
