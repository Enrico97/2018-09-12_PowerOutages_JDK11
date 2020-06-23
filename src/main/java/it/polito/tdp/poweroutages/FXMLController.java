package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.Vicini;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Nerc> cmbBoxNerc;

    @FXML
    private Button btnVisualizzaVicini;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	model.creaGrafo();
    	txtResult.appendText("grafo creato");

    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	try {
    		int x = (Integer.parseInt(txtK.getText()));
    		model.simula(x);
    		txtResult.appendText("le catastrofi sono: "+model.getCatastrofi()+"\n"+model.getBonus());
    	} catch(NumberFormatException e) {
    		txtResult.appendText("inserire un numero di mesi corretto");
    	}
    	
    }

    @FXML
    void doVisualizzaVicini(ActionEvent event) {

    	txtResult.clear();
    	if(cmbBoxNerc.getValue()!=null) {
    		for(Vicini v : model.vicini(cmbBoxNerc.getValue()))
    			txtResult.appendText(v.toString()+"\n");
    }
    	else
    		txtResult.appendText("selezionare un nerc");
    }
    
    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert cmbBoxNerc != null : "fx:id=\"cmbBoxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnVisualizzaVicini != null : "fx:id=\"btnVisualizzaVicini\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		cmbBoxNerc.getItems().addAll(model.loadAllNercs());
	}
}
