package pl.futuresoft.judo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "Document")
@Table(name = "document", schema = "administration")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id", nullable = false)
    private Integer documentId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable =true)
    private String description;
    @Column(name = "content", nullable = false)
    private byte[] content;

}
