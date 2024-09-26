package com.assessment.sofka.mscorepersona.communication.rest;

import com.assessment.sofka.mscorepersona.messages.request.CustomerReqDTO;
import com.assessment.sofka.mscorepersona.messages.request.CustomerUpdateReqDTO;
import com.assessment.sofka.mscorepersona.messages.response.CustomerRespDTO;
import com.assessment.sofka.mscorepersona.service.ICustomerService;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @RequestMapping(consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public GenericResponseDTO<String> saveCustomer(
            @RequestBody GenericRequestDTO<CustomerReqDTO> requestDTO) {
        return customerService.saveCustomer(requestDTO);
    }

    @RequestMapping(path = "/remove", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public GenericResponseDTO<String> deleteCustomer(
            @RequestBody GenericRequestDTO<String> requestDTO) {
        return customerService.deleteCustomer(requestDTO);
    }

    @RequestMapping(path = "/update", consumes = "application/json", produces = "application/json", method = RequestMethod.PUT)
    public GenericResponseDTO<String> updateCustomer(
            @RequestBody GenericRequestDTO<CustomerUpdateReqDTO> requestDTO) {
        return customerService.updateCustomer(requestDTO);
    }

    @RequestMapping(path = "/list", produces = "application/json", method = RequestMethod.GET)
    public GenericResponseDTO<List<CustomerRespDTO>> findCustomers() {
        return customerService.findAllCustomer();
    }
}
