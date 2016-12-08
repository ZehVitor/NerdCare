package application.view;

import application.Main;
import application.template.NerdCareViewBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import persistence.dominio.Doenca;
import persistence.dominio.Paciente;

public class PerfilController extends NerdCareViewBase {

	private static Stage stage;
	private static Paciente pacienteSelecionado;
	@FXML
	private Label lbNome;
	@FXML
	private Label lbIdade;
	@FXML
	private Label lbSexo;
	@FXML
	private Label lbMail;
	@FXML
	private Label lbAltura;
	@FXML
	private Label lbPeso;
	@FXML
	private Label lbFumante;
	@FXML
	private Label lbPressaoSanguinea;
	@FXML
	private Label lbHistoricoFamiliar;
	@FXML
	private Label lbDoencas;
	@FXML
	private Label lbAlergias;
	@FXML
	private ImageView fotoUrl;
	
	private BorderPane root = new BorderPane();
	
	@Override
	public void startEspecifico(Stage primaryStage) {
		try {
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cadastro de pacientes");
			PerfilController.stage = primaryStage;
			
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Perfil.fxml"));
            TabPane pageView = (TabPane) loader.load();
            root.setCenter(pageView);
			
            PerfilController.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize(){
		if (pacienteSelecionado != null) {
			this.lbNome.setText(pacienteSelecionado.getNome());
			this.lbIdade.setText(String.valueOf(pacienteSelecionado.getIdade()));
			this.lbSexo.setText(pacienteSelecionado.getSexo() == 'M' ? "Masculino" : "Feminino");
			this.lbMail.setText(pacienteSelecionado.getEmail());
			this.lbAltura.setText(String.valueOf(pacienteSelecionado.getAltura()));
			this.lbPeso.setText(String.valueOf(pacienteSelecionado.getPeso()));
			this.lbFumante.setText(pacienteSelecionado.isFumante() ? "Sim" : "Não");
			this.lbPressaoSanguinea.setText(String.valueOf(pacienteSelecionado.getPressaoSanguinea()));
			this.lbHistoricoFamiliar.setText(pacienteSelecionado.getHistoricoFamiliar());
			this.lbAlergias.setText(pacienteSelecionado.getAlergias());
			StringBuilder sb = new StringBuilder();
			for (Doenca doenca : pacienteSelecionado.getDoencas()) {
				sb.append(doenca.getNome());
				sb.append("; ");
			}
			
			this.lbDoencas.setText(sb.toString());
			if (pacienteSelecionado.getFotoUrl() == null || pacienteSelecionado.getFotoUrl().isEmpty()) {
				this.fotoUrl.setImage(new Image("/resource/images/sem_foto.png"));
			}
			else {
				this.fotoUrl.setImage(new Image(pacienteSelecionado.getFotoUrl()));
			}
		}
	}
	
	public void getPacienteSelecionado(Paciente paciente){
		pacienteSelecionado = paciente;
	}
	
	@FXML
	public void handleNovaConsulta(){
		try {
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Pesquisa.fxml"));
            ScrollPane pageView = (ScrollPane) loader.load();
            
            ScrollPane pane = getScrollPane(); 
            pane.setContent(pageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
