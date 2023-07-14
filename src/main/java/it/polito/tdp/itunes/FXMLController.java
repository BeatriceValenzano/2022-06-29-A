/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	
    	txtResult.clear();
    	Album a1 = this.cmbA1.getValue();
    	if(a1 == null) {
    		txtResult.appendText("Selezionare un album valido!");
    	} else {
    		for(Album a : model.listaSuccessori(a1)) {
    			txtResult.appendText(a.toString() + ", Bilancio = " + a.getBilancio() + "\n");
    		}
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	txtResult.clear();
    	try {
    		int x = Integer.parseInt(this.txtX.getText());
    		Album partenza = this.cmbA1.getValue();
    		Album arrivo = this.cmbA2.getValue();
    		if(partenza == null || arrivo == null) {
    			txtResult.appendText("Selezionare degli album validi!");
    		}
    		List<Album> percorso = model.percorsoMigliore(x, partenza, arrivo);
    		if(percorso.size() == 0) {
    			txtResult.appendText("Non esiste un percorso tra " + partenza + " e " + arrivo);
    		}
    		for(Album a : percorso) {
    			txtResult.appendText(a.toString() + "; Bilancio= " + a.getBilancio() + "\n");
    		}
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un numero valido!");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
    	this.cmbA1.getItems().clear();
    	try {
    		int n = Integer.parseInt(this.txtN.getText());
    		model.creaGrafo(n);
    		txtResult.appendText("Grafo creato!\n");
    		txtResult.appendText("# Vertici: " + model.vertici() + "\n# Archi: " + model.archi());
    		this.cmbA1.getItems().addAll(model.getListaVertici());
    		this.cmbA2.getItems().addAll(model.getListaVertici());
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un numero intero!");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}
