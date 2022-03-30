package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.util.ResourceBundle;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Presence;
import model.PresenceRegisterDAO;
import utils.GenericFormatter;

public class PresenceMenuController {
	private ResourceBundle texts;
	private PresenceRegisterDAO dao;
	private Stage ventana;

	@FXML
	private Button btnClockInOut;
	@FXML
	private Button btnList;
	@FXML
	private Button btnReturn;

	private Connection conexionBD;

	public void setConexionBD(Connection bd) throws IOException {
		this.conexionBD = bd;
		dao = new PresenceRegisterDAO(conexionBD);
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
		if (e.getSource() == btnClockInOut) {
			System.out.println("products view");
			changeScene("/vista/PresenceView.fxml", "Fichar");
		} else if (e.getSource() == btnList) {
			for (Presence presence : dao.getMap()) {
				System.out.println(presence.toString() + "\n");
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

		if (title.equals("Fichar")) {
			PresenceController presenceAdd = loader.getController();
			presenceAdd.setConexionBD(conexionBD);
			presenceAdd.setVentana(stage);

			stage.setOnCloseRequest((WindowEvent we) -> {
				try {
					presenceAdd.sortir();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}

}
