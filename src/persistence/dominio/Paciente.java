package persistence.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.postgresql.jdbc2.EscapedFunctions;

@Entity
@Table(name="Paciente")
public class Paciente implements Serializable {
	
	@Id
	@SequenceGenerator(name="SEQ_PACIENTE", initialValue=1,
	allocationSize=1, sequenceName="seq_paciente")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PACIENTE")
	@Column(name="id_Paciente")
	private int id;
	
	
	@OneToMany(mappedBy="pacientes", fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private Usuario usuario;
	
	@ManyToMany
    @JoinTable(name="paciente_has_doencas", joinColumns=
    {@JoinColumn(name="id_Paciente")}, inverseJoinColumns=
      {@JoinColumn(name="id_Doenca")})
	private List<Doenca> doencas; 
	
	public Paciente(){
		doencas = new ArrayList<Doenca>();
	}
	
	private String nome;
	private String telefone;
	private String email;
	private String endereco;
	private double altura;
	private double peso;
	private boolean fumante;
	private double pressaoSanguinea;
	private String historicoFamiliar;
	private String alergias;
	private char sexo;
	private int idade;
	private String fotoUrl;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Doenca> getDoencas() {
		return doencas;
	}
	public void setDoencas(List<Doenca> doencas) {
		this.doencas = doencas;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public boolean isFumante() {
		return fumante;
	}
	public void setFumante(boolean fumante) {
		this.fumante = fumante;
	}
	public double getPressaoSanguinea() {
		return pressaoSanguinea;
	}
	public void setPressaoSanguinea(double pressaoSanguinea) {
		this.pressaoSanguinea = pressaoSanguinea;
	}
	public String getHistoricoFamiliar() {
		return historicoFamiliar;
	}
	public void setHistoricoFamiliar(String historicoFamiliar) {
		if (this.historicoFamiliar == null || this.historicoFamiliar.isEmpty()) {
			this.historicoFamiliar = historicoFamiliar;
		}
		else {
			this.historicoFamiliar += "; " + historicoFamiliar;	
		}
	}
	public String getAlergias() {
		return alergias;
	}
	public void setAlergias(String alergias) {
		if (this.alergias == null || this.alergias.isEmpty()) {
			this.alergias = alergias;
		}
		else {
			this.alergias += "; " + alergias;
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public String getFotoUrl() {
		return fotoUrl;
	}
	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}
}
