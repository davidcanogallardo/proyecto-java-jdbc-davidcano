package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import model.Product;
import model.ProductDAO;
import utils.AlertWindow;
import utils.GenericFormatter;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class DiscontinuedProdController {

    private ProductDAO dao;

    @FXML
    private DatePicker date;

    private Stage ventana;

	private ResourceBundle texts;

    private ValidationSupport vs;
	private Connection conexionBD;

	public void setConexionBD(Connection bd) throws IOException {
		this.conexionBD = bd;
		dao = new ProductDAO(conexionBD);
		dao.load();
	}

    @FXML
    private void initialize() throws IOException {
        texts = GenericFormatter.getResourceBundle();

        vs = new ValidationSupport();
        vs.registerValidator(date, true, Validator.createEmptyValidator(texts.getString("alert.prodlist.date")));
    }

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }

    public void onCloseWindow() throws IOException {
        dao.save();
    }

    @FXML
    private void onActionExit(ActionEvent e) throws IOException {
        dao.save();
        ventana.close();
    }

    private boolean isDateValid() {
        if (vs.isInvalid()) {
            String errors = vs.getValidationResult().getMessages().toString();
            String title = GenericFormatter.getResourceBundle().getString("alert.title");
            String header = GenericFormatter.getResourceBundle().getString("alert.message");
            AlertWindow.show(ventana, title, header, errors);

            return false;
        }

        return true;
    }



    @FXML
    private void list() {
        if (isDateValid()) {
            List<Product> list;
            list = dao.getDiscontinuedProducts(date.getValue());
            for (Product prod : list) {
                System.out.print(texts.getString("prodlist.days"));
                System.out.println(ChronoUnit.DAYS.between(prod.getEndCatalog(), date.getValue()));
                System.out.println(prod.toString() + "\n");
            }
        }
    }

}

