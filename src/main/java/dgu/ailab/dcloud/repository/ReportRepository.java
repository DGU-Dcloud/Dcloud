package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUserId(String userId);
    Report findByPost_PostID(Integer postId);
    List<Report> findByIsAnsweredFalse();
}
