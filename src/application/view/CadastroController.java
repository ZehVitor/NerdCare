package application.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import application.Main;
import application.template.NerdCareViewBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import persistence.dao.GenericDAO;
import persistence.dominio.Banco;
import persistence.dominio.Paciente;
import javafx.stage.FileChooser.ExtensionFilter;

public class CadastroController extends NerdCareViewBase {
	
	private static Stage stage;
	private Paciente pacienteAtual = new Paciente();
	@FXML
	private ImageView imageFoto;
	@FXML
	private TextField tfNome;
	@FXML
	private TextField tfIdade;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfSexo;
	@FXML
	private TextField tfAltura;
	@FXML
	private TextField tfPeso;
	@FXML
	private RadioButton radioFumanteSim;
	@FXML
	private RadioButton radioFumanteNao;
	private ToggleGroup group = new ToggleGroup();
	@FXML
	private TextField tfPressaoSanguinea;
	@FXML
	private TextField tfHistoricoFamiliar;
	@FXML
	private TextField tfAlergias;
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
	
	@FXML
	public void initialize(){
		this.radioFumanteSim.setToggleGroup(this.group);
		this.radioFumanteNao.setToggleGroup(this.group);
	}
	
	@FXML
	public void handleAdicionarFoto(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Picture File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Picture Files", "*.jpg", "*.png", "*.gif", "*.jpeg"));
		 File selectedFile = fileChooser.showOpenDialog(stage);
		 if (selectedFile != null) {
			 String formatar = selectedFile.getAbsolutePath();
			 formatar = formatar.replace('\\', '/');
			 
			 formatar = new File(formatar).toURI().toString();
			 this.imageFoto.setImage(new Image(formatar));	
		 }
	}
	
	@FXML
	public void handleAdicionarPaciente(){
		if (!this.validaCampos()) {
			return;
		}
		
		GenericDAO dao = new GenericDAO();
		this.pacienteAtual.setNome(this.tfNome.getText());
		this.pacienteAtual.setIdade(Integer.parseInt(this.tfIdade.getText()));
		this.pacienteAtual.setEmail(this.tfEmail.getText());
		char sexo = this.tfSexo.getText().equalsIgnoreCase("Masculino") ||
					this.tfSexo.getText().equalsIgnoreCase("Homem") ? 'M' :
					this.tfSexo.getText().equalsIgnoreCase("Feminino") ||	
					this.tfSexo.getText().equalsIgnoreCase("Mulher") ? 'F' : ' ';
		this.pacienteAtual.setSexo(sexo);
		this.pacienteAtual.setAltura(Double.parseDouble(this.tfAltura.getText()));
		this.pacienteAtual.setPeso(Double.parseDouble(this.tfPeso.getText()));
		this.pacienteAtual.setFumante(this.radioFumanteSim.isSelected() ? true : false);
		this.pacienteAtual.setPressaoSanguinea(Double.parseDouble(this.tfPressaoSanguinea.getText()));
		List<Paciente> listP = new ArrayList<Paciente>();
		listP.add(this.pacienteAtual);
		this.pacienteAtual.setUsuario(Banco.getCurrentUser());
		this.pacienteAtual.setFotoUrl(this.imageFoto.getImage().impl_getUrl());
		
		dao.inserir(this.pacienteAtual);
		JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
		reset();
	}
	
	@FXML
	public void handleAdcionarAlergia(){
		this.pacienteAtual.setAlergias(this.tfAlergias.getText());
	}
	
	@FXML
	public void handleDoencaCronica(){
		
	}
	
	private boolean validaCampos(){
		StringBuilder sb = new StringBuilder();
		sb.append("Ocorreram os seguintes erros:");
		sb.append("");
		try {
			int idade = Integer.parseInt(this.tfIdade.getText());
		} catch (Exception e) {
			sb.append("Campo idade incorreto.");
			sb.append("");
		}
		
		try {
			double altura = Double.parseDouble(this.tfAltura.getText());
		} catch (Exception e) {
			sb.append("Campo Altura incorreto.");
			sb.append("");
		}
		
		try {
			double peso = Double.parseDouble(this.tfPeso.getText());
		} catch (Exception e) {
			sb.append("Campo Peso incorreto.");
			sb.append("");
		}
		
		try {
			double pressaoSanguinea = Double.parseDouble(this.tfPressaoSanguinea.getText());
		} catch (Exception e) {
			sb.append("Campo Pressão Sanguinea incorreto.");
			sb.append("");
		}
		
		if (sb.length() > 30) {
			JOptionPane.showMessageDialog(null, sb.toString());
			return false;
		}
		
		return true;
	}
	
	private void reset(){
		this.imageFoto = new ImageView();
		this.tfNome = new TextField();
		this.tfIdade = new TextField();
		this.tfEmail = new TextField();
		this.tfSexo = new TextField();
		this.tfAltura = new TextField();
		this.radioFumanteSim = new RadioButton();
		this.radioFumanteNao = new RadioButton();
		this.tfPressaoSanguinea = new TextField();
		this.tfHistoricoFamiliar = new TextField();
		this.tfAlergias = new TextField();
	}
}
