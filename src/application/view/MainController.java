package application.view;

import application.Login;
import application.Main;
import application.template.NerdCareViewBase;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import persistence.dominio.Banco;

public class MainController extends NerdCareViewBase {

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
			MainController.stage = primaryStage;
			
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            VBox pageView = (VBox) loader.load();
            root.setCenter(pageView);
			
            listener();
            MainController.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize(){
		setScrollPane(this.scrollPane);
	}
	
	@FXML
	public void handleConsultar(){
		try {
//			new PesquisaController().start(stage);
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Pesquisa.fxml"));
            AnchorPane pageView = (AnchorPane) loader.load();
            
			scrollPane.setContent(pageView);
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
	
	public void listener(){
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                t.consume();
                persistence.dominio.Banco.closeInstance();
                stage.close();
                System.exit(0);
            }
        });
	}
}
