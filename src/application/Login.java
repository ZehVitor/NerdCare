package application;

import javax.swing.JOptionPane;

import application.template.NerdCareViewBase;
import application.view.CadastroController;
import application.view.MainController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import persistence.dao.GenericDAO;
import persistence.dominio.Banco;
import persistence.dominio.Usuario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class Login extends NerdCareViewBase {

	private static Stage stage;
	
	private AnchorPane anchorPane;
	
	private TextField loginInputBox;
	private PasswordField passwordInputBox;
	private Button entrarButton;
	private Button sairButton;
	private Button cadastrarButton;
	
	private double WIDTH;
	
	private String FAREWELL_MESSAGE;
	private String FAILED_LOGIN_MESSAGE;
	
	public double middleCordinates(double componentWidth, double anchorPaneWidth) {
		return ((anchorPaneWidth - componentWidth) / 2);
	}

	private void initComponents() {
		WIDTH = 400;
		FAREWELL_MESSAGE = "Bye!";
		FAILED_LOGIN_MESSAGE = "Login ou Senha inv�lidos!";
		
		persistence.dominio.Banco.getInstance();
		
//		------------------------------------Inserts para Cria��o do banco--------------------------
//		Usuario u = new Usuario();
// 		GenericDAO dao = new GenericDAO();
// 		u.setNome("Lautter");
// 		u.setEmail("lautter@mail.com");
// 		u.setLogin("edye");
// 		u.setSenha("123");
		
// 		dao.inserir(u);
 		
		anchorPane = new AnchorPane();
		anchorPane.setPrefSize(WIDTH, 300);
		
		loginInputBox = new TextField();
		passwordInputBox = new PasswordField();
		entrarButton = new Button("Entrar");
		sairButton = new Button("Sair");
		cadastrarButton = new Button("Cadastrar");
		
		anchorPane.getChildren().addAll(loginInputBox, passwordInputBox, entrarButton, sairButton, cadastrarButton);
	}
	
	private void initLayout(){
		anchorPane.getStyleClass().add("pane");
		this.entrarButton.getStyleClass().add("btEntrar");
		this.sairButton.getStyleClass().add("btSair");
		
		loginInputBox.setPromptText("Digite aqui seu login");
		loginInputBox.setLayoutX(middleCordinates(loginInputBox.getWidth(), WIDTH));
		loginInputBox.setLayoutY(50);

		passwordInputBox.setPromptText("Digite aqui sua senha");
		passwordInputBox.setLayoutX(middleCordinates(passwordInputBox.getWidth(), WIDTH));
		passwordInputBox.setLayoutY(100);

		entrarButton.setLayoutX(middleCordinates(entrarButton.getWidth(), WIDTH));
		entrarButton.setLayoutY(150);

		sairButton.setLayoutX(middleCordinates(sairButton.getWidth(), WIDTH));
		sairButton.setLayoutY(200);
		
		cadastrarButton.setLayoutX(middleCordinates(cadastrarButton.getWidth(), WIDTH));
		cadastrarButton.setLayoutY(250);
	}
	
	private void initListeners(){
		this.entrarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				logar();		
			}
		});
		
		this.sairButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fecharAplicacao();
			}
		});
		
		this.cadastrarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastroController().start(new Stage());
					Login.stage.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		this.passwordInputBox.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                logar();
	            }
	        }
	    });
		
		this.loginInputBox.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                logar();
	            }
	        }
	    });
	}
	
	private void logar(){
		EntityManager em = Banco.getInstance();
		Usuario user = null;
		String login = loginInputBox.getText();
		String password = passwordInputBox.getText();
		
		Query qLogin = em.createQuery("Select u from Usuario as u " + "where u.login = :param");
		qLogin.setParameter("param", login);
		try {
			user = (Usuario) qLogin.getSingleResult();	
		} catch (NoResultException e) {
			user = null;
		}
		
		if (user != null && user.getLogin().equalsIgnoreCase(login) && user.getSenha().equals(password)) {
			try {
				Banco.setCurrentUser(user);
				new MainController().start(new Stage());
				Login.stage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, FAILED_LOGIN_MESSAGE);
		}
	}

	private void fecharAplicacao(){
		JOptionPane.showMessageDialog(null, FAREWELL_MESSAGE);
		persistence.dominio.Banco.closeInstance();
		System.exit(0);
	}
	
	@Override
	public void startEspecifico(Stage primaryStage) {
		try {
			initComponents();
			
			Scene scene = new Scene(anchorPane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login - NerdCare");
			primaryStage.show();

			initLayout();

			Login.stage = primaryStage;
			initListeners();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
