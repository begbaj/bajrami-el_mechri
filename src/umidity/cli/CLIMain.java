package umidity.cli;


import umidity.api.ApiCalls;
import umidity.information.ApiResponse;

import java.util.Scanner;

public class CLIMain {
    String prompt = ">";
    private Scanner inputScanner;
    public CLIMain(){
        inputScanner = new Scanner(System.in);
        System.out.println("umidity version: 0.0.1");
        while(mainMenu() != 0);
    }

    private int mainMenu(){
        System.out.println(
                "Menu:\n" +
                        "1) Get by city name\n" +
                        "2) Get ...\n" +
                        "3) Get ...\n" +
                        "4) Get ...\n" +
                        "0) to quit \n"
        );
        String input = userPrompt(UserPromptTypes.Integer);
        if(input.equals("1")){
            ApiCalls caller = new ApiCalls("a8f213a93e1af4abd8aa6ea20941cb9b");
            System.out.println("City name: ");
            ApiResponse response;
            try {
                String cityName = userPrompt();
                response = caller.getByCityName(cityName, "", "");
                System.out.printf(" City:%s\n Humidity:%s\n Temperature:%s \n",
                        response.name,
                        response.main.humidity,
                        response.main.temp);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(input.equals("0"))
            return 0;
        return 1;
    }

    public String userPrompt(){
        System.out.print("string::" + prompt);
        String returns = inputScanner.nextLine();
        System.out.println();
        return returns;
    }

    public String userPrompt(UserPromptTypes promptType){
        String returns = "";
        if(promptType == UserPromptTypes.Integer){
            System.out.print("int::" + prompt);
            returns += String.valueOf(inputScanner.nextInt());
            inputScanner = new Scanner(System.in);
            System.out.println();
        }
        if(promptType == UserPromptTypes.Float){
            System.out.print("float::" + prompt);
            returns = String.valueOf(inputScanner.nextFloat());
            System.out.println();
        }
        if(promptType == UserPromptTypes.Double){
            System.out.print("double::" + prompt);
            returns = String.valueOf(inputScanner.nextDouble());
            System.out.println();
        }
        if(promptType == UserPromptTypes.String){
            System.out.print("string::" + prompt);
            returns = inputScanner.nextLine();
            System.out.println();
        }
        //TODO: etc, etc (userPrompt tempalte types)
        return returns;
    }
}
