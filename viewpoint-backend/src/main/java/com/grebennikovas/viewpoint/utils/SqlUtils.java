package com.grebennikovas.viewpoint.utils;

import com.grebennikovas.viewpoint.datasets.parameter.Parameter;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlUtils {
    private SqlUtils() {
    }

    public static String convertToString(List<String> list) {
        return String.join(", ", list);
    }

    public static String prepareParamValue(String parameterType, String paramValue) {
        String preparedParamValue;
        if (parameterType.equals("String") || parameterType.equals("Timestamp")) {
            preparedParamValue = String.format("'%s'", paramValue.trim());
        } else {
            preparedParamValue = String.format("%s", paramValue.trim());
        }
        return preparedParamValue;
    }

    public static String setParameters(String sqlQuery, Map<String,String> paramValues) {
        Pattern pattern = Pattern.compile("\\s*\\{:(\\w+)}[^\\r^\\n\\s]*");
        Matcher matcher = pattern.matcher(sqlQuery);

        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String parameterName = matcher.group(1);
            if (paramValues.containsKey(parameterName)) {
                String parameterValue = " " + paramValues.get(parameterName) + " ";
                matcher.appendReplacement(result, Matcher.quoteReplacement(parameterValue));
            }

        }

        matcher.appendTail(result);
        return result.toString();
    }
}
