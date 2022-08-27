package pl.futuresoft.judo.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.futuresoft.judo.backend.command.AgreementCommand;
import pl.futuresoft.judo.backend.dto.AgreementDto;
import pl.futuresoft.judo.backend.service.AgreementService;


@RestController
public class AgreementController {

    @Autowired
    AgreementService agreementService;

    @PostMapping("/agreement")
    public ResponseEntity<AgreementDto> createAgreement(@RequestBody AgreementCommand agreementCommand){
        return new ResponseEntity<AgreementDto>(agreementService.saveAgreement(agreementCommand), HttpStatus.OK);
    }
    @PostMapping("/agreement/pdf")
    public @ResponseBody
    byte[] createAgreementPdf(@RequestBody AgreementCommand agreementCommand){
        return agreementService.saveAgreement(agreementCommand).getContent();
    }
}
