package com.example.aes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.aes.AES;
import com.example.aes.Other;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.stage.FileChooser;

public class HelloController {
    @FXML
    private TextArea saveFileText;
    @FXML
    private Button saveFileButton;
    @FXML
    private TextArea saveFileTextDec;
    @FXML
    private Button saveFileButtonDec;
    @FXML
    private Button uploadFileButtonDec;
    @FXML
    private TextArea uploadFileTextDec;

    @FXML
    private Label welcomeText;

    @FXML
    private Button generateButton;

    @FXML
    private Button uploadFileButton;
    @FXML
    private TextField keyField;

    @FXML
    private TextArea decodeInput;

    @FXML
    private TextArea uploadFileText;

    @FXML
    private TextArea encodeInput;

    @FXML
    private Label inputLabel;

    @FXML
    private CheckBox fileSwitch;
    @FXML
    private CheckBox inputSwitch;

    private CheckBox lastSelected;

    private AES aes;

    private byte[] klucz ;

    private byte[] fileBytes;

    private byte[] fileBytes2;
    private byte[] message;

    @FXML
    protected void initialize(){
    }

    @FXML
    protected void onGenerateButtonClick() {
        this.aes = new AES(4,10);
        if(keyField.getText().isBlank()){
            this.klucz = new byte[]{(byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, (byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
            keyField.setText(aes.printByteArrayInHex(klucz));
        }
        else {
            if(Other.checkKeyString(keyField.getText())){
                this.klucz = aes.convertKeyStringToHexByteArray(keyField.getText());
                keyField.setText(aes.printByteArrayInHex(klucz));
            }
            else{
                keyField.setText("");
                keyField.setPromptText("The given key is not 32 characters");
                keyField.setStyle("-fx-prompt-text-fill: red;");
            }

        }
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
    protected void handleEncodeButton(){
        if(inputSwitch.isSelected()){
            if(encodeInput.getText().isBlank()){
                inputLabel.setText("Blank input field");
                return;
            }
            inputLabel.setText("");
            message = aes.stringToByteArray(encodeInput.getText());
            decodeInput.setText(aes.printByteArrayInHex(aes.encode(message,klucz)));
        } else if (fileSwitch.isSelected()) {
            inputLabel.setText("");
            if(fileBytes.length != 0){
                decodeInput.setText(aes.printByteArrayInHex(aes.encode(fileBytes,klucz)));
            }
        }
        else {
            inputLabel.setText("Select input source");
        }
    }

    @FXML
    protected void handleDecodeButton(){
        if(inputSwitch.isSelected()){
            if(decodeInput.getText().isBlank()){
                inputLabel.setText("Blank input field");
                return;
            }
            inputLabel.setText("");
            byte [] encrypted = aes.convertStringToHexByteArray(decodeInput.getText());
            encodeInput.clear();
            encodeInput.setText(Other.byteArrayToString(aes.decode(encrypted,klucz)));
        } else if (fileSwitch.isSelected()) {
            inputLabel.setText("");
            byte [] encrypted = aes.convertStringToHexByteArray(decodeInput.getText());
            encodeInput.clear();
            encodeInput.setText(Other.byteArrayToString(aes.decode(encrypted,klucz)));

        }
        else {
            inputLabel.setText("Select input source");
        }
    }

    public void handleUploadButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File chooser");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        if(file!=null){
            uploadFileText.appendText(file.getAbsolutePath() + "\n");
            try{
                FileInputStream inputStream = new FileInputStream(file);
                fileBytes = inputStream.readAllBytes();
                inputStream.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            encodeInput.setText(Other.byteArrayToString(fileBytes));

        }
        else{
            uploadFileText.setPromptText("File is not valid");
            uploadFileText.setStyle("-fx-prompt-text-fill: red;");
        }
        }

    public void handleUploadButtonClickDec() {
        FileChooser fileChooser1 = new FileChooser();
        fileChooser1.setTitle("File chooser1");
        fileChooser1.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
        File file1 = fileChooser1.showOpenDialog(null);
        if(file1!=null){
            uploadFileTextDec.appendText(file1.getAbsolutePath() + "\n");
            try{
                FileInputStream inputStream1 = new FileInputStream(file1);
                fileBytes2 = inputStream1.readAllBytes();
                inputStream1.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            decodeInput.setText(Other.byteArrayToString(fileBytes2));

        }
        else{
            uploadFileTextDec.setPromptText("File is not valid");
            uploadFileTextDec.setStyle("-fx-prompt-text-fill: red;");
        }
    }

}