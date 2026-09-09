package modelo;

public class Mensagem {
	private int id;
	private String datahora;
	private String texto;
	private Pessoa pessoa;
	private Grupo grupo;
	
	public Mensagem() {
	}

	public Mensagem(int id, String datahora, String texto, Pessoa pessoa, Grupo grupo) {
        this.id = id;
        this.datahora = datahora;
        this.texto = texto;
        this.pessoa = pessoa;
        this.grupo = grupo;
    }
    
	public int getId() {
    	return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getDataHora() {
    	return datahora;
    }
    
    public void setDataHora(String datahora) {
    	this.datahora = datahora;
    }
    
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public Pessoa getPessoa() {
    	return pessoa;
    }
    
    public void setPessoa(Pessoa pessoa) {
    	this.pessoa = pessoa;
    }
    
    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}


	
	

