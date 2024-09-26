package com.assessment.sofka.mscoretransaction.util;

import com.assessment.sofka.mscoretransaction.enums.DateFormatEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date convertToDate(String currentDate, DateFormatEnum formatDateEnum) {
        try {
            return currentDate == null ? null : (new SimpleDateFormat(formatDateEnum.getFormat())).parse(currentDate);
        } catch (Exception var3) {
            return null;
        }
    }

    public static String convertDateToString(Date currentDate, DateFormatEnum formatDateEnum) {
        try {
            return currentDate == null ? null : (new SimpleDateFormat(formatDateEnum.getFormat())).format(currentDate);
        } catch (Exception var3) {
            return null;
        }
    }

}
