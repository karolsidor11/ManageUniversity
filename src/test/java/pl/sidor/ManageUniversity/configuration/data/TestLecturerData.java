package configuration.data;

import pl.sidor.ManageUniversity.lecturer.model.Lecturer;

public class TestLecturerData {

    public static Lecturer prepareLecturer() {
        return Lecturer.builder()
                .id(1L)
                .name("Jan")
                .lastName("Kowalski")
                .email("kowalski@wp.pl")
                .grade("Doktor")
                .build();
    }

    public static Lecturer updatelecturer(){
        return  Lecturer.builder()
                .id(1L).name("Jan")
                .lastName("Nowak")
                .email("nowak@wp.pl")
                .build();
    }
}
