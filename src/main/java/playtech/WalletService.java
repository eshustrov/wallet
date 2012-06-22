package playtech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

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
    private static final int NOT_FOUND = 1;

    private final PlayerAccess playerAccess;

    @Autowired
    public WalletService(final PlayerAccess playerAccess) {
        this.playerAccess = playerAccess;
    }

    @PUT
    @Path("balance/{username}/{transactionId}/{balanceChange}")
    @Produces(MediaType.APPLICATION_JSON)
    public WalletStatus changeBalance(@PathParam("username") final String username,
                                      @PathParam("transactionId") final long transactionId,
                                      @PathParam("balanceChange") final BigDecimal balanceChange) {

        final WalletStatus status = new WalletStatus();
        status.transactionId = transactionId;

        final Player player;
        try {
            player = playerAccess.load(username);
        } catch (EmptyResultDataAccessException exception) {
            status.errorCode = NOT_FOUND;
            return status;
        }

        status.balanceVersion = player.balanceVersion;
        status.balanceChange = balanceChange;
        status.balanceAfterChange = player.balance.add(balanceChange);
        System.out.println(playerAccess);
        return status;
    }
}
