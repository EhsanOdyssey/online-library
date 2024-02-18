package neo.ehsanodyssey.library.dal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "USER")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"libraries"})
@ToString(exclude = {"libraries"})
public class User {
    @Id
    @UuidGenerator
    private String id;
    @NotNull
    @Column(name = "USERNAME", unique = true, length = 100, nullable = false, updatable = false)
    private String username;
    @Column(name = "FIRST_NAME", length = 256)
    private String firstName;
    @Column(name = "LAST_NAME", length = 256)
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<Library> libraries;
}
