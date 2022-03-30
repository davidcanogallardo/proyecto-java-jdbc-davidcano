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
import model.Product;
import model.ProductDAO;
import utils.GenericFormatter;

public class ProductsMenuController {
	private ResourceBundle texts;
	private Stage ventana;
	private ProductDAO dao;

	@FXML
	private Button btnAdd;
	@FXML
	private Button btnList;
	@FXML
	private Button btnListD;
	@FXML
	private Button btnReturn;

	private Connection conexionBD;

	public void setConexionBD(Connection bd) throws IOException {
		this.conexionBD = bd;
		dao = new ProductDAO(conexionBD);
		dao.load();
	}

	@FXML
	private void initialize() throws IOException {
		texts = GenericFormatter.getResourceBundle();
		// dao.load();
	}

	public Stage getVentana() {
		return ventana;
	}

	public void setVentana(Stage ventana) {
		this.ventana = ventana;
	}

	@FXML
	private void onActionExit(ActionEvent e) throws IOException {
		ventana.close();
	}

	@FXML
	private void onAction(ActionEvent e) throws Exception {
		if (e.getSource() == btnAdd) {
			changeScene("/vista/ProductsView.fxml", texts.getString("prodform.title"));
		} else if (e.getSource() == btnList) {
			for (Product product : dao.getMap().values()) {
				System.out.println(product.toString() + "\t");
			}
		} else if (e.getSource() == btnListD) {
			changeScene("/vista/DiscontinuedProdView.fxml", "list");
		} else if (e.getSource() == btnReturn) {
			Platform.exit();
		}
	}

	private void changeScene(String path, String title) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

		texts = GenericFormatter.getResourceBundle();
		loader.setResources(texts);

		Stage stage = new Stage();
		Scene fm_scene = new Scene(loader.load());
		stage.setTitle(title);
		stage.setScene(fm_scene);
		stage.show();

		if (title.equals(texts.getString("prodform.title"))) {
			ProductsController productsAdd = loader.getController();
			productsAdd.setConexionBD(conexionBD);
			productsAdd.setVentana(stage);

			stage.setOnCloseRequest((WindowEvent we) -> {
				try {
					productsAdd.onCloseWindow();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} else if (title.equals("list")) {
			DiscontinuedProdController prod = loader.getController();
			prod.setVentana(stage);
			stage.setOnCloseRequest((WindowEvent we) -> {
				try {
					prod.onCloseWindow();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}

}
