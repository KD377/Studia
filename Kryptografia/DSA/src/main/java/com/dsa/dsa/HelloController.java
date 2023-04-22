package com.dsa.dsa;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.dsa.dsa.DSA;
import com.dsa.dsa.Utilities;

import java.math.BigInteger;

public class HelloController {
    @FXML
    private Button generateButton;

    @FXML
    private Button signButton;

    @FXML
    private Button verifyButton;
    @FXML
    private TextField qgField;
    @FXML
    private TextField privateField;
    @FXML
    private TextField publicField;
    @FXML
    private TextField pModField;

    @FXML
    private TextArea inputArea;

    @FXML
    private TextArea outputArea;

    @FXML
    private RadioButton inputSwitch;

    @FXML
    private RadioButton fileSwitch;

    private RadioButton lastSelected;

    private DSA dsa;

    private BigInteger [] signature;
    @FXML
    protected void initialize(){
        dsa = new DSA();
    }

    @FXML
    protected void onGenerateButtonClick(){
        dsa.generateKey();
        privateField.setText(Utilities.toHexString(dsa.getX()));
        publicField.setText(Utilities.toHexString(dsa.getY()));
        qgField.setText(Utilities.toHexString(dsa.getQ())+Utilities.toHexString(dsa.getG()));
        pModField.setText(Utilities.toHexString(dsa.getP()));
    }

    @FXML
    protected void handleFileSwitch() {
        if (fileSwitch.isSelected()) {
            if (lastSelected != null && lastSelected != fileSwitch) {
                lastSelected.setSelected(false);
            }
            lastSelected = fileSwitch;
        } else {
            lastSelected = null;
        }
    }

    @FXML
    protected void handleInputSwitch() {
        if (inputSwitch.isSelected()) {
            if (lastSelected != null && lastSelected != inputSwitch) {
                lastSelected.setSelected(false);
            }
            lastSelected = inputSwitch;
        } else {
            lastSelected = null;
        }
    }

    @FXML
    protected void handleSignButton(){
        if(inputSwitch.isSelected()){
            signature = dsa.sign(Utilities.stringToByteArray(inputArea.getText()));
            outputArea.setText(Utilities.toHexString(signature[0])+"\n"+Utilities.toHexString(signature[1]));
        } else if (fileSwitch.isSelected()) {

        }
    }

    @FXML
    protected void handleVerifyButton(){
        boolean isVerified;
        if(inputSwitch.isSelected()){
            isVerified = dsa.verifySignature(Utilities.stringToByteArray(inputArea.getText()),signature);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            if(isVerified){
                alert.setContentText("Podpis zgodny!");
            }
            else{
                alert.setContentText("Podpis niezgodny!");
            }
            alert.showAndWait();
        } else if (fileSwitch.isSelected()) {

        }
    }
}