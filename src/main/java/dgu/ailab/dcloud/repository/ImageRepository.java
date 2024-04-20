package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.entity.DockerImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<DockerImages, Long> {

}
