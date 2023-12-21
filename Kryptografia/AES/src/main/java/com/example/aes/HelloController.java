package com.example.aes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.aes.AES;
import com.example.aes.Other;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.stage.FileChooser;

public class HelloController {

    @FXML
    private TextArea uploadFileTextDec;

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

    private boolean encodeFileOK = false;

    private boolean decodeFileOK = false;

    private byte[] klucz = new byte[16];

    private byte[] inputBytes;

    private byte[] outputEncoded;
    private byte[] outputDecoded;

    private byte[] inputBytesEncoded;


    private byte[] message;

    @FXML
    protected void initialize() {
        this.aes = new AES(4, 10);
    }

    @FXML
    protected void onGenerateButtonClick() {
        if (keyField.getText().isBlank()) {
            this.klucz = aes.generateRandomMainKey();
            keyField.setText(Other.printByteArrayInHex(klucz));
        } else {
            if (Other.checkKeyString(keyField.getText())) {
                this.klucz = Other.convertKeyStringToHexByteArray(keyField.getText());
                keyField.setText(Other.printByteArrayInHex(klucz));
            } else {
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
    protected void handleEncodeButton() {
        if (inputSwitch.isSelected()) {
            if (encodeInput.getText().isBlank()) {
                inputLabel.setText("Blank input field");
                return;
            }
            inputLabel.setText("");
            message = Other.stringToByteArray(encodeInput.getText());
            outputEncoded = aes.encode(message, klucz);
            decodeInput.setText(Other.printByteArrayInHex(outputEncoded));
        } else if (fileSwitch.isSelected()) {
            inputLabel.setText("");
            if (inputBytes.length != 0 && encodeFileOK) {
                inputLabel.setText("");
                outputEncoded = aes.encode(inputBytes, klucz);
                decodeInput.setText(Other.printByteArrayInHex(outputEncoded));
            } else {
                inputLabel.setText("File not loaded");
            }
        } else {
            inputLabel.setText("Select input source");
        }
    }

    @FXML
    protected void handleDecodeButton() {
        if (inputSwitch.isSelected()) {
            if (decodeInput.getText().isBlank()) {
                inputLabel.setText("Blank input field");
                return;
            }
            inputLabel.setText("");
            byte[] encrypted = Other.convertStringToHexByteArray(decodeInput.getText());
            encodeInput.clear();
            outputDecoded = aes.decode(encrypted, klucz);
            encodeInput.setText(Other.byteArrayToString(outputDecoded));
        } else if (fileSwitch.isSelected()) {
            inputLabel.setText("");
            if (inputBytesEncoded.length != 0 && decodeFileOK) {
                inputLabel.setText("");
                encodeInput.clear();
                outputDecoded = aes.decode(inputBytesEncoded, klucz);
                encodeInput.setText(Other.byteArrayToString(outputDecoded));
            } else {
                inputLabel.setText("File not loaded");
            }
        } else {
            inputLabel.setText("Select input source");
        }
    }

    @FXML
    public void handleUploadButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File chooser");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            uploadFileText.appendText(file.getAbsolutePath() + "\n");
            try {
                FileInputStream inputStream = new FileInputStream(file);
                inputBytes = inputStream.readAllBytes();
                inputStream.close();
                encodeFileOK = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            encodeInput.setText(Other.byteArrayToString(inputBytes));

        } else {
            uploadFileText.setPromptText("File is not valid");
            uploadFileText.setStyle("-fx-prompt-text-fill: red;");
        }
    }

    @FXML
    public void handleUploadButtonClickDec() {
        FileChooser fileChooser1 = new FileChooser();
        fileChooser1.setTitle("File chooser1");
        fileChooser1.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
        File file1 = fileChooser1.showOpenDialog(null);
        if (file1 != null) {
            uploadFileTextDec.appendText(file1.getAbsolutePath() + "\n");
            try {
                FileInputStream inputStream1 = new FileInputStream(file1);
                inputBytesEncoded = inputStream1.readAllBytes();
                inputStream1.close();
                decodeFileOK = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            decodeInput.setText(Other.printByteArrayInHex(inputBytesEncoded));

        } else {
            uploadFileTextDec.setPromptText("File is not valid");
            uploadFileTextDec.setStyle("-fx-prompt-text-fill: red;");
        }
    }

    @FXML
    protected void handleSaveButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(outputDecoded);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleSaveButtonDec() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files(*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(outputEncoded);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleSaveKey() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files(*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(klucz);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleUploadKey() {
        FileChooser fileChooser1 = new FileChooser();
        fileChooser1.setTitle("Upload key");
        fileChooser1.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        File file1 = fileChooser1.showOpenDialog(null);
        if (file1 != null) {
            try {
                FileInputStream inputStream1 = new FileInputStream(file1);
                byte[] tmp = inputStream1.readAllBytes();
                if (tmp.length != 16) {
                    keyField.setText("");
                    keyField.setPromptText("The given key is not 32 characters");
                    keyField.setStyle("-fx-prompt-text-fill: red;");
                } else {
                    this.klucz = tmp;
                    keyField.setText(Other.printByteArrayInHex(klucz));
                }
                inputStream1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}