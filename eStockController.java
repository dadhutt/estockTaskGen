package com.estock;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static com.estock.eStockTaskCreator.*;

public class eStockController {

    public JSONArray toSend = new JSONArray();

    @FXML
    private TextField TASK_NUM;


    @FXML
    private TextField EMAILS;

    @FXML
    private TextField ASINS;

    @FXML
    private CheckBox NORMAL_CHECK;

    @FXML
    private TextField PROXIES;

    @FXML
    private TextField QUANTITY;

    @FXML
    private TextField PRICE_CHECK;

    @FXML
    private Label TASK_COUNT;

    @FXML
    private Label TOTAL_COUNT;

    @FXML
    private Button TASK_BUTTON;

    @FXML
    private Button JSON_BUTTON;

    @FXML
    private Label STATUS;

    @FXML
    private CheckBox ENDLESS;

    @FXML
    private CheckBox THIRD;

    @FXML
    private CheckBox NEW;

    @FXML
    private Hyperlink HYPERLINK;

    @FXML
    private TextField DELAY;

    @FXML
    private Button RESET;

    @FXML
    private ComboBox<String> PRESET;

    @FXML
    private TextField OFFERID;

    @FXML
    private ComboBox<String> MODE;

    @FXML
    private Label MODE_COUNT;

    String[] modes = {"Serverside","ASIN"};

    ArrayList<Preset> presetsSS = new ArrayList<>();
    ArrayList<Preset> presetsASIN = new ArrayList<>();

    int serverCount = 0, asinCount = 0, offerCount = 0;


    EventHandler<ActionEvent> openLink = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            try
            {
                Desktop.getDesktop().browse(new URI("https://docs.google.com/spreadsheets/d/1aWcwmJ_LxWL4GWbaoE6iWRhM0tWQzYsvNVWqFN2iae8/edit#gid=1644844704"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (URISyntaxException uriSyntaxException) {
                uriSyntaxException.printStackTrace();
            }
        }
    };

    EventHandler<ActionEvent> test = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            try
            {
                makeFile(toSend);
                STATUS.setText("tasks.json created in Documents Folder!");

            } catch (IOException ioException)
            {
                STATUS.setText("Error Making JSON");
            }
        }
    };

    EventHandler<ActionEvent> modeSelect = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            OFFERID.setDisable(!MODE.getSelectionModel().getSelectedItem().contains("Offer"));

            PRESET.getItems().clear();

            ArrayList<Preset> toUse = new ArrayList<>();

            switch (MODE.getSelectionModel().getSelectedItem())
            {
                case "Serverside":
                {
                    toUse = presetsSS;
                    break;
                }
                case "ASIN":
                {
                    toUse = presetsASIN;
                    break;
                }
                default:{
                    break;
                }
            }

            for(Preset x : toUse)
            {
                PRESET.getItems().add(x.name);
            }
        }
    };

    EventHandler<ActionEvent> resetTasks = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            STATUS.setText("Tasks Reset!");
            TASK_COUNT.setText("Tasks Created: 0");
            TOTAL_COUNT.setText("Total Tasks: 0");

            toSend.clear();
        }
    };

    EventHandler<ActionEvent> fillPreset = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            String toFind = PRESET.getSelectionModel().getSelectedItem();

            ArrayList<Preset> toUse = new ArrayList<>();

            switch (MODE.getSelectionModel().getSelectedItem())
            {
                case "Serverside":
                    toUse = presetsSS;
                    break;
                case "ASIN":
                    toUse = presetsASIN;
                    break;
            }

            for(Preset x : toUse)
            {
                if(x.name.equals(toFind))
                {
                    ASINS.setText(x.asins);
                    PRICE_CHECK.setText("" + x.price);
                }
            }
        }
    };
    //delay = int, quantity = String, price = String
    EventHandler<ActionEvent> makeTasks = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event)
        {
            ArrayList<String> asins = new ArrayList<>(), profiles = new ArrayList<>(), proxies = new ArrayList<>();
            boolean canMake = true;
            int delay = 1;
            String proxy = "No Proxies",quantity = "", price = "",errorMessage = "Error";
            eStockSettings settingsToMake = new eStockSettings();

            if(EMAILS.getText().isEmpty())
            {
                canMake = false;
                errorMessage = "Email Field is empty!";
            }
            else
            {
                for(String profile : EMAILS.getText().split(","))
                {
                    if(!profile.trim().isEmpty())
                    {
                        profiles.add(profile.trim());
                    }
                }
            }
            if(ASINS.getText().isEmpty())
            {
                canMake = false;
                errorMessage = "ASIN Field is empty!";
            }
            else
            {
                for(String asin : ASINS.getText().split(","))
                {
                    if(!asin.trim().isEmpty())
                    {
                        asins.add(asin.trim());
                    }
                }
            }

            if(PROXIES.getText().trim().isEmpty())
            {
                proxies.add("No Proxies");
            }
            else
            {
                proxies.addAll(Arrays.asList(PROXIES.getText().split(",")));
            }

            settingsToMake.setProfiles(profiles);

            /**
             B08HR5SXPS,B08QW8BKDV,B08HR3Y5GQ,B08J6F174Z,B08HJTH61J,B08HHDP9DW,B08HH5WF97,B08HVV2P4Z,B08HR6FMF3,B08HR55YB5,B08HR7SV3M,B083GSKZSW             */
            System.out.println("Enter Quantity");

            try
            {
                quantity = Integer.parseInt(QUANTITY.getText()) + "";
            }
            catch (Exception e)
            {
                canMake = false;
                errorMessage = "Quantity Error! Use numbers only!";
            }

            try
            {
                price = Integer.parseInt(PRICE_CHECK.getText()) + "";
            }
            catch (Exception e)
            {
                canMake = false;
                errorMessage = "Price Check Error! Use numbers only!";
            }

            try
            {
                settingsToMake.setNumOfTasks(Integer.parseInt(TASK_NUM.getText()));
            }
            catch (Exception e)
            {
                canMake = false;
                errorMessage = "Task Number Error! Use numbers only!";
            }

            try
            {
                delay = Integer.parseInt(DELAY.getText());
            }
            catch (Exception e)
            {
                canMake = false;
                errorMessage = "Delay Error! Use numbers only!";
            }

            if(canMake)
            {
                int totalTasks = 0;
                boolean isServerSide = MODE.getSelectionModel().getSelectedItem().contains("Server");
                for(String profile : settingsToMake.getProfiles())
                {
                    for(String asin : asins)
                    {
                        for(int i = 0; i < settingsToMake.getNumOfTasks();i++)
                        {
                            proxy = proxies.get(totalTasks % proxies.size());

                            JSONObject task = new JSONObject();
                            task.put("uuid", UUID.randomUUID());
                            task.put("site","Amazon");
                            task.put("link",asin);
                            task.put("delay",delay);
                            task.put("profile",getShortenedProfile(profile));
                            task.put("proxies",proxy);
                            task.put("endless",ENDLESS.isSelected());
                            task.put("accessToken","");
                            task.put("status","Idle");
                            task.put("quantity",quantity);
                            task.put("email","");
                            task.put("password","");
                            task.put("price",price);
                            task.put("sessionName",profile);
                            task.put("thirdParty",THIRD.isSelected());
                            task.put("region","com");
                            task.put("fallbackOnVariantNotFound","");
                            task.put("variantInput","");
                            task.put("condition",NEW.isSelected());
                            task.put("accountID","");
                            task.put("size","");
                            task.put("colorId","");
                            task.put("monitor_pref",isServerSide);
                            task.put("catchall","");
                            task.put("shipping_option",false);
                            task.put("bruteForce","");
                            task.put("bruteForceATC","");
                            task.put("captchaMode","");
                            task.put("checkoutMethod","");
                            task.put("groupedMonitoring",false);
                            task.put("mode",true);
                            task.put("statusColor","#ffffff");

                            if(MODE.getSelectionModel().getSelectedItem().contains("Offer") && !OFFERID.getText().isEmpty())
                            {
                                task.put("offer",OFFERID.getText().trim());

                            }
                            toSend.put(task);
                            totalTasks++;

                            if(isServerSide)
                            {
                                serverCount++;
                            }
                            else
                            {
                                asinCount++;
                            }
                        }

                        if(NORMAL_CHECK.isSelected() && !MODE.getSelectionModel().getSelectedItem().contains("Offer"))
                        {
                            proxy = proxies.get(totalTasks % proxies.size());

                            JSONObject task = new JSONObject();
                            task.put("uuid", UUID.randomUUID());
                            task.put("site","Amazon");
                            task.put("link",asin);
                            task.put("delay",delay);
                            task.put("profile",getShortenedProfile(profile));
                            task.put("proxies",proxy);
                            task.put("endless","true");
                            task.put("accessToken","");
                            task.put("status","Idle");
                            task.put("quantity",quantity);
                            task.put("email","");
                            task.put("password","");
                            task.put("price",price);
                            task.put("sessionName",profile);
                            task.put("thirdParty",THIRD.isSelected());
                            task.put("region","com");
                            task.put("fallbackOnVariantNotFound","");
                            task.put("variantInput","");
                            task.put("condition",NEW.isSelected());
                            task.put("accountID","");
                            task.put("size","");
                            task.put("colorId","");
                            task.put("monitor_pref",isServerSide);
                            task.put("catchall","");
                            task.put("shipping_option",false);
                            task.put("bruteForce","");
                            task.put("bruteForceATC","");
                            task.put("captchaMode","");
                            task.put("checkoutMethod","");
                            task.put("groupedMonitoring",false);
                            task.put("mode",false);
                            task.put("statusColor","#ffffff");

                            toSend.put(task);
                            totalTasks++;

                            if(isServerSide)
                            {
                                serverCount++;
                            }
                            else
                            {
                                asinCount++;
                            }

                        }
                    }
            }
            STATUS.setText("Tasks Created!");
            TASK_COUNT.setText("Tasks Created: " + totalTasks);
            TOTAL_COUNT.setText("Total Tasks: " + toSend.length());
            MODE_COUNT.setText("SS: " + serverCount + " / ASIN: " + asinCount + " / OfferID: " + offerCount);

        }
        else
        {
            STATUS.setText(errorMessage);
        }
            
    }};

    public void initialize()
    {
        presetsSS.add(new Preset("3060 (SS)","B08WPJ5P4R,B08WT47L8B,B08WHJPBFX,B08W8DGK3X,B08WGTL4CW,B08X4V8N5Q,B08WM28PVH,B08WPRMVWB,B08X4VJ6SB,B08P2H5LW2",700));
        presetsSS.add(new Preset("3060ti (SS)","B08NYPLXPJ,B08NW5HNYW,B08P2HBBLX,B08P3V572B,B083Z5P6TX,B083Z7TR8Z,B08P3XJLJJ,B08P2D1JZZ,B08NW693LG",750));
        presetsSS.add(new Preset("3070 (SS)","B08HBF5L3K,B08HBJB7YD,B08KWN2LZG,B08LF1CWT2,B08L8LG4M3,B08L8L9TCZ,B08L8L71SM,B08M14Y3C7,B08M13DXSZ,B08KY266MG,B08KWLMZV4,B08L8HPKR6,B08MT6B58K,B08L8JNTXQ,B08L8KC1J7,B08LW46GH2,B08KWPDXJZ,B08LF32LJ6,B08M4YFNX2,B08R15HVJ3",950));
        presetsSS.add(new Preset("3080 (SS)","B08HR5SXPS,B08QW8BKDV,B08HR3Y5GQ,B08J6F174Z,B08HJTH61J,B08HHDP9DW,B08HH5WF97,B08HVV2P4Z,B08HR6FMF3,B08HR55YB5,B08HR7SV3M,B083GSKZSW,B08KTWTMK5",1150));
        presetsSS.add(new Preset("3090 (SS)","B08J5F3G18,B08J61SS5R,B08HJQ182D,B08HJRF2CN,B08J6GMWCQ,B08HR9D2JS,B08HJGNJ81,B08HRBW6VB,B08KTWVHQP,B08R133PYZ",2000));
        presetsSS.add(new Preset("PS5 (SS)","B08FC5L3RG",570));

        presetsASIN.add(new Preset("3060 (ASIN)","B08WM28PVH,B08WPRMVWB,B08WTFG5BX,B08WPJ5P4R,B08W8DGK3X,B08WGTL4CW,B08WHJPBFX,B08X4V8N5Q,B08X4W98LV,B08X4VJ6SB,B08WT47L8B",700));
        presetsASIN.add(new Preset("3060ti (ASIN)","B08P3XJLJJ,B08P3V572B,B08NYNJ6RC,B08NYPKW1Z,B08NYPLXPJ,B08NYP7KG6,B08Q8QR7PK,B083Z5P6TX,B08R6QRZ9D,B08P2HBBLX,B08P2D1JZZ,B083Z7TR8Z,B08P2DQ28S,B08S6Z5285,B08P2D3JSG,B08R876RTH,B08P2H5LW2,B08PW559LL,B08NW693LG,B08NW5HNYW ",750));
        presetsASIN.add(new Preset("3070 (ASIN)","B08KWLMZV4,B08QV5NX8C,B08KWPDXJZ,B08KWN2LZG,B08Q7BMHVD,B08HBF5L3K,B08HBJB7YD,B08LF1CWT2,B08LMTX18N,B08L8HPKR6,B08L8LG4M3,B08MT6B58K,B08L8KC1J7,B08R15HVJ3,B08KXZV626,B08KY322TH,B08KY266MG,B08M14Y3C7,B08M13DXSZ,B08LW46GH2,B08MYCN952,B08L8L71SM,B08L8L9TCZ,B08L8JNTXQ,B08R42LX5R,B08W2LJY26,B08M4YFNX2,B08V1ZWGVR,B08LF32LJ6",950));
        presetsASIN.add(new Preset("3080 (ASIN)","B08JCRHZGW,B08HH5WF97,B08HHDP9DW,B08HR7SV3M,B08HR3Y5GQ,B08HBTJMLJ,B08HBR7QBM,B08HJS2JLJ,B08HR6FMF3,B08HJNKT3P,B08HR5SXPS,B08HR55YB5,B08HR4RJ3Q,B08HJTH61J,B08J6F174Z,B08KTWTMK5,B08HR3DPGW,B08RHX55HG,B08TRR3LG2,B08QW8BKDV,B08HVV2P4Z",1150));
        presetsASIN.add(new Preset("PS5 (ASIN)","B08FC5L3RG",570));

        TASK_BUTTON.setOnAction(makeTasks);
        JSON_BUTTON.setOnAction(test);
        HYPERLINK.setOnAction(openLink);
        RESET.setOnAction(resetTasks);

        for(Preset x : presetsSS)
        {
            PRESET.getItems().add(x.name);
        }

        PRESET.setOnAction(fillPreset);

        MODE.getItems().addAll(modes);
        MODE.getSelectionModel().select("Serverside");
        MODE.setOnAction(modeSelect);
    }

}
