package application.view;

import application.Main;
import application.template.NerdCareViewBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PerfilController extends NerdCareViewBase {

	private static Stage stage;
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
            AnchorPane pageView = (AnchorPane) loader.load();
            root.setCenter(pageView);
			
            PerfilController.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
