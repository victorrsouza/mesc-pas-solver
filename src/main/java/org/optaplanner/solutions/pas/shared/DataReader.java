package org.optaplanner.solutions.pas.shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.optaplanner.solutions.pas.domain.AllocationPercentage;
import org.optaplanner.solutions.pas.domain.PlanningItem;
import org.optaplanner.solutions.pas.domain.ResourceAllocation;
import org.optaplanner.solutions.pas.domain.FinancialSource;

import org.json.*;

public class DataReader {

    private ProblemContent problem;
    private File directory;    
    private Scanner scanner;
    
    public DataReader() {
        this.problem = new ProblemContent();
        this.directory = new File(".");
    }

    public ProblemContent readFromTxt(String fileName) {

        String filePath = this.directory.getAbsolutePath() + "/src/main/resources/org/optaplanner/solutions/pas/data/" + fileName + ".txt";
        File file = new File(filePath);
        
        String inputType = "";

        try {
            scanner = new Scanner(file);    

            for (double i = 0; i <= 100; i = i + 0.1) {
                this.problem.addPercentage(new AllocationPercentage(new BigDecimal(i)));
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.substring(0,1).equals("f")) {
                    inputType = "f";
                }
                else if (line.substring(0,1).equals("m")) {
                    inputType = "m";
                }
                else if (inputType.equals("f")) {
                    String[] fonteAttr = line.split(":");
                    int fonteId = Integer.parseInt(fonteAttr[0]);
                    double fonteValue = Double.parseDouble(fonteAttr[1]);
                    this.problem.addSource(new FinancialSource(fonteId, fonteValue));
                }
                else if (inputType.equals("m")) {
                    String[] metaAttr = line.split(":");
                    int metaId = Integer.parseInt(metaAttr[0]);

                    String[] strList = metaAttr[1].split(",");
                    List<Integer> acceptableCodes = new ArrayList<Integer>();
                    for(String s : strList) acceptableCodes.add(Integer.valueOf(s.trim()));

                    double metaValue = Double.parseDouble(metaAttr[2]);

                    PlanningItem planningItem = new PlanningItem(metaId, metaValue, acceptableCodes);
                    //AllocationPercentage allocationPercentage = this.problem.getAllocationPercentageList().get(0);

                    for (int i = 0; i < strList.length; i++) {
                        FinancialSource source = this.getSourceByCode(this.problem.getSourceList(), strList[i]);
                        ResourceAllocation resourceAllocation = new ResourceAllocation(i + 1, source, planningItem);
                        this.problem.addResourceAllocation(resourceAllocation);
                    }
                    this.problem.addPlanningItem(planningItem);
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not founded");
        }

        return this.problem;
    }

    public ProblemContent readFromJson(String fileName) {
        String fileContent = jsonFileToString(fileName);
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(fileContent);

            JSONArray sourcesArray = jsonObj.getJSONArray("sources");
            JSONArray itemsArray = jsonObj.getJSONArray("items");
            double percentageScale = jsonObj.getDouble("percentageScale");

            for (double i = 0; i <= 100; i = i + percentageScale) {
                this.problem.addPercentage(new AllocationPercentage(new BigDecimal(i)));
            }

            for (int i = 0; i < sourcesArray.length(); i++) {
                JSONObject jsonSource = sourcesArray.getJSONObject(i);
                int code = jsonSource.getInt("code");
                double availableResource = jsonSource.getDouble("availableResource");
                FinancialSource source = new FinancialSource(code, availableResource);
                this.problem.addSource(source);
            }

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jsonItem = itemsArray.getJSONObject(i);
                int id = jsonItem.getInt("id");
                double estimatedResource = jsonItem.getDouble("estimatedResource");
                JSONArray acceptableCodesArray = jsonItem.getJSONArray("acceptableCodes");
                List<Integer> acceptableCodes = new ArrayList<Integer>();
                for(int j = 0; j < acceptableCodesArray.length(); j++) {
                    int code = acceptableCodesArray.getInt(j);
                    acceptableCodes.add(code);
                }

                PlanningItem item = new PlanningItem(id, estimatedResource, acceptableCodes);
                this.problem.addPlanningItem(item);

                for(int j = 0; j < acceptableCodesArray.length(); j++) {
                    String code = acceptableCodesArray.getString(j);
                    FinancialSource source = this.getSourceByCode(this.problem.getSourceList(), code);
                    ResourceAllocation resourceAllocation = new ResourceAllocation(j + 1, source, item);
                    this.problem.addResourceAllocation(resourceAllocation);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return this.problem;
    }

    private String jsonFileToString(String fileName) {
        String filePath = this.directory.getAbsolutePath() + "/src/main/resources/org/optaplanner/solutions/pas/data/" + fileName + ".json";
        String result = "{}";
        try {
            result = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.println("Error to load JSON file");
        }

        return result;
    }

    private FinancialSource getSourceByCode(List<FinancialSource> sources, String code) {
        for (FinancialSource s : sources) {
            if (Integer.parseInt(code.trim()) == s.getCode()) {
                return s;
            }
        }
        return null;
    }
}