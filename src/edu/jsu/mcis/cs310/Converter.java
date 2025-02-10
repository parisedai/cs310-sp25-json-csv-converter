package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class Converter {

    /*
        
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
        {
            "ProdNums": [
                "6149-02",
                "6149-03"
            ],
            "ColHeadings": [
                "ProdNum",
                "Title",
                "Season",
                "Episode",
                "Stardate",
                "OriginalAirdate",
                "RemasteredAirdate"
            ],
            "Data": [
                [
                    "Where No Man Has Gone Before",
                    1,
                    1,
                    "1312.4 - 1313.8",
                    "9/22/1966",
                    "1/20/2007"
                ],
                [
                    "The Corbomite Maneuver",
                    1,
                    2,
                    "1512.2 - 1514.1",
                    "11/10/1966",
                    "12/9/2006"
                ]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
        
    */

    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {

        String result = "{}"; // default return value; replace later!

        try {

            // INSERT YOUR CODE HERE
            JsonArray jsonProdNum = new JsonArray();  // Stores the "ProdNum" values separately
            JsonArray jsonColHeadings = new JsonArray();  // Stores column headings
            JsonArray jsonData = new JsonArray();  // Stores the main data as nested arrays
            
            // Using LinkedHashMap instead of JsonObject to maintain key insertion order
            LinkedHashMap<String, JsonArray> jsonRecord = new LinkedHashMap<>();
            
            // Reading csvString and putting it in a list
            CSVReader reader = new CSVReader(new StringReader(csvString));
            
            // Reads all lines from CSV into a list
            List<String[]> csvList = reader.readAll(); 
            
            // Iterating the list
            Iterator<String[]> iterator = csvList.iterator();
            
            // Isolating header line and adding it to JsonArray
            String[] csvHeadings = iterator.next();
            jsonColHeadings.addAll(Arrays.asList(csvHeadings));
            
            // Main loop - putting rest of data in appropriate JsonArrays
            while(iterator.hasNext()){
                JsonArray csvData = new JsonArray();
                String[] csvRecords = iterator.next();
                for (int i = 0; i < csvRecords.length; i++){
                    if (csvHeadings[i].equals("ProdNum")){
                        jsonProdNum.add(csvRecords[i]);
                    }
                    else if (csvHeadings[i].equals("Season") || csvHeadings[i].equals("Episode")){
                        csvData.add(Integer.valueOf(csvRecords[i]));
                    }
                    else{
                        csvData.add(csvRecords[i]);
                    }
                }
                jsonData.add(csvData);
            }
            // Filling JsonObject with the JsonArrays
            jsonRecord.put("ProdNums", jsonProdNum);
            jsonRecord.put("ColHeadings", jsonColHeadings);
            jsonRecord.put("Data", jsonData);
            
            // Serializing JsonObject to a string
            String jsonString = Jsoner.serialize(jsonRecord);
            
            return jsonString;

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result.trim();

    }

    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {

        String result = ""; // default return value; replace later!

        try {

            // INSERT YOUR CODE HERE
            JsonObject json = Jsoner.deserialize(jsonString, new JsonObject());
            
            // Variable initialization
            // extract key JSON arrays from the JsonObject
            JsonArray prodnums = (JsonArray) json.get("ProdNums");
            JsonArray colHeadings = (JsonArray) json.get("ColHeadings");
            JsonArray data = (JsonArray) json.get("Data");
            
            // StringWriter and CSVWriter to write a csv string
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");
            
            /*  COL HEADINGS */ 
            
            // convert the JSON column headers into a List<String>
            List<String> csvColHeadingsList = new ArrayList<>();
            for (int i = 0; i < colHeadings.size(); i++) {
                csvColHeadingsList.add(colHeadings.getString(i));
            }
            
            // converting List<String> to String[] and writing the array with csvWriter
            int colHeadingsSize = csvColHeadingsList.size();
            String[] csvColHeadings = csvColHeadingsList.toArray(new String[colHeadingsSize]);
            csvWriter.writeNext(csvColHeadings);  // write the column headers
            
            /*----- HANDLING REMAINING DATA-- */
            
            // removing "ProdNum" from colHeadings since ProdNum is separated from rest of data
            colHeadings.remove("ProdNum");
            
            /* 
            combining prodnum and data JsonArrays into List<String> and converting
            the List into a string array one line at a time, then writing that line
            with csvWriter.
            */
            for (int i = 0; i < prodnums.size(); i++) {
                // Putting all contents of prodnums JsonArray into List<String>
                List<String> csvData = (List<String>) data.get(i);
                List<String> csvRecordList = new ArrayList<>();
                csvRecordList.add(prodnums.getString(i));
                
                
                for(int j = 0; j < csvData.size(); j++){
                    /* 
                     check if the column is "Season" or "Episode" (which are stored as integers in JSON)
                    */
                    
                    if (colHeadings.get(j).equals("Season")){
                        /*
                        convert integer to string and add to the row
                        */
                        csvRecordList.add(String.valueOf(csvData.get(j)));
                    }
                    else if (colHeadings.get(j).equals("Episode")){
                        
                        // convert integer to a 2-digit string (like, 1 -> "01") for CSV formatting
                        Integer num = Integer.valueOf(String.valueOf(csvData.get(j)));
                        String numString = String.format("%02d", num);
                        csvRecordList.add(numString);
                    }
                    else{
                        csvRecordList.add(csvData.get(j));
                    }
                }
                
                // convert the list to a string array and write the row to CSV
                String[] csvRecord = csvRecordList.toArray(new String[colHeadingsSize]);
                csvWriter.writeNext(csvRecord);   
            }
            
            String csvString = writer.toString();

            return csvString.trim();  
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result.trim();

    }

}