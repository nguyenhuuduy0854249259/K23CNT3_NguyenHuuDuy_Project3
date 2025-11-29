package k23cnt3.nhdLesson08.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class nhdBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String name;
    private String description;
    private String imgUrl;
    private Integer quantity;
    private Double price;
    private Boolean isActive;
    // Tạo mối quan hệ với bảng author
    @ManyToMany
    @JoinTable(
            name = "vtd_Book_Author",
            joinColumns = @JoinColumn(name = "vtdBookId"),
            inverseJoinColumns = @JoinColumn(name =
                    "vtdAuthorId")
    )
    private List<nhdAuthor> vtdAuthors = new ArrayList<>();
}
