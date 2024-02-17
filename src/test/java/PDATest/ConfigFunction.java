package PDATest;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class ConfigFunction 
{
	static String URI;
	static String Topic;
	
	
	public static void Function()
	{

        // Load properties from config.properties file
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/test/java/Config/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

     // Get the selected test from the console
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the test to run (DEV, SIT, PPD): ");
        String selectedTest = scanner.nextLine();

        // Validate the selected test
        if (!properties.containsKey(selectedTest)) {
            System.out.println("Invalid test selected");
            return;
        }

        // Run the selected test
        switch (selectedTest) 
        {
            case "DEV":
                runDev();
                break;
            case "SIT":
                runSit();
                break;
            case "PPD":
                runPpd();
                break;
            default:
                System.out.println("Invalid test selected");
        }
		
	}
	public static void main(String[] args) 
	{
		Function();
    }

    public static void runDev() 
    {
    	URI = "URL of Dev Environment";
    	Topic = "/Dev-topic";
        System.out.println("Running Dev");
        
        return;
        // Add your Test2 logic here
       
    }

    public static void runSit() 
    {
    	URI = "URL of Test Environment";
    	Topic = "/Test-topic";
        System.out.println("Running SIT");
        // Add your Test3 logic here
        
        return;
    	
    }

    public static void runPpd() 
    {
    	URI = "URL of Production Environment";
    	Topic = "/Production-topic";
        System.out.println("Running PRD");
        
        
        return;
    }
}

