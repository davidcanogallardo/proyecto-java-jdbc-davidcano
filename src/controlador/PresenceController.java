package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import model.Presence;
import model.PresenceRegisterDAO;
import utils.AlertWindow;
import utils.GenericFormatter;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PresenceController {

    private PresenceRegisterDAO dao;

    @FXML
    private TextField guiId;

    @FXML
    private Button guiClockIn;
    @FXML
    private Button guiClockOut;

    private Stage ventana;

    private ValidationSupport vs;
    private ResourceBundle texts;

	private Connection con;

	public void setDBConnection(Connection bd) throws IOException {
		this.con = bd;
		dao = new PresenceRegisterDAO(con);
		dao.load();
	}
    @FXML
    private void initialize() throws IOException {
        texts = GenericFormatter.getResourceBundle();

        vs = new ValidationSupport();
        vs.registerValidator(guiId, true, Validator.createEmptyValidator(texts.getString("alert.presence.id")));
    }

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }

    public void sortir() throws IOException {
        dao.save();
    }

    @FXML
    private boolean isDatosValidos() {
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
    private void onActionSortir(ActionEvent e) throws IOException {
        dao.save();
        ventana.close();
    }

    @FXML
    private void onAction(ActionEvent e) throws Exception {
        if (e.getSource() == guiClockIn) {
            if (isDatosValidos()) {
                Presence presence = new Presence(Integer.parseInt(guiId.getText()), LocalDateTime.now());
                if (dao.add(presence) == null) {
                    AlertWindow.show(ventana, "Error", texts.getString("alert.presence.clockin"), "");
                }
            }
        } else if (e.getSource() == guiClockOut) {
            if (isDatosValidos()) {
                if (!dao.addLeaveTime(Integer.parseInt(guiId.getText()))) {
                    AlertWindow.show(ventana, "Error2", texts.getString("alert.presence.clockout"), "");
                }
            }
        }
        System.out.println("--------------------------");
        dao.list();
        System.out.println("--------------------------");
    }

    @FXML
    private void list() {
        dao.list();
    }

}
