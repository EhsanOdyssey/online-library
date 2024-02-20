package neo.ehsanodyssey.library.dal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
@Entity
@Table(
        name = "LIBRARY",
        uniqueConstraints = { @UniqueConstraint(name = "UniqueUserAndLibrary", columnNames = { "user_id", "name" }) }
)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"user", "books"})
@ToString(exclude = {"user", "books"})
public class Library {
    @Id
    @UuidGenerator
    private String id;
    @NotNull
    @Column(name = "NAME", length = 256, nullable = false)
    private String name;
    @NotNull
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private boolean active;

    @Basic
    @Column(name = "USER_ID", nullable = false, insertable = false, updatable = false)
    private String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "LIBRARY_BOOKS",
            joinColumns = @JoinColumn(name = "LIBRARY_ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"LIBRARY_ID", "BOOK_ID"})
    )
    @JsonManagedReference
    private List<Book> books;

    public void addBook(Book book) {
        if (this.books == null) {
            this.books = new ArrayList<>();
        }
        this.books.add(book);
    }

    public void removeBook(Book book) {
        if (!CollectionUtils.isEmpty(this.books)) {
            this.books.remove(book);
        }
    }
}
