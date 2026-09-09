package modelo;
import java.util.List;
import java.util.ArrayList;

public class Grupo {
	private int id;
	private String nome;
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	public Grupo() {	
	}
	
	public Grupo(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
    public void adicionarPessoa(Pessoa pessoa) {
        pessoas.add(pessoa);
    }

    public void removerPessoa(Pessoa pessoa) {
        pessoas.remove(pessoa);
    }


}
