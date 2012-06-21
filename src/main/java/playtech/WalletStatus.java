package playtech;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
public class WalletStatus {
    public long transactionId;
    int errorCode;
    public long balanceVersion;
    public BigDecimal balanceChange;
    public BigDecimal balanceAfterChange;
}
