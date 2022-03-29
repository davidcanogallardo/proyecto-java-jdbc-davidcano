package controlador;

import java.io.IOException;

import model.Client;
import model.ClientDAO;
import model.Persistable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class List {
    private ClientDAO dao;

    @FXML
    private AnchorPane pane;
    @FXML
    private Label output;
    @FXML
    private ListView<Label> listview;

    // Elements gràfics de la UI
    private Stage ventana;

    @FXML
    private void initialize() throws IOException {
        dao = new ClientDAO();
        dao.load();
        for (Client cli : dao.getMap().values()) {
            Label label = new Label(cli.getName());
            label.setId(cli.getId().toString());
            listview.getItems().add(label);
        }
    }

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        System.out.println("seteo ventana");
        this.ventana = ventana;
    }

    public void sortir() throws IOException {
        System.out.println("cerrar");
        dao.save();
    }

    @FXML
    private void onActionSortir(ActionEvent e) throws IOException {
        System.out.println("salgo de client");
        // TODO sortir();
        dao.save();
        ventana.close();
    }

    @FXML
    private void list() {
        // System.out.println((Label) listview.getSelectionModel().getSelectedItem());
        Label selectedLabel = (Label) listview.getSelectionModel().getSelectedItem();
        selectedLabel.getId();
        // (Label)listview.getSelectionModel().getSelectedItem().get
        output.setText("");
        Client cli = dao.get(Integer.parseInt(selectedLabel.getId()));
        output.setText(output.getText() + "Nombre: " + cli.getName() + "\n");
        output.setText(output.getText() + "Apellidos: " + cli.getSurname() + "\n");
        output.setText(output.getText() + "DNI: " + cli.getDni() + "\n");
        output.setText(output.getText() + "Localidad: " + cli.getFullAddress().getLocality() + "\n");
        output.setText(output.getText() + "Provincia: " + cli.getFullAddress().getProvince() + "\n");
        output.setText(output.getText() + "CP: " + cli.getFullAddress().getZipCode() + "\n");
        output.setText(output.getText() + "Dirección: " + cli.getFullAddress().getAddress() + "\n");
        output.setText(output.getText() + "Teléfonos: " + cli.getPhoneNumber().toString() + "\n");
        // output.setText("Nombre: "+cli.getFullAddress().getLocality()+"\n");
        // output.setText("Nombre: "+cli.getFullAddress().getLocality()+"\n");
        // output.setText("Nombre: "+cli.getName()+"\n");
    }
}