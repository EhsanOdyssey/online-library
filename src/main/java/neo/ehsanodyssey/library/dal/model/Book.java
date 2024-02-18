package neo.ehsanodyssey.library.dal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
@Entity
@Table(name = "BOOK")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"libraries"})
@ToString(exclude = {"libraries"})
public class Book {
    @Id
    @UuidGenerator
    private String id;
    @NotNull
    @Column(name = "ISBN_13", unique = true, length = 36, nullable = false, updatable = false)
    private String isbn13;
    @NotNull
    @Column(name = "TITLE", length = 256, nullable = false)
    private String title;
    private int pages;

    @ManyToMany(mappedBy = "books")
    @JsonBackReference
    private List<Library> libraries;
}
