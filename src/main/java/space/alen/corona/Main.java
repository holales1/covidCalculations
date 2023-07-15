package space.alen.corona;

import java.io.IOException;

import org.json.JSONObject;

import space.alen.corona.Services.ApiEnglandService;
import space.alen.corona.Services.ApiUsaService;
import space.alen.corona.Services.ScannerService;

public class Main {

    public static void main(String[] args) {
        //Chekea que los argumentos sean validos
        String countryString;
        int date;
        if(!ScannerService.isArgsValid(args)){
            System.out.println("Arguments not valid");
            System.exit(0);
        }
        countryString = args[0];
        date = Integer.parseInt(args[1]);
 
        try {
            //Se inicializa el servicio necesario y se hacen las llamadas a los metodos correspondientes
             if(countryString.equals("US")){
                ApiUsaService apiService = new ApiUsaService();
                if(!apiService.deleteFileIfOlderThanPreviousDay()){
                    JSONObject response = apiService.getDataFromAPI();
                    apiService.storeDataAsJsonFile(response);
                }
                apiService.printJson(date);
            }else if(countryString.equals("GB")){
                ApiEnglandService apiService = new ApiEnglandService();
                if(!apiService.deleteFileIfOlderThanPreviousDay()){
                    JSONObject response = apiService.getDataFromAPI();
                    apiService.storeDataAsJsonFile(response);
                }
                apiService.printJson(date);
            }else{
                System.out.println("There is no info about that country");
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
    }
}
