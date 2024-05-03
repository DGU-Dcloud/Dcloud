package dgu.ailab.dcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor // Lombok 어노테이션을 사용하여 기본 생성자 생성
public class ActionRequestDto {
    private String action;
    private List<Integer> ids;


}
