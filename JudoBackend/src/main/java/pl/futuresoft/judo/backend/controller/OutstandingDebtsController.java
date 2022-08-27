package pl.futuresoft.judo.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.futuresoft.judo.backend.command.OutstandingDebtsCommand;
import pl.futuresoft.judo.backend.dto.OutstandingDebtsDto;
import pl.futuresoft.judo.backend.service.OutstandingDebtsSchedulerService;

import java.util.List;

@RestController
public class OutstandingDebtsController {

    private final OutstandingDebtsSchedulerService outstandingDebtsSchedulerService;

    public OutstandingDebtsController(OutstandingDebtsSchedulerService outstandingDebtsSchedulerService) {
        this.outstandingDebtsSchedulerService = outstandingDebtsSchedulerService;
    }

    @GetMapping("/outstandingDebts")
    public ResponseEntity<List<OutstandingDebtsDto>> outstandingDebtsDtoListForAllClub() {
        return  new ResponseEntity<List<OutstandingDebtsDto>>(outstandingDebtsSchedulerService.outstandingDebtsDtoList(), HttpStatus.OK);
    }

    @PutMapping("/outstandingDebts/{debtsId}")
    @PreAuthorize("hasAuthority('ROLE_a')")
    public ResponseEntity<OutstandingDebtsDto> editOutstandingDebts(@RequestBody OutstandingDebtsCommand outstandingDebtsCommand, @PathVariable Integer debtsId) {
        return new ResponseEntity<OutstandingDebtsDto>(outstandingDebtsSchedulerService.editOutstandingDebts(outstandingDebtsCommand, debtsId), HttpStatus.OK);
    }
}
