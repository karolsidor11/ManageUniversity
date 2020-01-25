package pl.sidor.ManageUniversity.header;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sidor.ManageUniversity.enums.ApplicationDetails;
import pl.sidor.ManageUniversity.utils.DateUtils;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Header {

    private String applicationName;
    private String applicationVersion;
    private String referenceDateTime;

    public static Header getHeader() {
        Header header = new Header();
        header.setApplicationName(ApplicationDetails.NAME.getDescription());
        header.setApplicationVersion(ApplicationDetails.VERSION.getDescription());
        header.setReferenceDateTime(DateUtils.formatDateNow());
        return header;
    }
}
