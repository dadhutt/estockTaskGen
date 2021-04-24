package com.estock;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class eStockTaskCreator extends Application
{
   /* public JSONObject makeNewTask()
    {
        JSONObject hey = new JSONObject();

        hey.put();
    }*/

    // 3060 - B08WPJ5P4R,B08WT47L8B,B08WHJPBFX,B08W8DGK3X,B08WGTL4CW,B08X4V8N5Q,B08WM28PVH,B08WPRMVWB,B08X4VJ6SB,B08NYPLXPJ,B08NW5HNYW,B08P2HBBLX,B08P3V572B
    // 3070 - B08HBF5L3K,B08HBJB7YD,B08KWN2LZG,B08LF1CWT2,B08L8LG4M3,B08L8L9TCZ,B08L8L71SM,B08M14Y3C7,B08M13DXSZ,B08KY266MG,B08KWLMZV4,B08L8HPKR6,B08MT6B58K,B08L8JNTXQ,B08L8KC1J7,B08LW46GH2,B08KWPDXJZ,B08LF32LJ6,B08M4YFNX2,B08R15HVJ3
    // 3080 - B08HR5SXPS,B08QW8BKDV,B08HR3Y5GQ,B08J6F174Z,B08HJTH61J,B08HHDP9DW,B08HH5WF97,B08HVV2P4Z,B08HR6FMF3,B08HR55YB5,B08HR7SV3M,B083GSKZSW
    // PS5 - B08FC6MR62
    public static void main (String[] args)
    {
        launch(args);
    }

    public static void make()
    {
        ArrayList<String> asins = new ArrayList<>(), profiles = new ArrayList<>();
        JSONArray finalArr = new JSONArray();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter profile names (Separate account names by comma)");
        for(String profile : scanner.nextLine().split(","))
        {
            if(!profile.trim().isEmpty())
            {
                profiles.add(profile.trim());
            }
        }

        boolean moreTasks = true;

        do
        {
            asins.clear();
            eStockSettings settingsToMake = new eStockSettings();
            settingsToMake.setProfiles(profiles);
            System.out.println("Enter asins (Separate multiple asins by comma)");
            for(String asin : scanner.nextLine().split(","))
            {
                asins.add(asin.trim());
            }

            /**
             B08HR5SXPS,B08QW8BKDV,B08HR3Y5GQ,B08J6F174Z,B08HJTH61J,B08HHDP9DW,B08HH5WF97,B08HVV2P4Z,B08HR6FMF3,B08HR55YB5,B08HR7SV3M,B083GSKZSW             */
            System.out.println("Enter Quantity");
            settingsToMake.setQuantity(Integer.parseInt(scanner.nextLine()));

            System.out.println("Enter Price Check");
            settingsToMake.setPriceCheck(Integer.parseInt(scanner.nextLine()));

            System.out.println("Number of tasks per account");
            settingsToMake.setNumOfTasks(Integer.parseInt(scanner.nextLine()));

            System.out.println("Make 1 normal task per profile per asin? (y/n)");

            switch (scanner.nextLine().toLowerCase().trim())
            {
                case "y":
                {
                    settingsToMake.setMakeNormal(true);
                    break;
                }
                default:
                {
                    settingsToMake.setMakeNormal(false);
                }

            }

            JSONArray array = makeTasks(asins,settingsToMake);

            for(Object x : array.toList())
            {
                finalArr.put(x);
            }

            System.out.println("Total Tasks - " + finalArr.length());

            System.out.println("\nCreate More Tasks? (y/n)");
            switch (scanner.nextLine().toLowerCase().trim())
            {
                case "y":
                {
                    break;
                }
                default:
                {
                    moreTasks = false;
                }

            }
        }
        while (moreTasks);

        try {
            makeFile(finalArr);
        }
        catch (IOException e)
        {
            System.out.println("Error making file");
            e.printStackTrace();
        }

    }

    public static String loadFromFile(String pathname)
    {
        StringBuilder toReturn = new StringBuilder();
        File file = new File(pathname);

        try
        {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine())
            {
                toReturn.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return toReturn.toString();

    }

    public static JSONArray makeTasks(ArrayList<String> asins, eStockSettings settings)
    {
        JSONArray taskArray = new JSONArray();
        int totalTasks = 0;
        for(String profile : settings.getProfiles())
        {
            for(String asin : asins)
            {
                for(int i = 0; i < settings.getNumOfTasks();i++)
                {
                    JSONObject task = new JSONObject();
                    task.put("uuid", UUID.randomUUID());
                    task.put("site","Amazon");
                    task.put("link",asin);
                    task.put("delay",1);
                    task.put("profile",getShortenedProfile(profile));
                    task.put("proxies","No Proxies");
                    task.put("endless","true");
                    task.put("accessToken","");
                    task.put("status","Idle");
                    task.put("quantity",settings.getQuantity());
                    task.put("email","");
                    task.put("password","");
                    task.put("price",settings.getPriceCheck());
                    task.put("sessionName",profile);
                    task.put("thirdParty",false);
                    task.put("region","com");
                    task.put("fallbackOnVariantNotFound","");
                    task.put("variantInput","");
                    task.put("condition",true);
                    task.put("accountID","");
                    task.put("size","");
                    task.put("colorId","");
                    task.put("monitor_pref",true);
                    task.put("catchall","");
                    task.put("shipping_option",false);
                    task.put("bruteForce","");
                    task.put("bruteForceATC","");
                    task.put("captchaMode","");
                    task.put("checkoutMethod","");
                    task.put("groupedMonitoring",false);
                    task.put("mode",true);
                    task.put("offer","");
                    task.put("statusColor","#ffffff");

                    taskArray.put(task);
                    totalTasks++;
                }

                if(settings.isMakeNormal())
                {
                    JSONObject task = new JSONObject();
                    task.put("uuid", UUID.randomUUID());
                    task.put("site","Amazon");
                    task.put("link",asin);
                    task.put("delay",1);
                    task.put("profile",getShortenedProfile(profile));
                    task.put("proxies","No Proxies");
                    task.put("endless","true");
                    task.put("accessToken","");
                    task.put("status","Idle");
                    task.put("quantity",settings.getQuantity());
                    task.put("email","");
                    task.put("password","");
                    task.put("price",settings.getPriceCheck());
                    task.put("sessionName",profile);
                    task.put("thirdParty",false);
                    task.put("region","com");
                    task.put("fallbackOnVariantNotFound","");
                    task.put("variantInput","");
                    task.put("condition",true);
                    task.put("accountID","");
                    task.put("size","");
                    task.put("colorId","");
                    task.put("monitor_pref",true);
                    task.put("catchall","");
                    task.put("shipping_option",false);
                    task.put("bruteForce","");
                    task.put("bruteForceATC","");
                    task.put("captchaMode","");
                    task.put("checkoutMethod","");
                    task.put("groupedMonitoring",false);
                    task.put("mode",false);
                    task.put("offer","");
                    task.put("statusColor","#ffffff");

                    taskArray.put(task);
                    totalTasks++;

                }
            }
        }
        System.out.println("Tasks Created - " + totalTasks);
        return taskArray;

    }

    public static String getShortenedProfile(String profile)
    {
        return profile.substring(0,7) + "...";
    }

    public static void makeFile(JSONArray array) throws IOException {
        File file = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/tasks.json");

        if(!file.exists())
        {
            System.out.println("Making new json file!");
            file.createNewFile();
        }
        else
        {
            System.out.println("Overwriting task.json file!");
        }
        FileWriter myWriter = new FileWriter(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/tasks.json");
        myWriter.write(array.toString());
        myWriter.close();

        System.out.println("File Made");

    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("eStockTaskGUI.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hydration's eStock Task Creator v0.14");
        primaryStage.show();
    }

}
