package space.alen.corona.Services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScannerService {

    //Metodo que se encarga de que el valor del año sea un numero de 4 digitos
    private static boolean isFourDigitNumber(String str) {
        Pattern pattern = Pattern.compile("^\\d{4}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    //Metodo que se encarga de que el valor del pais sea un string de 2 digitos
    private static boolean isCountryCode(String str) {
        String countryCode = str.toUpperCase();
        return countryCode.equals("GB") || countryCode.equals("US");
    }

    //Metodo que se llama para asegurarse que los argumentos sean validos. Llama a los otros dos metodos de la clase
    public static boolean isArgsValid(String [] args) {
        if(args.length!=2){
            return false;
        }
        if(!isCountryCode(args[0]) || !isFourDigitNumber(args[1])){
            return false;
        }
        return true;
    }

}
