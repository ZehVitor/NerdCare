package application.view;

import application.Main;
import application.template.NerdCareViewBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CadastroController extends NerdCareViewBase {
	
	private static Stage stage;
	private BorderPane root = new BorderPane();
	
	@Override
	public void startEspecifico(Stage primaryStage) {
		try {
//			scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
//			stage.setScene(scene);
//			stage.setTitle("Cadastro de pacientes");
//			stage = primaryStage;
			
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Cadastro.fxml"));
            AnchorPane pageView = (AnchorPane) loader.load();
            BorderPane test = (BorderPane) primaryStage.getScene().getRoot();
//            root.setCenter(pageView);
            
            Object t = test.getChildren().get(0);
            test.setRight(pageView);
			
//			CadastroController.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
