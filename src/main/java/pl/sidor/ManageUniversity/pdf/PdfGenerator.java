package pl.sidor.ManageUniversity.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Component
public class PdfGenerator {

    public ByteArrayInputStream prepareEmptyPdf(Document document, ByteArrayOutputStream stream, String message) {
        try {
            PdfWriter.getInstance(document, stream);
            document.open();
            Paragraph paragraphMessage = getParagraphMessage(message);
            document.add(paragraphMessage);
            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
        return new ByteArrayInputStream(stream.toByteArray());
    }

    private Paragraph getParagraphMessage(String message) {
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph1 = new Paragraph();
        paragraph1.add(message);
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.setFont(font);
        return paragraph1;
    }
}
