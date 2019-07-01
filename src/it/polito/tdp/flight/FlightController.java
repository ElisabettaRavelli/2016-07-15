package it.polito.tdp.flight;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtDistanzaInput;

	@FXML
	private TextField txtPasseggeriInput;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		try {
		Double distanzaMax = Double.parseDouble(txtDistanzaInput.getText());
		this.model.creaGrafo(distanzaMax);
		txtResult.clear();
		txtResult.appendText(String.format("Grafo creato di %d vertici e %d archi\n", this.model.getVertici(), this.model.getArchi()));
		int cc = this.model.calcolaCC();
		txtResult.appendText("Numero di componenti connesse nel grafo: " + cc + "\n");
		
		Airport a = this.model.piuLondanoDaFiumicino();
		txtResult.appendText("Aeroporto più lontano raggiungibile da Fiumicino: " + a.toString() + "\n");
		
		}catch(NumberFormatException e) {
			txtResult.appendText("Si deve inserire una distanza massima\n");
		}
	}
	

	@FXML
	void doSimula(ActionEvent event) {
		
	}

	@FXML
	void initialize() {
		assert txtDistanzaInput != null : "fx:id=\"txtDistanzaInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtPasseggeriInput != null : "fx:id=\"txtPasseggeriInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Untitled'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
