package playtech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Component
@Scope("request")
@Path("/")
public class WalletService {
    private static final BigDecimal HUNDRED = new BigDecimal(100);

    private final DataSource dataSource;

    @Autowired
    public WalletService(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PUT
    @Path("balance/{username}/{transactionId}/{balanceChange}")
    @Produces(MediaType.APPLICATION_JSON)
    public WalletStatus changeBalance(@PathParam("username") final String username,
                                      @PathParam("transactionId") final long transactionId,
                                      @PathParam("balanceChange") final BigDecimal balanceChange) {
        final WalletStatus status = new WalletStatus();
        status.transactionId = transactionId;
        status.errorCode = 0;
        status.balanceVersion = 1;
        status.balanceChange = balanceChange;
        status.balanceAfterChange = HUNDRED.add(balanceChange);
        System.out.println(dataSource);
        return status;
    }
}
