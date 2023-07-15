package space.alen.corona.Services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import space.alen.corona.Interfaces.IApi;

public class ApiUsaService implements IApi{

    private static String filePath = "testUsa.json";

    //Metodo que realiza una llamada a una api
    @Override
    public JSONObject getDataFromAPI() throws IOException {
        URL url = new URL(Configuration.usaApi);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return new JSONObject(response.toString());
        } else {
            throw new IOException("Failed to retrieve data from API. Response code: " + responseCode);
        }
    }

    @Override
    public void storeDataAsJsonFile(JSONObject data) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(data.toString(4));
            System.out.println("Data stored in " + filePath + " successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Borramos el json si es demasiado antiguo. Si no se saca la informacion del json y asi no se hace una llamada inncesaria a la api
    @Override
    public boolean deleteFileIfOlderThanPreviousDay() {
        File file = new File(filePath);

        if (file.exists()) {
            try {
                BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                FileTime creationTime = attributes.creationTime();
                LocalDate fileCreationDate = creationTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate previousDay = LocalDate.now().minusDays(1);

                if (fileCreationDate.isBefore(previousDay)) {
                    boolean deleted = file.delete();
                    if (deleted) {
                        System.out.println("File deleted: " + filePath);
                        return true;
                    } else {
                        System.out.println("Failed to delete file: " + filePath);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        return false;
    }

    //Se lee el archivo json y se imprime la informacion
    public void printJson(int chosenYear) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(jsonContent);
            JSONArray dataArray = jsonObject.getJSONArray("data");

            Map<YearMonth, Integer> monthlyCounts = new HashMap<>();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                String dateString = dataObject.getString("date");
                JSONObject casesObject = dataObject.getJSONObject("cases");
                JSONObject totalObject = casesObject.getJSONObject("total");
                JSONObject calculatedObject = totalObject.getJSONObject("calculated");
                
                int changeFromPriorDay = getValue(calculatedObject);

                LocalDate date = LocalDate.parse(dateString, dateFormatter);
                if (date.getYear() == chosenYear) {
                    
                    YearMonth yearMonth = YearMonth.from(date);
                    monthlyCounts.put(yearMonth, monthlyCounts.getOrDefault(yearMonth, 0) + changeFromPriorDay);
                }
            }

            for (Map.Entry<YearMonth, Integer> entry : monthlyCounts.entrySet()) {
                YearMonth yearMonth = entry.getKey();
                int count = entry.getValue();
                System.out.println(yearMonth.getMonth() + " " + yearMonth.getYear() + ": " + count + " | "+printTable(count));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Metodo para asegurar que el valor no sea nulo
    private int getValue(JSONObject calculatedObject) {
        if(calculatedObject.isNull("change_from_prior_day")){
           return 0;             
        }
        return calculatedObject.getInt("change_from_prior_day");
    }

    //Metodo para imprimir una tabla
    private String printTable(int count){
        int result=count/50000;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < result; i++) {
            stringBuilder.append("\u2587");
        }
        
        return stringBuilder.toString();
    }
}
