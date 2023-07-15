package space.alen.corona.Interfaces;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.ZoneId;

import org.json.JSONObject;

public interface IApi {
    public JSONObject getDataFromAPI() throws IOException;
    public void storeDataAsJsonFile(JSONObject data); 
    public boolean deleteFileIfOlderThanPreviousDay();
}
