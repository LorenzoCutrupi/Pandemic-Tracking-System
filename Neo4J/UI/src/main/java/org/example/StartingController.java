package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class StartingController implements GUIController{
    public Label labelDescription;
    public TextField textfield;
    public Button buttonquery;
    public ScrollPane results;
    public Label labelResult;
    public Button query1,query2,query3,query4,query5,query6,action1,action2,action3;
    public TextField creation1,creation2,creation3;
    public Label firstName,lastName,personID;
    public ScrollPane results1;
    public Label labelResult1;
    public Button createButton;
    GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void query2controller() {
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Returns how many tests resulted positive in the selected day.\n" +
                "Please type a date with format YYYY-MM-DD (For example 2021-11-01)");
        textfield.setDisable(false); textfield.setOpacity(1);buttonquery.setDisable(false);buttonquery.setOpacity(1);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
        buttonquery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Integer result = new QueryUtil().query2(textfield.getText());
                results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
                labelResult.setText(result.toString());
            }
        });
    }


    public void query1controller() {
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Returns all the people who came in contact to the selected person.\n" +
                "Please type a number between 1 and 200 corresponding to the personId (For example 160)");
        textfield.setDisable(false); textfield.setOpacity(1);buttonquery.setDisable(false);buttonquery.setOpacity(1);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
        buttonquery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<String> resultList = new QueryUtil().query1(textfield.getText());
                results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
                StringBuilder show = new StringBuilder();
                for (String res :resultList) {
                    show.append(res).append("\n");
                }
                labelResult.setText(show.toString());
            }
        });
    }

    public void action1Controller() {
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Deletes the person with the selected id");
        textfield.setDisable(false); textfield.setOpacity(1);buttonquery.setDisable(false);buttonquery.setOpacity(1);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
        buttonquery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<String> resultList = new QueryUtil().command1(textfield.getText());
                results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
                StringBuilder show = new StringBuilder();
                if(resultList.size()==0){
                    show.append("There's no person in the database with the selected id");
                }else{
                    for (String res :resultList) {
                        show.append("Person ").append(res).append(" deleted from the database").append("\n");
                    }
                }
                labelResult.setText(show.toString());
            }
        });
    }

    public void action2Controller() {
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Updates the field vaccinated of the person with the selected id to true");
        textfield.setDisable(false); textfield.setOpacity(1);buttonquery.setDisable(false);buttonquery.setOpacity(1);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
        buttonquery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<String> resultList = new QueryUtil().command2(textfield.getText());
                results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
                StringBuilder show = new StringBuilder();
                if(resultList.size()==0){
                    show.append("There's no person in the database with the selected id");
                }else{
                    for (String res :resultList) {
                        show.append("Person ").append(res).append(" is now set as vaccinated").append("\n");
                    }
                }
                labelResult.setText(show.toString());
            }
        });
    }

    public void action3Controller() {
        disableQuery();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Creates a person in the database with the inserted informations");
        creation1.setDisable(false); creation1.setOpacity(1);creation2.setDisable(false); creation2.setOpacity(1);creation3.setDisable(false); creation3.setOpacity(1);
        firstName.setOpacity(1);lastName.setOpacity(1);personID.setOpacity(1);
        createButton.setOpacity(1);createButton.setDisable(false);
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Boolean result = new QueryUtil().command3(creation3.getText(),creation1.getText(),creation2.getText());
                results1.setDisable(false);results1.setOpacity(1);labelResult1.setDisable(false);labelResult1.setOpacity(1);
                StringBuilder show = new StringBuilder();
                if(!result){
                    show.append("There's already a person in the database with the selected id");
                }else{
                    show.append(creation1.getText()+" "+creation2.getText()+" has been successfully added to the database");
                }
                labelResult1.setText(show.toString());
            }
        });
    }

    public void disableAction3(){
        creation1.setDisable(true); creation1.setOpacity(0);creation2.setDisable(true); creation2.setOpacity(0);
        creation3.setDisable(true); creation3.setOpacity(0);
        firstName.setOpacity(0);lastName.setOpacity(0);personID.setOpacity(0);
        createButton.setOpacity(0);createButton.setDisable(true);results1.setOpacity(0);results1.setDisable(true);
        labelResult1.setOpacity(0);labelResult1.setDisable(true);
    }

    public void disableQuery(){
        textfield.setDisable(true); textfield.setOpacity(0);buttonquery.setDisable(true);buttonquery.setOpacity(0);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
    }

    public void query3controller() {
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Returns the percentage of positive people between vaccinated people, and the amount of positive between not vaccinated");
        ArrayList<String> resultList = new QueryUtil().query3();
        double percentage1 = Double.parseDouble(resultList.get(2)) * 100; String formattedValue1 = String.format("%.2f%%", percentage1);
        double percentage2 = Double.parseDouble(resultList.get(5)) * 100; String formattedValue2 = String.format("%.2f%%", percentage2);
        textfield.setDisable(true); textfield.setOpacity(0);buttonquery.setDisable(true);buttonquery.setOpacity(0);
        results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
        results.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);results.setFitToWidth(true);
        String show = "The total amount of vaccinated people is "+resultList.get(1)+" of which "+resultList.get(0)+" are positive.\n"
                +"The percentage of positive people between vaccinated people is "+formattedValue1+"\n\nThe total amount of not vaccinated people is "
                +resultList.get(4)+" of which "+resultList.get(3)+" are positive.\n"
                +"The percentage of positive people between vaccinated people is "+formattedValue2;
        labelResult.setText(show);
    }


    public void query4controller() {
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Returns all the people who are not positive and had contact with a positive person (divided in relatives contacts, street contacts and place contacts)");
        ArrayList<String> resultList = new QueryUtil().query4();
        textfield.setDisable(true); textfield.setOpacity(0);buttonquery.setDisable(true);buttonquery.setOpacity(0);
        results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
        results.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);results.setFitToWidth(true);
        String show = resultList.get(0)+" people had a contact with a family member who resulted positive and must go under quarantine.\n\n"+
                resultList.get(1)+" people had a street contact with a person who resulted positive and must go under quarantine.\n\n"+
                resultList.get(2)+" people had a contact in a place with a person who resulted positive and must go under quarantine.\n";
        labelResult.setText(show);
    }

    public void query5controller() {
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Returns the 10 people who had the most contacts in the days registered in the database");
        ArrayList<String> resultList = new QueryUtil().query5();
        textfield.setDisable(true); textfield.setOpacity(0);buttonquery.setDisable(true);buttonquery.setOpacity(0);
        results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
        results.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);results.setFitToWidth(true);
        String show = "";
        for(int i=0;i<resultList.size();i++){
            show += i+1 +" "+ resultList.get(i)+"\n";
        }
        labelResult.setText(show);
    }

    public void query6controller() {
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Returns all the people who became positive after visiting a place");
        ArrayList<String> resultList = new QueryUtil().query6();
        textfield.setDisable(true); textfield.setOpacity(0);buttonquery.setDisable(true);buttonquery.setOpacity(0);
        results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
        results.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);results.setFitToWidth(true);
        String show = "";
        for(int i=0;i<resultList.size();i++){
            show += resultList.get(i)+"\n";
        }
        labelResult.setText(show);
    }
}
