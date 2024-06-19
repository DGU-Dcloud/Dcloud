package dgu.ailab.dcloud.repository;

import dgu.ailab.dcloud.entity.DockerImageId;
import dgu.ailab.dcloud.entity.DockerImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DockerImagesRepository extends JpaRepository<DockerImages, DockerImageId> {
}