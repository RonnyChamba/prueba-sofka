package com.assessment.sofka.mscoretransaction.communication.rest;


import com.assessment.sofka.mscoretransaction.messages.response.ReportRespDTO;
import com.assessment.sofka.mscoretransaction.services.IReportService;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReportController {

    private final IReportService reportService;

    @GetMapping(produces = "application/json")
    public GenericResponseDTO<List<ReportRespDTO>> findAccountByCustomers(
            @RequestParam(name = "cliente") String identificationCustomer,
            @RequestParam(name = "dateFrom") String dateFrom,
            @RequestParam(name = "dateTo") String dateTo) {

        System.out.println("identificationCustomer: " + identificationCustomer);
        return reportService.generateReport(identificationCustomer, dateFrom, dateTo);
    }

}
