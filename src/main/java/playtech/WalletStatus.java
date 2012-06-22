package playtech;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
public class WalletStatus {
    public long transactionId;
    public int errorCode;
    public long balanceVersion;
    public BigDecimal balanceChange = BigDecimal.ZERO;
    public BigDecimal balanceAfterChange = BigDecimal.ZERO;
}
