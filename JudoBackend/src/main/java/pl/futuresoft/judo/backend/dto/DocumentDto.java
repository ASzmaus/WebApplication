package pl.futuresoft.judo.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentDto {
    private Integer documentId;
    private String name;
    private byte[] content;

}
