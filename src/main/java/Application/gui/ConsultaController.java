package Application.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ConsultaController implements Initializable {

    @FXML private ListView<String> listaEspera;

    @FXML private VBox painelProntuario;
    @FXML private Label lblNomePet;
    @FXML private Label lblTipoAnimal;
    @FXML private TextField campoPeso;
    @FXML private TextArea campoSintomas;
    @FXML private ListView<String> lvHistoricoClinico;
    @FXML private CheckBox chkVacinaAntirrabica;
    @FXML private Button registrarVacina;
    @FXML private Button emitirReceitaPDF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        painelProntuario.setDisable(true);
    }

    @FXML
    private void finalizarConsulta() {
        lblNomePet.setText("Paciente: Selecione um animal");
        lblTipoAnimal.setText("Tipo: -");
        campoPeso.clear();
        campoSintomas.clear();
        chkVacinaAntirrabica.setSelected(false);
        painelProntuario.setDisable(true);
    }
}
