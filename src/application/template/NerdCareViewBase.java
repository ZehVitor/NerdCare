package application.template;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class NerdCareViewBase extends Application {

	private static Stage stage;
	private BorderPane root = new BorderPane();
	private static ScrollPane scrollPane;
	
	@Override
	public void start(Stage primaryStage) {
		try {
//			Scene scene = new Scene(root,400,400);
//			try {
//				scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());	
//			} catch (Exception e) {
//				scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
//			}
			
//			primaryStage.setScene(scene);
//			primaryStage.setResizable(false);

			NerdCareViewBase.stage = primaryStage;
//			listener();
			
			startEspecifico(NerdCareViewBase.stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void startEspecifico(Stage primaryStage);

	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(ScrollPane scrollPane) {
		NerdCareViewBase.scrollPane = scrollPane;
	}
}
