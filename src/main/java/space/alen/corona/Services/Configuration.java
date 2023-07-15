package space.alen.corona.Services;

public class Configuration {

    public static String englandApi = "https://api.coronavirus.data.gov.uk/v1/data?filters=areaType=overview&areaName=United Kingdom&structure=";
    public static String englandApiStructure = "{\"date\":\"date\",\"newCasesByPublishDate\":\"newCasesByPublishDate\"}";
    
    public static String usaApi = "https://api.covidtracking.com/v2/us/daily.json";
}
