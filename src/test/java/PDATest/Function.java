package PDATest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

public class Function 
{
	static String  keyName = "RootManageSharedAccessKey";
	static String URI;
	static String Topic;
	
	public static void SASGeneration(String resourceUri, String keyName,String  key) throws IOException
    {
      	String sasToken = SASGenerator.GetSASToken(resourceUri, keyName, key);
    	   	
    	String filePath = "Input/SASToken.txt";
		FileWriter fileWriter;
			fileWriter = new FileWriter(filePath);
			fileWriter.write(sasToken);
			fileWriter.close();
		
    }
	
	public static void Environments() throws IOException
	{

      
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/test/java/Config/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);

        boolean isValidTest;

        do {
        	System.out.print("Envirnments - ");
            System.out.println("DEV  -" +  "  SIT  -" + "  PPD  ");
            System.out.print("Enter the Environment To Be Run (Case Sensitive) : ");
            String selectedTest = scanner.nextLine();

        	
        	isValidTest = true;
            switch (selectedTest.toUpperCase()) {
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
                    System.out.println("Invalid test selected. Please try again.");
                    System.out.println();
                    isValidTest = false;
            }

        } while (!isValidTest);
		
	}
	
	public static void runDev() throws IOException 
    {
		String filePath = "src/test/java/Config/ValueConfiguration.json";
		String fileContents = new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject jsonObject = new JSONObject(fileContents);
		String DevURI = jsonObject.getString("DevURI");
		String Devkey = jsonObject.getString("Devkey");
		String Dev = jsonObject.getString("Dev");
		String topic = jsonObject.getString("topic");
    	
		System.out.println();
    	System.out.println("ResourceUri  :  " + DevURI);
    	
    	Function.SASGeneration(DevURI, keyName, Devkey);

    	File sas = new File("Input/SASToken.txt");
		String sastoken = new String(Files.readAllBytes(sas.toPath()), StandardCharsets.UTF_8);
		
		File body = new File("Input/RequestBody.json");
		String requestbody = new String(Files.readAllBytes(body.toPath()), StandardCharsets.UTF_8);
		
		Function.Function(Dev, topic);
    }
	

    public static void runSit() throws IOException 
    {
    	String filePath = "src/test/java/Config/ValueConfiguration.json";
		String fileContents = new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject jsonObject = new JSONObject(fileContents);
		String SitURI = jsonObject.getString("SitURI");
		String Sitkey = jsonObject.getString("Sitkey");
		String Sit = jsonObject.getString("Sit");
		String topic = jsonObject.getString("topic");
    	
		System.out.println();
    	System.out.println("ResourceUri  :  " + SitURI);
    	System.out.println();
    	
    	Function.SASGeneration(SitURI, keyName, Sitkey);

    	File sas = new File("Input/SASToken.txt");
		String sastoken = new String(Files.readAllBytes(sas.toPath()), StandardCharsets.UTF_8);
		
		File body = new File("Input/RequestBody.json");
		String requestbody = new String(Files.readAllBytes(body.toPath()), StandardCharsets.UTF_8);
		
		Function.Function(Sit, topic);
    }

    public static void runPpd() throws IOException 
    {
    	String filePath = "src/test/java/Config/ValueConfiguration.json";
		String fileContents = new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject jsonObject = new JSONObject(fileContents);
		String PpdURI = jsonObject.getString("PpdURI");
		String Ppdkey = jsonObject.getString("Ppdkey");
		String Ppd = jsonObject.getString("Ppd");
		String topic = jsonObject.getString("topic");
    	
		System.out.println();
    	System.out.println("ResourceUri  :  " + PpdURI);
    	System.out.println();
    	
    	Function.SASGeneration(PpdURI, keyName, Ppdkey);

    	File sas = new File("Input/SASToken.txt");
		String sastoken = new String(Files.readAllBytes(sas.toPath()), StandardCharsets.UTF_8);
		
		File body = new File("Input/RequestBody.json");
		String requestbody = new String(Files.readAllBytes(body.toPath()), StandardCharsets.UTF_8);
		
		Function.Function(Ppd, topic);
    }
	   
	public static void TestRun() throws IOException
	{
		RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
		
		
		
		String filePath = "src/test/java/Config/ValueConfiguration.json";
		String fileContents = new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject jsonObject = new JSONObject(fileContents);
		String DevURL = jsonObject.getString("DevURL");
		String topic = jsonObject.getString("topic");
		String SitURL = jsonObject.getString("SitURL");
		String PpdURL = jsonObject.getString("PpdURL");
		
	}
	
	public static void Function(String URI, String Topic) throws IOException
	{
		//RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
		File sas = new File("Input/SASToken.txt");
		String sastoken = new String(Files.readAllBytes(sas.toPath()), StandardCharsets.UTF_8);
		File body = new File("Input/RequestBody.json");
		String requestbody = new String(Files.readAllBytes(body.toPath()), StandardCharsets.UTF_8);
		
		RestAssured.baseURI = URI + Topic + "/messages";
		Response response = RestAssured.given().log().method().when()
				.header("Authorization",sastoken)
				.header("X.RMG.MESSAGE.TYPE","SRA")
				.header("X.RMG.MESSAGE.ID","be84d5ec-2fb1-49ba-bef6-6168c558408a")
				.body(requestbody)
				.post();
		System.out.println(response.getStatusLine());
		System.out.println(response.getHeaders());
		
	}
		
		
		

}
