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
        setPessoa(pessoa);
        setGrupo(grupo);
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
        if (this.pessoa != null && this.pessoa != pessoa) {
            this.pessoa.getMensagens().remove(this);
        }
        this.pessoa = pessoa;
        if (pessoa != null && !pessoa.getMensagens().contains(this)) {
            pessoa.getMensagens().add(this);
        }
    }

    public Grupo getGrupo() {
        return grupo;
    }
    
    public void setGrupo(Grupo grupo) {
        if (this.grupo != null && this.grupo != grupo) {
            this.grupo.getMensagens().remove(this);
        }
        this.grupo = grupo;
        if (grupo != null && !grupo.getMensagens().contains(this)) {
            grupo.getMensagens().add(this);
        }
    }

    @Override
    public String toString() {
        return "Mensagem [id=" + id + ", datahora=" + datahora + ", texto=" + texto +
                ", pessoa=" + (pessoa != null ? pessoa.getNome() : "null") +
                ", grupo=" + (grupo != null ? grupo.getNome() : "null") + "]";
    }
}
