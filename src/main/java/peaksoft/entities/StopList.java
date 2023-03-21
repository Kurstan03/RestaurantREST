package peaksoft.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author kurstan
 * @created at 16.03.2023 18:07
 */
@Entity
@Table(name = "stop_lists")
@Getter
@Setter
@NoArgsConstructor
public class StopList {
    @Id
    @SequenceGenerator(
            name = "stop_list_id_gen",
            sequenceName = "stop_list_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stop_list_id_gen"
    )
    private Long id;
    private String reason;
    private LocalDate date;
    @ManyToOne(cascade =
                   {CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH})
    private MenuItem menuItem;
}
