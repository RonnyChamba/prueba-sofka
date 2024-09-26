package com.assessment.sofka.mscorepersona.service.impl;

import com.assessment.sofka.mscorepersona.exeption.GenericException;
import com.assessment.sofka.mscorepersona.entity.Customer;
import com.assessment.sofka.mscorepersona.messages.request.CustomerReqDTO;
import com.assessment.sofka.mscorepersona.messages.request.CustomerUpdateReqDTO;
import com.assessment.sofka.mscorepersona.messages.response.CustomerRespDTO;
import com.assessment.sofka.mscorepersona.repository.ICustomerRepository;
import com.assessment.sofka.mscorepersona.service.ICustomerService;
import com.assessment.sofka.mscorepersona.util.CommonValidator;
import com.assessment.sofka.mscorepersona.vo.UpdateCustomerVO;
import com.dependency.mscore.grpc.dto.request.GenericRequestDTO;
import com.dependency.mscore.grpc.dto.response.GenericResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {


    private final ICustomerRepository customerRepository;

    public static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Override
    @Transactional
    public GenericResponseDTO<String> saveCustomer(GenericRequestDTO<CustomerReqDTO> requestDTO) {

        try {
            LOG.info("Request to save customer: {}", requestDTO);
            validateRequestPayload(requestDTO);
            validateFieldPayload(requestDTO, Arrays.asList("fullName", "identification", "age", "gender", "phone", "password"));

            if (customerRepository.existsByIdentification(requestDTO.getPayload().getIdentification())) {
                throw new GenericException("Customer already exists with the identification: " + requestDTO.getPayload().getIdentification());
            }

            Customer customer = CommonValidator.objectMapping(requestDTO.getPayload(), Customer.class);
            customer.setStatusRecord("Active");
            customer = customerRepository.save(customer);

            LOG.info("Customer saved: {}", customer.getPersonId());

            return GenericResponseDTO.<String>builder()
                    .message("Customer saved successfully")
                    .status("OK")
                    .code(200)
                    .payload(customer.getPersonId() + "")
                    .build();

        } catch (Exception e) {

            String message = e.getMessage();

            if (e instanceof GenericException genericException) {
                message = genericException.getUserMessage();
            }
            return GenericResponseDTO.<String>builder()
                    .message(message)
                    .status("ERROR")
                    .code(500)
                    .build();
        }
    }

    @Override
    @Transactional
    public GenericResponseDTO<String> deleteCustomer(GenericRequestDTO<String> requestDTO) {

        try {
            LOG.info("Request to delete customer: {}", requestDTO);
            CommonValidator.validateFieldObjectFromTemplate(requestDTO,
                    List.of("payload"));

            Customer customer = customerRepository.findByIdentificationAndStatusRecord(requestDTO.getPayload(), "Active")
                    .orElseThrow(() -> new GenericException("Customer not found with id: " + requestDTO.getPayload() + " with status Active"));

            customer.setStatusRecord("Deleted");
            customer.setRemovedAt(new Date());
            customer = customerRepository.save(customer);
            LOG.info("Customer deleted: {}", customer.getPersonId());

            return GenericResponseDTO.<String>builder()
                    .message("Customer deleted successfully")
                    .status("OK")
                    .code(200)
                    .build();

        } catch (Exception e) {

            String message = e.getMessage();

            if (e instanceof GenericException genericException) {
                message = genericException.getUserMessage();
            }
            return GenericResponseDTO.<String>builder()
                    .message(message)
                    .status("ERROR")
                    .code(500)
                    .build();
        }
    }

    @Override
    @Transactional
    public GenericResponseDTO<String> updateCustomer(GenericRequestDTO<CustomerUpdateReqDTO> requestDTO) {
        try {
            LOG.info("Request to delete customer: {}", requestDTO);
            CommonValidator.validateFieldObjectFromTemplate(requestDTO,
                    List.of("payload"));
            validateFieldPayload(requestDTO, List.of("identification", "dataToUpdate"));

            UpdateCustomerVO dataToUpdate = requestDTO.getPayload().getDataToUpdate();

            CommonValidator.validateFieldObjectFromTemplate(dataToUpdate,
                    List.of("fullName", "age", "gender", "phone", "password", "status"));


            Customer customer = customerRepository.findByIdentificationAndStatusRecord(requestDTO.getPayload().getIdentification(), "Active")
                    .orElseThrow(() -> new GenericException("Customer not found with id: " + requestDTO.getPayload().getIdentification() + " with status Active"));

            setValues(customer, dataToUpdate);

            customer = customerRepository.save(customer);
            LOG.info("Customer updated: {}", customer.getPersonId());

            return GenericResponseDTO.<String>builder()
                    .message("Customer updated successfully")
                    .status("OK")
                    .payload(customer.getPersonId() + "")
                    .code(200)
                    .build();

        } catch (Exception e) {

            String message = e.getMessage();

            if (e instanceof GenericException genericException) {
                message = genericException.getUserMessage();
            }
            return GenericResponseDTO.<String>builder()
                    .message(message)
                    .status("ERROR")
                    .code(500)
                    .build();
        }
    }

    @Override
    public GenericResponseDTO<List<CustomerRespDTO>> findAllCustomer() {
        try {

            List<CustomerRespDTO> customers = customerRepository.findAllByStatusRecord("Active")
                    .stream()
                    .map(customer -> CommonValidator.objectMapping(customer, CustomerRespDTO.class))
                    .toList();

            return GenericResponseDTO.<List<CustomerRespDTO>>builder()
                    .message("Customers found successfully")
                    .status("OK")
                    .payload(customers)
                    .code(200)
                    .build();

        } catch (Exception e) {

            String message = e.getMessage();

            if (e instanceof GenericException genericException) {
                message = genericException.getUserMessage();
            }
            return GenericResponseDTO.<List<CustomerRespDTO>>builder()
                    .message(message)
                    .status("ERROR")
                    .code(500)
                    .build();
        }
    }

    @Override
    public CustomerRespDTO findByAIdentification(GenericRequestDTO<String> requestDTO) {
        try {

            Customer customer = customerRepository.findByIdentificationAndStatusRecord(requestDTO.getPayload(), "Active")
                    .orElseThrow(() -> new GenericException("Customer not found with id: " + requestDTO.getPayload() + " with status Active"));


//            CustomerRespDTO customerRespDTO = CommonValidator.objectMapping(customer, CustomerRespDTO.class);
            return CommonValidator.objectMapping(customer, CustomerRespDTO.class);

//            return GenericResponseDTO.<CustomerRespDTO>builder()
//                    .message("Customers found successfully")
//                    .status("OK")
//                    .payload(customerRespDTO)
//                    .code(200)
//                    .build();

        } catch (Exception e) {

            String message = e.getMessage();

            if (e instanceof GenericException genericException) {
                message = genericException.getUserMessage();
            }
            throw new GenericException(message);
//            return GenericResponseDTO.<CustomerRespDTO>builder()
//                    .message(message)
//                    .status("ERROR")
//                    .code(500)
//                    .build();
        }
    }

    private void setValues(Customer customer, UpdateCustomerVO dataToUpdate) {
        customer.setFullName(dataToUpdate.getFullName());
        customer.setAge(dataToUpdate.getAge());
        customer.setGender(dataToUpdate.getGender());
        customer.setAddress(dataToUpdate.getAddress());
        customer.setPhone(dataToUpdate.getPhone());
        customer.setStatus(dataToUpdate.getStatus());
        customer.setPassword(dataToUpdate.getPassword());
    }

    private <T> void validateRequestPayload(GenericRequestDTO<T> requestDTO) {
        CommonValidator.validateFieldObjectFromTemplate(requestDTO,
                List.of("payload"));
    }

    private <T> void validateFieldPayload(GenericRequestDTO<T> requestDTO, List<String> fields) {
        CommonValidator.validateFieldObjectFromTemplate(requestDTO.getPayload(), fields);
    }
}
