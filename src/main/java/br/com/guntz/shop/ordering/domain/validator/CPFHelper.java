package br.com.guntz.shop.ordering.domain.validator;

public class CPFHelper {

    public static Boolean isValid(String cpf) {

        //Remove mask
        String values = convertToUnmaskedValue(cpf);

        if (!isNumeric(values)) {
            return false;
        }

        if (values.length() != 11) {
            return false;
        }

        //Calculate the first digit of the CPF
        Integer calculateFirstDigit = getCalculateFirstDigit(values);

        //Calculate the second digit of the CPF
        Integer calculateSecondDigit = getCalculateSecondDigit(values, calculateFirstDigit);

        if (Character.getNumericValue(values.charAt(9)) != calculateFirstDigit) {
            return false;
        }

        if (Character.getNumericValue(values.charAt(10)) != calculateSecondDigit) {
            return false;
        }

        return true;
    }

    private static Integer getCalculateFirstDigit(String values) {
        Integer sumValues = Character.getNumericValue(values.charAt(0)) * 10 +
                            Character.getNumericValue(values.charAt(1)) * 9 +
                            Character.getNumericValue(values.charAt(2)) * 8 +
                            Character.getNumericValue(values.charAt(3)) * 7 +
                            Character.getNumericValue(values.charAt(4)) * 6 +
                            Character.getNumericValue(values.charAt(5)) * 5 +
                            Character.getNumericValue(values.charAt(6)) * 4 +
                            Character.getNumericValue(values.charAt(7)) * 3 +
                            Character.getNumericValue(values.charAt(8)) * 2;

        Integer valueOfRemainingDivision = sumValues % 11;

        if (valueOfRemainingDivision >= 2) {
            return 11 - valueOfRemainingDivision;
        }

        return 0;
    }

    public static Integer getCalculateSecondDigit(String values, Integer calculateFirstDigit) {
        Integer sumValues = Character.getNumericValue(values.charAt(0)) * 11 +
                            Character.getNumericValue(values.charAt(1)) * 10 +
                            Character.getNumericValue(values.charAt(2)) * 9 +
                            Character.getNumericValue(values.charAt(3)) * 8 +
                            Character.getNumericValue(values.charAt(4)) * 7 +
                            Character.getNumericValue(values.charAt(5)) * 6 +
                            Character.getNumericValue(values.charAt(6)) * 5 +
                            Character.getNumericValue(values.charAt(7)) * 4 +
                            Character.getNumericValue(values.charAt(8)) * 3 +
                            calculateFirstDigit * 2;


        Integer valueOfRemainingDivision = sumValues % 11;

        if (valueOfRemainingDivision >= 2) {
            return 11 - valueOfRemainingDivision;
        }

        return 0;
    }


    public static String convertToUnmaskedValue(String cpf) {
        return cpf
                .replaceAll("\\.", "")
                .replace("-", "");
    }

    public static Boolean isNumeric(String value) {
        return value
                .matches("\\d+");
    }

}
