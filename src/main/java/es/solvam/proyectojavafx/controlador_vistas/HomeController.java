package es.solvam.proyectojavafx.controlador_vistas;

import es.solvam.proyectojavafx.modelos.Persona;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML private ImageView imInformacion;
    @FXML private ImageView imGuardar;
    @FXML private ImageView imSalir;
    @FXML private ImageView imAtras;
    @FXML private ImageView imDelante;
    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private RadioButton rbHombre;
    @FXML private RadioButton rbMujer;
    @FXML private DatePicker dtFechaNacimiento;
    @FXML private ComboBox<String> cbOcupacion;
    @FXML private CheckBox chTecnologia;
    @FXML private CheckBox chDiseno;
    @FXML private CheckBox chConsultoria;
    @FXML private CheckBox chFormacion;
    @FXML private ImageView imFoto;


    public void onExitButtonClick(MouseEvent mouseEvent) {
        // copia de seguridad
        try {
            copiaSeguridad();
        } catch (IOException e) {
            e.printStackTrace();
            lanzaAlert("ERROR", "Error al escribir la copia de seguridad");
        }
        System.exit(0);
    }

    private final String rutaCopiaSeguridad = "src/main/resources/es/solvam/proyectojavafx/backup/backup.bck";
    private void copiaSeguridad() throws IOException {
        File copia = new File(rutaCopiaSeguridad);
        FileWriter fileWriter = new FileWriter(copia, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Persona p: personas) {
            printWriter.println(p.modelo2Fichero());
        }
        printWriter.close();
        fileWriter.close();
    }

    public void onImFotoClicked(MouseEvent mouseEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");

        // Agregar filtros para los ficheros
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PGN", "*.png")
        );

        imgFile = fileChooser.showOpenDialog(null);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
            File destino = new File("src/main/resources/es/solvam/proyectojavafx/imagenes_user/"+sdf.format(new Date()) +"-"+imgFile.getName());
             Files.copy(imgFile.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
             imgFile = destino;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (imgFile != null) {
            Image imagen = new Image(imgFile.toURI().toString());
            imFoto.setImage(imagen);
        }
    }

    public void bthGuardarClicked(MouseEvent mouseEvent) {
        // Crear una Persona
        Persona persona = new Persona();
        // Asignar Valores
        persona.setDni(txtDni.getText());
        persona.setNombre(txtNombre.getText());
        persona.setTelefono(txtTelefono.getText());
        persona.setImagen(imgFile.getPath());
        persona.setSexo(rbHombre.isSelected());
        persona.setFechaNacimiento(dtFechaNacimiento.getValue());
        persona.setOcupacion(cbOcupacion.getValue());
        persona.setConsultoria(chConsultoria.isSelected());
        persona.setDiseno(chDiseno.isSelected());
        persona.setTecnologia(chTecnologia.isSelected());
        persona.setFormacion(chFormacion.isSelected());
        // Agregar a la Lista
        personas.add(persona);
        lanzaAlert( "Persona Insertada", "Se ha insertado la persona.\nSe limpiará el Formulario");
        // Limpiar Form
        //limpiarForm();
        personaActual++;
        imAtras.setVisible(true);
    }

    private void lanzaAlert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarForm() {
        txtDni.clear();
        txtNombre.clear();
        txtTelefono.clear();
        rbMujer.setSelected(false);
        rbHombre.setSelected(false);
        dtFechaNacimiento.setValue(null);
        cbOcupacion.setValue(null);
        chConsultoria.setSelected(false);
        chFormacion.setSelected(false);
        chDiseno.setSelected(false);
        chTecnologia.setSelected(false);
        File img = new File("src/main/resources/es/solvam/proyectojavafx/imagenes/user.png");
        imFoto.setImage(new Image(img.toURI().toString()));
    }


    private File imgFile;
    private ArrayList<Persona> personas;
    // Rellenar el Combo -> Con una colección de Strings
    ObservableList<String> contenidoComboOcupacion = FXCollections.observableArrayList("Estudiante", "Empleado", "Autónomo", "Jubilado");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbOcupacion.setItems(contenidoComboOcupacion);

        ToggleGroup grupo = new ToggleGroup();
        rbHombre.setToggleGroup(grupo);
        rbMujer.setToggleGroup(grupo);

        personas = new ArrayList<>();

        // Inicializar los datos del fichero
        try {
            cargarDatos();
        } catch (FileNotFoundException e) {
            lanzaAlert("ERROR", "ERROR AL LEER EL FICHERO");
            e.printStackTrace();
        } catch (IOException e) {
            lanzaAlert("ERROR", "ERROR AL LEER EL FICHERO");
            throw new RuntimeException(e);
        }
    }

    private void cargarDatos() throws IOException {
        File copia = new File(rutaCopiaSeguridad);
        FileReader fileReader = new FileReader(copia);
        BufferedReader bf = new BufferedReader(fileReader);
        String linea;
        while ( (linea = bf.readLine()) != null ){
            Persona p = new Persona(linea);
            personas.add(p);
        }
        bf.close();
        fileReader.close();
        lanzaAlert("Datos Cargados", "Se han cargado "+personas.size()+" personas");
        if (personas.size() > 0) {
            rellenaDatos(personas.get(0));
            imAtras.setVisible(false);
            personaActual = 0;
            if (personaActual == personas.size()-1) {
                imDelante.setVisible(false);
            }
        }
        if (personas.size() == 0){
            imDelante.setVisible(false);
            imAtras.setVisible(false);
        }
    }

    // Mantiene el indice del arrayList de la persona mostrada
    private int personaActual = -1;

    private void rellenaDatos(Persona persona) {
        txtDni.setText(persona.getDni());
        txtNombre.setText(persona.getNombre());
        txtTelefono.setText(persona.getTelefono());
        rbHombre.setSelected(persona.isSexo());
        rbMujer.setSelected(!persona.isSexo());
        dtFechaNacimiento.setValue(persona.getFechaNacimiento());
        cbOcupacion.setValue(persona.getOcupacion());
        chTecnologia.setSelected(persona.isTecnologia());
        chDiseno.setSelected(persona.isDiseno());
        chFormacion.setSelected(persona.isFormacion());
        chConsultoria.setSelected(persona.isConsultoria());
        File file = new File(persona.getImagen());
        imFoto.setImage(new Image(file.toURI().toString()));
    }

    public void atrasClicked(MouseEvent mouseEvent) {
        personaActual--;
        rellenaDatos(personas.get(personaActual));
        imDelante.setVisible(true);
        if (personaActual == 0) {
            imAtras.setVisible(false);
        }
    }

    public void delanteClicked(MouseEvent mouseEvent) {
        personaActual++;
        rellenaDatos(personas.get(personaActual));
        imAtras.setVisible(true);
        if (personaActual == personas.size()-1){
            imDelante.setVisible(false);
        }
    }
}