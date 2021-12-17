import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Main {

	//main for reading file, json to array operation,
    //array sorting, creating objects and write output 
    public static void main(String[] args) throws IOException {		
    	try {
    		Assignment[] assignments = new Assignment[20];
        	ArrayList<Assignment> dynamicArray = new ArrayList<Assignment>();
        	ArrayList<Assignment> greedyArray = new ArrayList<Assignment>();
        	assignments = readFile("input.json");
        	Arrays.sort(assignments);

        	
        	Scheduler sch = new Scheduler(assignments);
        	
        	dynamicArray = sch.scheduleDynamic();
        	greedyArray = sch.scheduleGreedy();
        	writeOutput("solution_dynamic.json", dynamicArray);
        	writeOutput("solution_greedy.json", greedyArray);
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}

    }

	//if file is not found, throws exception
    private static Assignment[] readFile(String filename) throws FileNotFoundException { 
    	BufferedReader reader = null;
    	Assignment[] assignments = new Assignment[20];
    	try {
    		reader = new BufferedReader(new FileReader(filename));
    		Gson gson = new Gson();
    		assignments = gson.fromJson(reader, Assignment[].class);
    	}
    	catch(FileNotFoundException ex){
    		throw new FileNotFoundException();
    	}
    	return assignments;
    }

	//writing output
    private static void writeOutput(String filename, ArrayList<Assignment> arrayList) throws IOException {
    	try {
    		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    		FileWriter file = new FileWriter(filename);
    		file.write(gson.toJson(arrayList));
    		file.close();
    	}
    	catch(IOException e) {
    		throw new IOException();
    	}
    	
    }
}
















