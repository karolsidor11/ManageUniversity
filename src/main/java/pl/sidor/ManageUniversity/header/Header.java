package pl.sidor.ManageUniversity.header;

import lombok.Getter;
import pl.sidor.ManageUniversity.enums.ApplicationDetails;
import pl.sidor.ManageUniversity.utils.DateUtils;

@Getter
public class Header {

    private final String applicationName = ApplicationDetails.NAME.getDescription();
    private final String applicationVersion = ApplicationDetails.VERSION.getDescription();
    private final String referenceDateTime = DateUtils.formatDateNow();
    private static Header header = null;

    public static Header getInstance() {
        if (header == null) {
            header = new Header();
            header.getApplicationVersion();
            header.getApplicationVersion();
            header.getReferenceDateTime();
        }
        return header;
    }
}
