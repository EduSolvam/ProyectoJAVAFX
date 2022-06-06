package es.solvam.proyectojavafx.controlador_vistas;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HomeController {

    @FXML private ImageView imInformacion;
    @FXML
    private ImageView imGuardar;
    @FXML
    private ImageView imSalir;



    public void onExitButtonClick(MouseEvent mouseEvent) {
        System.exit(0);
    }

}