package com.varun.controller;


import java.io.IOException;

import com.varun.App;
import javafx.fxml.FXML;

public class PrimaryController {


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
