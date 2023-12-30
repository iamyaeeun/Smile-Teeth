package prac.prac.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[a-zA-Z0-9]).{8,64}$", message = "비밀번호가 형식에 맞지 않습니다.")
    private String password;

    private String name;

    @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$", message = "전화번호 형식이 맞지 않습니다.")
    private String phone;

    @Column(nullable = false)
    private Long age;

    @Column(nullable = false)
    private boolean ortho;

    @ColumnDefault("'기본 이미지'")
    private String profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
}