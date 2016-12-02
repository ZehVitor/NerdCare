package application.view;

import application.Main;
import application.template.NerdCareViewBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PesquisaController extends NerdCareViewBase {

	private static Stage stage;
	private BorderPane root = new BorderPane();
	
	@FXML
	private ScrollPane scrollPane = new ScrollPane();
	
	@Override
	public void startEspecifico(Stage primaryStage) {
		try {
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cadastro de pacientes");
			PesquisaController.stage = primaryStage;
			
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Pesquisa.fxml"));
            ScrollPane pageView = (ScrollPane) loader.load();
            root.setCenter(pageView);
			
            PesquisaController.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize(){
		setScrollPane(this.scrollPane);
	}
	
	@FXML
	public void handlePerfilCompleto(){
		try {
//			new PerfilController().start(new Stage());
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Perfil.fxml"));
            TabPane pageView = (TabPane) loader.load();
            ScrollPane pane = getScrollPane(); 
			pane.setContent(pageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleCadastrar(){
		try {
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Cadastro.fxml"));
            AnchorPane pageView = (AnchorPane) loader.load();
            
			scrollPane.setContent(pageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
