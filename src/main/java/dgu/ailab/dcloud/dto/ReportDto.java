package dgu.ailab.dcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor // Lombok 어노테이션을 사용하여 기본 생성자 생성
public class ReportDto {
    private boolean isAnswered=false;
    private int reportId;
    private Date createdAt;
    private String category;
    private String serverName;
    private String userId;
    private String department;
    private String requirement;
    private int sshPort;
    private String studentId;
    private String why;


    public ReportDto(Boolean isAnswered, Integer reportID, Date createdAt, String category, String userId, String department, String requirement, Integer sshPort, String studentID, String why) {
    }
}
