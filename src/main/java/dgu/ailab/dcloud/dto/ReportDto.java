package dgu.ailab.dcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private boolean isAnswered;
    private int reportId;
    private Date createdAt;
    private String category;
    private String userId;
    private String department;
    private String requirement;
    private int sshPort;
    private String studentId;
    private String why;
    private Integer postId; // postId 필드 추가

    @Override
    public String toString() {
        return "리포트 정보\n" +
                " - 답변 여부: " + (isAnswered ? "답변됨" : "답변되지 않음") + "\n" +
                " - 리포트 ID: " + reportId + "\n" +
                " - 작성일: " + createdAt + "\n" +
                " - 카테고리: '" + category + "'\n" +
                " - 사용자 ID: '" + userId + "'\n" +
                " - 부서: '" + department + "'\n" +
                " - 요구사항: '" + requirement + "'\n" +
                " - SSH 포트: " + sshPort + "\n" +
                " - 학생 ID: '" + studentId + "'\n" +
                " - 이유: '" + why + "'\n" +
                " - 포스트 ID: " + postId;

    }
}
