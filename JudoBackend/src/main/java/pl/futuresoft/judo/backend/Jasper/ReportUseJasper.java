package pl.futuresoft.judo.backend.Jasper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.futuresoft.judo.backend.entity.Document;
import pl.futuresoft.judo.backend.exception.FetchDocumentException;
import pl.futuresoft.judo.backend.repository.DocumentRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@Component
public class ReportUseJasper {

    private DocumentRepository documentRepository;

    public ReportUseJasper(DocumentRepository documentRepository){
        this.documentRepository=documentRepository;
    }

    public InputStream fetchDocument(Document document){
        return documentRepository.findById(document.getDocumentId())
                .map(p->new ByteArrayInputStream(p.getContent()))
                .orElseThrow(FetchDocumentException::new);
    }
}
