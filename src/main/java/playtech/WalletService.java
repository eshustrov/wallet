package playtech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(WalletService.class);
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
                                      @PathParam("transactionId") final int transactionId,
                                      @PathParam("balanceChange") final BigDecimal balanceChange) {

        System.out.println(LOGGER);
        LOGGER.info("IN: transaction {} for user {}: requested balance change: {}",
                new Object[]{transactionId, username, balanceChange});
        final WalletStatus status = new WalletStatus();
        status.transactionId = transactionId;

        final Player player;
        try {
            player = playerAccess.load(username);
        } catch (EmptyResultDataAccessException exception) {
            status.errorCode = NOT_FOUND;
            LOGGER.info("OUT: transaction {} for user {}: error code: {}",
                    new Object[]{status.transactionId, username, status.errorCode});
            return status;
        }

        player.balanceVersion++;
        player.balance = player.balance.add(balanceChange);
        playerAccess.update(player);

        status.balanceVersion = player.balanceVersion;
        status.balanceChange = balanceChange;
        status.balanceAfterChange = player.balance;
        LOGGER.info("OUT: transaction {} for user {}: balance {} of version {} after change {}",
                new Object[]{status.transactionId, username,
                        status.balanceAfterChange, status.balanceVersion, status.balanceChange});
        return status;
    }
}
