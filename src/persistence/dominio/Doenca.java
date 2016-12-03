package persistence.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Doenca")
public class Doenca implements Serializable {
	
	@Id
	@SequenceGenerator(name="SEQ_DOENCA", initialValue=1,
	allocationSize=1, sequenceName="seq_doenca")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DOENCA")
	@Column(name="id_Doenca")
	private int id;
	
	private String nome;
	private String sintomas;
	
	@ManyToMany(mappedBy="doencas")
	private List<Paciente> pacientes;
	
	public Doenca(){
		pacientes = new ArrayList<Paciente>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSintomas() {
		return sintomas;
	}
	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}
	public List<Paciente> getPacientes() {
		return pacientes;
	}
	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}
}
