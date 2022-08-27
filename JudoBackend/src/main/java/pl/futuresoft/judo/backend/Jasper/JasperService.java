package pl.futuresoft.judo.backend.Jasper;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.springframework.stereotype.Component;
import pl.futuresoft.judo.backend.entity.Document;
import pl.futuresoft.judo.backend.exception.JasperException;

import java.io.ByteArrayOutputStream;
import java.util.Map;


@Component
@Slf4j
public class JasperService {

    private final ReportUseJasper reportUseJasper;

    public JasperService(ReportUseJasper reportUseJasper) {
        this.reportUseJasper = reportUseJasper;
    }

    public byte[] createDocument(Map<String, Object> stringObjectMap, Document document) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        byte[] result;
        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(reportUseJasper.fetchDocument(document));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, stringObjectMap, new JREmptyDataSource());

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));

            SimplePdfReportConfiguration reportConfig  = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            exporter.setConfiguration(reportConfig);
            exporter.exportReport();

            result = byteArrayOutputStream.toByteArray();

        } catch(Exception e){
            log.error("Error: ",e);
            throw new JasperException(e);
        }
        return result;
    }
}
