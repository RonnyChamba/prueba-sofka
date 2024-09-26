package com.assessment.sofka.mscoretransaction.util;

import java.util.Map;

public class ConstantsTransaction {

    public static final String SUCCESS_CODE = "200";
    public static final String ERROR_CODE = "500";
    public static final String SUCCESS_STATUS = "OK";
    public static final String ERROR_STATUS = "ERROR";


    public static final String SUCCESS_MESSAGE = "Operation was successful";

    public static final String ERROR_MESSAGE = "An error occurred";

   public static final  Map<String, String> OPERATION_TYPE = Map.of("DEPOSIT", "DEPOSIT", "WITHDRAW", "WITHDRAW");
}
