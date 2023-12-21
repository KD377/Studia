package com.dsa.dsa;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.dsa.dsa.DSA;
import com.dsa.dsa.Utilities;
import javafx.stage.FileChooser;

import java.io.*;
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

    private BigInteger [] signature = new BigInteger[2];

    private byte[] inputFile;
    private boolean inputFileOK = false;
    private boolean isKeyOK = false;
    @FXML
    protected void initialize(){
        dsa = new DSA();
    }

    @FXML
    protected void onGenerateButtonClick(){
        dsa.generateKey();
        isKeyOK  = true;
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
        if(isKeyOK){
            if(inputSwitch.isSelected()){
                signature = dsa.sign(Utilities.stringToByteArray(inputArea.getText()));
                outputArea.setText(Utilities.toHexString(signature[0])+"\n"+Utilities.toHexString(signature[1]));
            } else if (fileSwitch.isSelected()) {
                if(inputFileOK){
                    signature = dsa.sign(inputFile);
                    outputArea.setText(Utilities.toHexString(signature[0])+"\n"+Utilities.toHexString(signature[1]));
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("The file has not been loaded properly");
                    alert.showAndWait();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Select the input source");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("The key has not been generated or loaded");
            alert.showAndWait();
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
            isVerified = dsa.verifySignature(inputFile,signature);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            if(isVerified){
                alert.setContentText("Podpis zgodny!");
            }
            else{
                alert.setContentText("Podpis niezgodny!");
            }
            alert.showAndWait();
        }
    }

    @FXML
    protected void handleLoadFileButton(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File chooser");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                inputFile = inputStream.readAllBytes();
                inputStream.close();
                inputFileOK = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
            inputArea.setText(Utilities.byteArrayToString(inputFile));

        } else {
            inputFileOK = false;
        }
    }

    @FXML
    protected void handleLoadSignButton(){
        FileChooser fileChooser1 = new FileChooser();
        fileChooser1.setTitle("Upload key");
        fileChooser1.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        File file1 = fileChooser1.showOpenDialog(null);
        if(file1 != null){
            try{
                FileInputStream inputStream = new FileInputStream(file1);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String rString = reader.readLine();
                String sString = reader.readLine();
                BigInteger r = new BigInteger(rString);
                BigInteger s = new BigInteger(sString);
                signature[0] = r;
                signature[1] = s;
                reader.close();
                inputStream.close();
                outputArea.setText(Utilities.toHexString(signature[0])+"\n"+Utilities.toHexString(signature[1]));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("The file has not been loaded");
            alert.showAndWait();
        }

    }

    @FXML
    protected void handleSaveSignButton(){
        if(isKeyOK){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    DataOutputStream dataOutput = new DataOutputStream(outputStream);
                    dataOutput.writeBytes(signature[0].toString()+"\n"+signature[1].toString()+"\n");
                    dataOutput.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("The key has not been generated or loaded");
            alert.showAndWait();
        }
    }

    @FXML
    protected void handleSaveKeyButton(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
        File file1 = fileChooser.showSaveDialog(null);
        if(file1 != null && isKeyOK){
            try {
                FileOutputStream outputStream = new FileOutputStream(file1);
                DataOutputStream dataOutput = new DataOutputStream(outputStream);
                dataOutput.writeBytes(dsa.getQ().toString()+"\n"+dsa.getG().toString()+"\n"+dsa.getY().toString()+"\n"+dsa.getX().toString()+"\n"+dsa.getP().toString()+"\n");
                dataOutput.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("The key has not been generated or loaded");
            alert.showAndWait();
        }
    }

    @FXML
    protected void handleLoadKeyButton(){
        FileChooser fileChooser1 = new FileChooser();
        fileChooser1.setTitle("Upload key");
        fileChooser1.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        File file1 = fileChooser1.showOpenDialog(null);
        if(file1 != null){
            try{
                FileInputStream inputStream = new FileInputStream(file1);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String qString = reader.readLine();
                String gString = reader.readLine();
                String yString = reader.readLine();
                String xString = reader.readLine();
                String pString = reader.readLine();
                dsa.setQ(new BigInteger(qString));
                dsa.setG(new BigInteger(gString));
                dsa.setY(new BigInteger(yString));
                dsa.setX(new BigInteger(xString));
                dsa.setP(new BigInteger(pString));
                isKeyOK=true;
                privateField.setText(Utilities.toHexString(dsa.getX()));
                publicField.setText(Utilities.toHexString(dsa.getY()));
                qgField.setText(Utilities.toHexString(dsa.getQ())+Utilities.toHexString(dsa.getG()));
                pModField.setText(Utilities.toHexString(dsa.getP()));
                reader.close();
                inputStream.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}