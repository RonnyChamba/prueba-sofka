package com.assessment.sofka.mscoretransaction.services;

import com.assessment.sofka.mscoretransaction.messages.response.ReportRespDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;

import java.util.List;

public interface IReportService {

    GenericResponseDTO<List<ReportRespDTO>> generateReport(String identificationCustomer,
                                                           String dateStart,
                                                           String dateEnd);
}
