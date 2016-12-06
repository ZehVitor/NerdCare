package application.view;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import application.template.NerdCareViewBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import persistence.dao.GenericDAO;
import persistence.dominio.Banco;
import persistence.dominio.Paciente;

public class PesquisaController extends NerdCareViewBase {

	private static Stage stage;
	@FXML
	private Label lbNome;
	@FXML
	private Label lbIdade;
	@FXML
	private Label lbSexo;
	@FXML
	private TextField txtPesquisa;
	@FXML
	private Button btAdicionar;
	@FXML
	private TableView<String> tbHistorico;
	@FXML
	private ChoiceBox cbAtributoPesquisa;
	@FXML
	private ImageView fotoUrl;
	private Paciente pacienteSelecionado;
	
	private BorderPane root = new BorderPane();
	
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
            AnchorPane pageView = (AnchorPane) loader.load();
            root.setCenter(pageView);
			
            PesquisaController.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handlePerfilCompleto(){
		try {
//			new PerfilController().start(new Stage()); // Para criar um PopUp da janela de Perfil
			new PerfilController().getPacienteSelecionado(this.pacienteSelecionado);
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
	public void initialize(){
		this.lbNome.setText("-");
		this.lbIdade.setText("-");
		this.lbSexo.setText("-");
		this.cbAtributoPesquisa.setItems(FXCollections.observableArrayList("Nome", "Idade", "Sexo"));
	}
	
	@FXML
	public void handlePesquisar(){
		String pesquisar = this.txtPesquisa.getText() == null ? "" : this.txtPesquisa.getText().toLowerCase();
		List<Paciente> foundItems = new ArrayList<Paciente>();
		String buscarPor = (String) this.cbAtributoPesquisa.getSelectionModel().getSelectedItem();
		buscarPor = buscarPor == null ? "" : buscarPor;
		GenericDAO dao = new GenericDAO();
		List<Paciente> pacientes = new ArrayList<Paciente>();
		try {
			// Criar DAO espec�fico e fazer consulta via Like %
			pacientes = dao.findAll(Paciente.class);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		switch (buscarPor) {
		case "Nome":
			for (Paciente p : pacientes) {
				String nome = p.getNome() ==  null ? "" : p.getNome().toLowerCase();
				if (nome.contains(pesquisar)) {
					foundItems.add(p);
				}
			}
			break;
		case "Idade":
			int idade = 0;
			try {
				idade = Integer.parseInt(pesquisar);
			} catch (Exception e) { idade = 0; }
			
			for (Paciente p : pacientes) {
				if (p.getIdade() == idade) {
					foundItems.add(p);
				}
			}
			break;
		case "Sexo":
			char sexo = pesquisar.equalsIgnoreCase("Masculino") ||
						pesquisar.equalsIgnoreCase("Homem") ? 'M' :
						pesquisar.equalsIgnoreCase("Feminino") ||	
						pesquisar.equalsIgnoreCase("Mulher") ? 'F' : ' ';
			for (Paciente p : pacientes) {
				if (p.getSexo() == sexo) {
					foundItems.add(p);
				}
			}
			break;
		default:
			for (Paciente p : pacientes) {
				foundItems.add(p);
			}
			break;
		}

		initPaciente(foundItems);
	}
	
	public void initPaciente(List<Paciente> pacientes){
		if (pacientes == null || pacientes.size() <= 0) {
			return;
		}
		
		this.pacienteSelecionado = pacientes.get(0);
		this.lbNome.setText(this.pacienteSelecionado.getNome());
		this.lbIdade.setText(String.valueOf(this.pacienteSelecionado.getIdade()));
		this.lbSexo.setText(this.pacienteSelecionado.getSexo() == 'M' ? "Masculino" : "Feminino");
		if (this.pacienteSelecionado.getFotoUrl() == null || this.pacienteSelecionado.getFotoUrl().isEmpty()) {
			this.fotoUrl.setImage(new Image("/resource/images/sem_foto.png"));
		}
		else {
			this.fotoUrl.setImage(new Image(this.pacienteSelecionado.getFotoUrl()));
		}
		
		String[] historico = this.pacienteSelecionado.getHistoricoFamiliar().split(";");
		ObservableList<String> listHistorico = FXCollections.observableArrayList();
		for (int i = 0; i < historico.length; i++) {
			listHistorico.add(historico[i]);
		}
		
		this.tbHistorico.setItems(listHistorico);
	}
}
