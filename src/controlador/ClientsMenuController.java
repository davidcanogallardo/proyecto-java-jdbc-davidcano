package controlador;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Address;
import model.Client;
import model.ClientDAO;
import utils.GenericFormatter;

public class ClientsMenuController {

	private ClientDAO dao;

	private ResourceBundle texts;
	private Stage ventana;

	@FXML
	private Button btnAdd;
	@FXML
	private Button btnList;
	@FXML
	private Button btnReturn;

	private Connection con;

	public void setDBConnection(Connection con) throws IOException {
		this.con = con;
		dao = new ClientDAO(con);
		dao.load();
	}

	@FXML
	private void initialize() throws IOException {
		texts = GenericFormatter.getResourceBundle();
	}

	public Stage getVentana() {
		return ventana;
	}

	public void setVentana(Stage ventana) {
		this.ventana = ventana;
	}

	@FXML
	private void onActionSortir(ActionEvent e) throws IOException {
		ventana.close();
	}

	@FXML
	private void onAction(ActionEvent e) throws Exception {
		if (e.getSource() == btnAdd) {
			changeScene("/vista/ClientsView.fxml", texts.getString("clientform.title"));
		} else if (e.getSource() == btnList) {
			for (Client client : dao.getPackMap().values()) {
				System.out.println(client.toString() + "\n");
			}
		} else if (e.getSource() == btnReturn) {
			Platform.exit();
		}
	}

	private void changeScene(String path, String title) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

		loader.setResources(texts);

		Stage stage = new Stage();
		Scene fm_scene = new Scene(loader.load());
		stage.setTitle(title);
		stage.setScene(fm_scene);
		stage.show();

		if (title.equals(texts.getString("clientform.title"))) {
			ClientsController clientsAdd = loader.getController();
			clientsAdd.setDBConnection(con);
			clientsAdd.setVentana(stage);

			stage.setOnCloseRequest((WindowEvent we) -> {
				try {
					clientsAdd.sortir();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}

}
