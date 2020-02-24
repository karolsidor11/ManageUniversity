package pl.sidor.ManageUniversity.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import static java.util.Objects.isNull;

@Component
public class LecturerPdfGenerator {

    public ByteArrayInputStream lecturerReport(Lecturer lecturer) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (isNull(lecturer)) {
            return prepereEmptyPdf(document, byteArrayOutputStream);
        }
        prepareDocument(lecturer, document, byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    private ByteArrayInputStream prepereEmptyPdf(Document document, ByteArrayOutputStream byteArrayOutputStream) {
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            Paragraph elements = getParagraphMessage();
            document.add(elements);
            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    private Paragraph getParagraphMessage() {
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph1 = new Paragraph();
        paragraph1.add("Nie znaleziono wykładowcy o podanym identyfikatorze.");
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.setFont(font);
        return paragraph1;
    }

    private void prepareDocument(Lecturer lecturer, Document document, ByteArrayOutputStream outputStream) {
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(getHeader(lecturer));
            document.add(new Paragraph(" "));
            document.add(prepareTable(lecturer));
            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    private Paragraph getHeader(Lecturer lecturer) {
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph1 = new Paragraph();
        paragraph1.add("Dane wykładowcy " + lecturer.getName() + " " + lecturer.getLastName());
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.setFont(font);
        return paragraph1;
    }

    private PdfPTable prepareTable(Lecturer lecturer) {
        PdfPTable pdfPTable = new PdfPTable(2);
        addRows(pdfPTable, lecturer);
        return pdfPTable;
    }

    private void addRows(PdfPTable table, Lecturer lecturer) {
        table.addCell("Identyfikator");
        table.addCell(lecturer.getId().toString());
        table.addCell("Imie");
        table.addCell(lecturer.getName());
        table.addCell("Nazwisko");
        table.addCell(lecturer.getLastName());
        table.addCell("Email");
        table.addCell(lecturer.getEmail());
        table.addCell("Miejscowosc");
        table.addCell(lecturer.getAdres().getCity());
        table.addCell("Ulica");
        table.addCell(lecturer.getAdres().getStreetAdress());
        table.addCell("Kod pocztowy");
        table.addCell(lecturer.getAdres().getZipCode());
        table.addCell("Numer telefonu");
        table.addCell(lecturer.getPhoneNumber().toString());
        table.addCell("Stopien naukowy");
        table.addCell(lecturer.getGrade());
    }
}
