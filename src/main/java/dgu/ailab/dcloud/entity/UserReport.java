package dgu.ailab.dcloud.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_report")
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    @Column(name = "access_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accessDate; // 접근 시간 등의 메타데이터를 포함할 수 있습니다.

    // 추가적으로 관계를 설명하는 메타데이터 필드가 필요한 경우 여기에 정의할 수 있습니다.
}
