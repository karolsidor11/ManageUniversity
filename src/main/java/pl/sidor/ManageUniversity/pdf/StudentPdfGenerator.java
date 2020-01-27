package pl.sidor.ManageUniversity.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.student.model.Student;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

@Component
public class StudentPdfGenerator {

    public ByteArrayInputStream studentReport(Student student) {
        Document document = new Document();
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        if (Objects.isNull(student)) {
            return prepareEmptyPdf(document, arrayOutputStream);
        }
        prepareDocument(student, document, arrayOutputStream);
        return new ByteArrayInputStream(arrayOutputStream.toByteArray());
    }

    private ByteArrayInputStream prepareEmptyPdf(Document document, ByteArrayOutputStream arrayOutputStream) {
        try {
            PdfWriter.getInstance(document, arrayOutputStream);
            document.open();
            Paragraph elements = getParagraphMessage();
            document.add(elements);
            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
        return new ByteArrayInputStream(arrayOutputStream.toByteArray());
    }

    private Paragraph getParagraphMessage() {
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph1 = new Paragraph();
        paragraph1.add("Nie znaleziono studenta o podanym identyfikatorze.");
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.setFont(font);
        return paragraph1;
    }

    private void prepareDocument(Student student, Document document, ByteArrayOutputStream arrayOutputStream) {
        try {
            PdfWriter.getInstance(document, arrayOutputStream);
            document.open();
            document.add(getHeader(student));
            document.add(new Paragraph(" "));
            document.add(getTable(student));
            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    private Paragraph getHeader(Student student) {
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph1 = new Paragraph();
        paragraph1.add("Dane studenta " + student.getName() + " " + student.getLastName());
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.setFont(font);

        return paragraph1;
    }

    private PdfPTable getTable(Student student) {
        PdfPTable pdfPTable = new PdfPTable(2);
        addRows(pdfPTable, student);
        return pdfPTable;
    }

    private void addRows(PdfPTable table, Student student) {
        table.addCell("Identyfikator");
        table.addCell(student.getId().toString());
        table.addCell("Imie");
        table.addCell(student.getName());
        table.addCell("Nazwisko");
        table.addCell(student.getLastName());
        table.addCell("Data urodzenia");
        table.addCell(student.getDateOfBirth().toString());
        table.addCell("Email");
        table.addCell(student.getEmail());
        table.addCell("Miejscowosc");
        table.addCell(student.getAdres().getCity());
        table.addCell("Ulica");
        table.addCell(student.getAdres().getStreetAdress());
        table.addCell("Kod pocztowy");
        table.addCell(student.getAdres().getZipCode());
        table.addCell("Numer telefonu");
        table.addCell(student.getPhoneNumber().toString());
        table.addCell("Grupa studencka");
        table.addCell(student.getStudentGroup().toString());
    }
}
