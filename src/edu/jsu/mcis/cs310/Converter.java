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
        
        Consider the following CSV data, a portion of a database of
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

<<<<<<< HEAD
            // CREATE JSON ARRAYS TO HOLD SEPARATE DATA CATEGORIES
=======
            // INSERT YOUR CODE HERE
>>>>>>> d38fc93ac2adb30edff7613cd35e2a477d58e142
            JsonArray jsonProdNum = new JsonArray();  // Stores the "ProdNum" values separately
            JsonArray jsonColHeadings = new JsonArray();  // Stores column headings
            JsonArray jsonData = new JsonArray();  // Stores the main data as nested arrays
            
<<<<<<< HEAD
            // USE A LINKED HASHMAP TO MAINTAIN ORDER OF INSERTION OF KEY-VALUE PAIRS
            LinkedHashMap<String, JsonArray> jsonRecord = new LinkedHashMap<>();
            
            // CREATE CSV READER TO PROCESS THE CSV STRING
            CSVReader reader = new CSVReader(new StringReader(csvString));
            
            // READ ALL LINES FROM CSV INTO A LIST
            List<String[]> csvList = reader.readAll(); 
            
            // CREATE AN ITERATOR TO GO THROUGH EACH CSV ROW
            Iterator<String[]> iterator = csvList.iterator();
            
            // SEPARATE THE HEADER LINE AND ADD TO JSON ARRAY
            String[] csvHeadings = iterator.next();
            jsonColHeadings.addAll(Arrays.asList(csvHeadings));
            
            // PROCESS DATA LINES AND ADD TO JSON ARRAYS
=======
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
>>>>>>> d38fc93ac2adb30edff7613cd35e2a477d58e142
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
<<<<<<< HEAD
            // FILL THE JSON OBJECT WITH THE JSON ARRAYS
=======
            // Filling JsonObject with the JsonArrays
>>>>>>> d38fc93ac2adb30edff7613cd35e2a477d58e142
            jsonRecord.put("ProdNums", jsonProdNum);
            jsonRecord.put("ColHeadings", jsonColHeadings);
            jsonRecord.put("Data", jsonData);
            
<<<<<<< HEAD
            // SERIALIZE JSON OBJECT TO A STRING
=======
            // Serializing JsonObject to a string
>>>>>>> d38fc93ac2adb30edff7613cd35e2a477d58e142
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

<<<<<<< HEAD
            // PARSE THE JSON STRING TO GET A JSON OBJECT
            JsonObject json = Jsoner.deserialize(jsonString, new JsonObject());
            
            // EXTRACT KEY JSON ARRAYS FROM THE JSON OBJECT
            JsonArray prodnums = (JsonArray) json.get("ProdNums");
            JsonArray colHeadings = (JsonArray) json.get("ColHeadings");
            JsonArray data = (JsonArray) json.get("Data");
            
            // USE STRINGWRITER AND CSVWRITER TO CONVERT JSON TO CSV FORMAT
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");
            
            /*  HANDLE COLUMN HEADINGS */
            
            // CONVERT THE JSON COLUMN HEADERS INTO A LIST
=======
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
>>>>>>> d38fc93ac2adb30edff7613cd35e2a477d58e142
            List<String> csvColHeadingsList = new ArrayList<>();
            for (int i = 0; i < colHeadings.size(); i++) {
                csvColHeadingsList.add(colHeadings.getString(i));
            }
            
<<<<<<< HEAD
            // CONVERT THE LIST TO A STRING ARRAY AND WRITE THE HEADER ROW TO CSV
            int colHeadingsSize = csvColHeadingsList.size();
            String[] csvColHeadings = csvColHeadingsList.toArray(new String[colHeadingsSize]);
            csvWriter.writeNext(csvColHeadings);  // WRITE COLUMN HEADINGS TO CSV
            
            /* HANDLING THE MAIN DATA */
            
            // REMOVE "ProdNum" FROM THE COLUMN HEADINGS SINCE IT IS SEPARATE
            colHeadings.remove("ProdNum");
            
            /* 
            COMBINE THE PRODNUM AND DATA JSON ARRAYS, AND WRITE TO CSV
            */
            for (int i = 0; i < prodnums.size(); i++) {
=======
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
>>>>>>> d38fc93ac2adb30edff7613cd35e2a477d58e142
                List<String> csvData = (List<String>) data.get(i);
                List<String> csvRecordList = new ArrayList<>();
                csvRecordList.add(prodnums.getString(i));
                
<<<<<<< HEAD
                for(int j = 0; j < csvData.size(); j++){
                    /* 
                    CHECK IF THE COLUMN IS "Season" OR "Episode" (WHICH ARE INTEGERS IN JSON)
                    */
                    if (colHeadings.get(j).equals("Season")){
                        // CONVERT THE INTEGER TO A STRING AND ADD TO THE ROW
                        csvRecordList.add(String.valueOf(csvData.get(j)));
                    }
                    else if (colHeadings.get(j).equals("Episode")){
                        // CONVERT INTEGER TO A 2-DIGIT STRING (E.G., 1 -> "01") FOR CSV FORMAT
=======
                
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
>>>>>>> d38fc93ac2adb30edff7613cd35e2a477d58e142
                        Integer num = Integer.valueOf(String.valueOf(csvData.get(j)));
                        String numString = String.format("%02d", num);
                        csvRecordList.add(numString);
                    }
                    else{
                        csvRecordList.add(csvData.get(j));
                    }
                }
                
<<<<<<< HEAD
                // CONVERT THE LIST TO A STRING ARRAY AND WRITE THE ROW TO CSV
=======
                // convert the list to a string array and write the row to CSV
>>>>>>> d38fc93ac2adb30edff7613cd35e2a477d58e142
                String[] csvRecord = csvRecordList.toArray(new String[colHeadingsSize]);
                csvWriter.writeNext(csvRecord);   
            }
            
<<<<<<< HEAD
            // RETURN THE FINAL CSV STRING
=======
>>>>>>> d38fc93ac2adb30edff7613cd35e2a477d58e142
            String csvString = writer.toString();

            return csvString.trim();  
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result.trim();

    }

<<<<<<< HEAD
}
=======
}
>>>>>>> d38fc93ac2adb30edff7613cd35e2a477d58e142
