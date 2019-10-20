package pl.sidor.ManageUniversity.api.BaseEntity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "User")
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "myGenerator")
    @GenericGenerator(name = "myGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private Integer id;

    @Column(name = "IMIE")
    private String name;

    @Column(name = "NAZWISKO")
    private String lastName;

    @Column(name = "WIEK")
    private Integer age;

    @Column(name = "WZROST")
    private Double height;

    @Column(name = "WAGA")
    private Double weight;
}
