package application;

import javax.swing.JOptionPane;

import application.template.NerdCareViewBase;
import application.view.CadastroController;
import application.view.MainController;
import command.InsertCommand;
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
import persistence.dominio.Doenca;
import persistence.dominio.Paciente;
import persistence.dominio.Usuario;

import java.util.ArrayList;
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
		Usuario u = new Usuario();
		Paciente p = new Paciente();
		Doenca d = new Doenca();
 		GenericDAO dao = new GenericDAO();
 		List<Paciente> listPacientes = new ArrayList<Paciente>();
 		List<Doenca> listDoencas = new ArrayList<Doenca>();
// 		
//		d.setNome("Dengue");
// 		d.setSintomas("febre; dor de cabe�a; dor no corpo");
// 		
//		p.setNome("Paciente Primario");
// 		p.setAlergias("Lactose");
// 		p.setAltura(100);
// 		p.setEndereco("Rua teste");
// 		p.setFumante(true);
// 		p.setHistoricoFamiliar("Hepatite");
// 		p.setPeso(80);
// 		p.setPressaoSanguinea(120);
// 		p.setTelefone("0800 888");
//		p.setIdade(18);
//		p.setSexo('M');
 		
//		u.setNome("Edye");
// 		u.setEmail("Edye@mail.com");
// 		u.setLogin("ed");
// 		u.setSenha("123");
// 		dao.inserir(u);
// 		dao.inserir(p);
// 		dao.inserir(d);
//      ------------------ // ---------------------
// 		try {
//			u = (Usuario) dao.findById(8, Usuario.class);
//			p = (Paciente) dao.findById(1, Paciente.class);
//			d = (Doenca) dao.findById(1, Doenca.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 		listPacientes.add(p);
// 		listDoencas.add(d);
//	
// 		p.setUsuario(u);
// 		p.setDoencas(listDoencas);
// 		u.setPacientes(listPacientes);
// 		d.setPacientes(listPacientes);
//
// 		dao.alterar(d);
// 		dao.alterar(p);
// 		dao.alterar(u);
 		
		anchorPane = new AnchorPane();
		anchorPane.setPrefSize(WIDTH, 300);
		
		loginInputBox = new TextField();
		passwordInputBox = new PasswordField();
		entrarButton = new Button("Entrar");
		sairButton = new Button("Sair");
		
		anchorPane.getChildren().addAll(loginInputBox, passwordInputBox, entrarButton, sairButton);
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
