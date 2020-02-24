package pl.sidor.ManageUniversity.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

@Component
public class SchedulePdfGenerator {

    public ByteArrayInputStream studentReport(Schedule schedule) {
        Document document = new Document();
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        if (Objects.isNull(schedule)) {
            return prepareEmptyPdf(document, arrayOutputStream);
        }
        prepareDocument(schedule, document, arrayOutputStream);
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
        paragraph1.add("Nie znaleziono rozkladu zajec o podanym identyfikatorze.");
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.setFont(font);
        return paragraph1;
    }

    private void prepareDocument(Schedule schedule, Document document, ByteArrayOutputStream arrayOutputStream) {
        try {
            PdfWriter.getInstance(document, arrayOutputStream);
            document.open();
            document.add(getHeader(schedule));
            document.add(new Paragraph(" "));
            document.add(getTable(schedule));
            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    private Paragraph getHeader(Schedule schedule) {
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph1 = new Paragraph();
        paragraph1.add("Rozklad zajec na " + schedule.getDayOfWeek().getDay());
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.setFont(font);

        return paragraph1;
    }

    private PdfPTable getTable(Schedule schedule) {
        PdfPTable pdfPTable = new PdfPTable(2);
        addRows(pdfPTable, schedule);
        return pdfPTable;
    }

    private void addRows(PdfPTable table, Schedule schedule) {
        table.addCell("Identyfikator  rozkladu");
        table.addCell(schedule.getId().toString());
        table.addCell("Dzien");
        table.addCell(schedule.getDayOfWeek().getDay());
        table.addCell("Grupa  studencka");
        table.addCell(String.valueOf(schedule.getStudentGroup()));
        table.addCell("Numer tygodnia");
        table.addCell(String.valueOf(schedule.getWeekNumber()));

        schedule.getSubjects().forEach(subject -> {
            table.addCell("----------------");
            table.addCell("----------------");
            table.addCell("Identyfikator przedmiotu");
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
        });
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
