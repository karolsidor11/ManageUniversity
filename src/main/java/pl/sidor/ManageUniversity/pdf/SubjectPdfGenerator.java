package pl.sidor.ManageUniversity.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class SubjectPdfGenerator {

    private  final PdfGenerator pdfGenerator;

    public ByteArrayInputStream subjectReport(Subject subject) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (isNull(subject)) {
           return pdfGenerator.prepareEmptyPdf(document, byteArrayOutputStream,
                   "Nie znaleziono przedmiotu o podanym identyfikatorze." );
        }
        prepareDocument(subject, document, byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    private void prepareDocument(Subject subject, Document document, ByteArrayOutputStream arrayOutputStream) {
        try {
            PdfWriter.getInstance(document, arrayOutputStream);
            document.open();
            document.add(getHeader(subject));
            document.add(new Paragraph(" "));
            document.add(getTable(subject));
            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    private Paragraph getHeader(Subject subject) {
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph1 = new Paragraph();
        paragraph1.add("Dane o przedmiocie " + subject.getName());
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.setFont(font);

        return paragraph1;
    }

    private PdfPTable getTable(Subject subject) {
        PdfPTable pdfPTable = new PdfPTable(2);
        addRows(pdfPTable, subject);
        return pdfPTable;
    }

    private void addRows(PdfPTable table, Subject subject) {
        table.addCell("Identyfikator");
        table.addCell(subject.getId().toString());
        table.addCell("Nazwa przedmiotu");
        table.addCell(subject.getName());
        table.addCell("Wykladowca");
        table.addCell(validateLecturer(subject));
        table.addCell("Numer sali");
        table.addCell(String.valueOf(subject.getRoomNumber()));
        table.addCell("Godzina rozpoczecia");
        table.addCell(String.valueOf(subject.getStartTime()));
        table.addCell("Godzina zakonczenia");
        table.addCell(String.valueOf(subject.getEndTime()));
    }

    private String validateLecturer(Subject subject) {
        try {
            String name = subject.getLecturer().getName();
            String lastName = subject.getLecturer().getLastName();
            return name + " " + lastName;

        } catch (Exception e) {
            return "Brak wykaldowcy";
        }
    }
}
