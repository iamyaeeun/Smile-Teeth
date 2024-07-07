package prac.prac.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Size;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private int totalScore;

    @Column(nullable = false)
    @Size(min = 10)
    private String content;

    @Column(nullable = false)
    private Long payment;

    public Review(User user){
        this.user = user;
    }
}
