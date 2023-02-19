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
    public TextField action11,action12,action13,action14,action15,action16,action17,action18;
    GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void disableAction1(){
        action11.setOpacity(0);action11.setDisable(true);action12.setOpacity(0);action12.setDisable(true);
        action13.setOpacity(0);action13.setDisable(true);action14.setOpacity(0);action14.setDisable(true);
        action15.setOpacity(0);action15.setDisable(true);action16.setOpacity(0);action16.setDisable(true);
        action17.setOpacity(0);action17.setDisable(true);action18.setOpacity(0);action18.setDisable(true);
    }

    public void query2controller() {
        disableAction3();
        disableAction1();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Returns the amount of people with the Super Green Pass (vaccinated in the last 9 months)");
        textfield.setDisable(true); textfield.setOpacity(0);buttonquery.setDisable(true);buttonquery.setOpacity(0);
        Integer result = new QueryUtil().query2();
        results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
        labelResult.setText(result.toString());
    }


    public void query1controller() {
        disableAction3();
        disableAction1();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Checks if the selected person, identified by his id (a number between 0 and 99) has got the Green Pass(vaccinated in the last " +
                "9 months or tested in the last 3 days)");
        textfield.setDisable(false); textfield.setOpacity(1);buttonquery.setDisable(false);buttonquery.setOpacity(1);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
        buttonquery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int resultList = new QueryUtil().query1(textfield.getText());
                results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
                if(resultList==1){labelResult.setText("The selected person has got the Green Pass");}
                else{labelResult.setText("The selected person hasn't got the Green Pass");}
            }
        });
    }

    public void action1Controller() {
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Creates a test with the inserted specifics");
        textfield.setDisable(true); textfield.setOpacity(0);buttonquery.setDisable(true);buttonquery.setOpacity(0);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
        action11.setOpacity(1);action11.setDisable(false);action12.setOpacity(1);action12.setDisable(false);
        action13.setOpacity(1);action13.setDisable(false);action14.setOpacity(1);action14.setDisable(false);
        action15.setOpacity(1);action15.setDisable(false);action16.setOpacity(1);action16.setDisable(false);
        action17.setOpacity(1);action17.setDisable(false);action18.setOpacity(1);action18.setDisable(false);
        createButton.setDisable(false);createButton.setOpacity(1);createButton.setText("Create test");
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean resultList = new QueryUtil().command1(action11.getText(),action12.getText(),action13.getText(),action14.getText(),
                        action15.getText(),action16.getText(),action17.getText(),action18.getText());
                results1.setDisable(false);results1.setOpacity(1);labelResult1.setDisable(false);labelResult1.setOpacity(1);
                labelResult1.setText("Test successfully added");
            }
        });
    }

    public void action2Controller() {
        disableQuery();disableAction1();disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Deletes a certificate of the person with the inserted name and surname");
        creation1.setDisable(false); creation1.setOpacity(1);creation2.setDisable(false); creation2.setOpacity(1);
        firstName.setOpacity(1);lastName.setOpacity(1);firstName.setText("First Name"); lastName.setText("Last Name");
        createButton.setOpacity(1);createButton.setDisable(false);createButton.setText("Delete certificate");
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Boolean result = new QueryUtil().command2(creation1.getText(),creation2.getText());
                results1.setDisable(false);results1.setOpacity(1);labelResult1.setDisable(false);labelResult1.setOpacity(1);
                StringBuilder show = new StringBuilder();
                if(!result){
                    show.append("Unable to delete the selected person");
                }else{
                    show.append(creation1.getText()+" "+creation2.getText()+" has been deleted from the database");
                }
                labelResult1.setText(show.toString());
            }
        });
    }

    public void action3Controller() {
        disableQuery();disableAction1();disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Updates the emergency phone contact of a person given his/her id");
        creation1.setDisable(false); creation1.setOpacity(1);creation2.setDisable(false); creation2.setOpacity(1);
        firstName.setOpacity(1);lastName.setOpacity(1);firstName.setText("Person ID"); lastName.setText("New phone");
        createButton.setOpacity(1);createButton.setDisable(false);createButton.setText("Update");
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Boolean result = new QueryUtil().command3(creation1.getText(),creation2.getText());
                results1.setDisable(false);results1.setOpacity(1);labelResult1.setDisable(false);labelResult1.setOpacity(1);
                StringBuilder show = new StringBuilder();
                if(!result){
                    show.append("Unable to update the phone number");
                }else{
                    show.append("Phone number successfully updated!");
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
        disableAction1();
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Amount of people vaccinated in a certain day, format YYYY-MM-DD (for example 2021-08-04)");
        textfield.setDisable(false); textfield.setOpacity(1);buttonquery.setDisable(false);buttonquery.setOpacity(1);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
        buttonquery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int resultList = new QueryUtil().query3(textfield.getText());
                results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
                labelResult.setText(String.valueOf(resultList));
            }
        });
    }


    public void query4controller() {
        disableAction1();
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Name and surname of all the people vaccinated by a certain doctor, identified by his id (for example 1013)");
        textfield.setDisable(false); textfield.setOpacity(1);buttonquery.setDisable(false);buttonquery.setOpacity(1);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
        buttonquery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList resultList = new QueryUtil().query4(textfield.getText());
                results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
                String show = "";
                for(int i=0;i<resultList.size();i++){
                    show += i+1 +" "+ resultList.get(i)+"\n";
                }
                labelResult.setText(show);
            }
        });
    }

    public void query5controller() {
        disableAction1();
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Name and surname of all the people vaccinated with Johnsson & Johnsson in a certain center, identified by its id (for example c4ceUtvT)");
        textfield.setDisable(false); textfield.setOpacity(1);buttonquery.setDisable(false);buttonquery.setOpacity(1);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
        buttonquery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList resultList = new QueryUtil().query5(textfield.getText());
                results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
                String show = "";
                for(int i=0;i<resultList.size();i++){
                    show += i+1 +" "+ resultList.get(i)+"\n";
                }
                labelResult.setText(show);
            }
        });
    }

    public void query6controller() {
        disableAction1();
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Name and surname of all the people vaccinated in a certain center, identified by its id (for example c4ceUtvT)");
        textfield.setDisable(false); textfield.setOpacity(1);buttonquery.setDisable(false);buttonquery.setOpacity(1);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
        buttonquery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList resultList = new QueryUtil().query6(textfield.getText());
                results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
                String show = "";
                for(int i=0;i<resultList.size();i++){
                    show += i+1 +" "+ resultList.get(i)+"\n";
                }
                labelResult.setText(show);
            }
        });
    }

    public void query7controller(ActionEvent actionEvent) {
        disableAction1();
        disableAction3();
        labelDescription.setOpacity(1);labelDescription.setDisable(false);
        labelDescription.setText("Number of people with the selected amount of vaccination doses");
        textfield.setDisable(false); textfield.setOpacity(1);buttonquery.setDisable(false);buttonquery.setOpacity(1);
        results.setDisable(true);results.setOpacity(0);labelResult.setDisable(true);labelResult.setOpacity(0);
        buttonquery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int resultList = new QueryUtil().query7(textfield.getText());
                results.setDisable(false);results.setOpacity(1);labelResult.setDisable(false);labelResult.setOpacity(1);
                labelResult.setText(String.valueOf(resultList));
            }
        });
    }
}
