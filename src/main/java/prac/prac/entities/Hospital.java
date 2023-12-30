package prac.prac.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hospital")
public class Hospital {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Long id;

    @Column(nullable = false)
    private String hospitalName;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}