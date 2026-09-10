package modelo;
import java.util.List;
import java.util.ArrayList;

public class Grupo {
	private int id;
	private String nome;
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private List<Mensagem> mensagens = new ArrayList<Mensagem>();

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

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

    // relacionamento N:N com Pessoa - uma chamada ja sincroniza os dois lados
    public void adicionarPessoa(Pessoa pessoa) {
        if (!pessoas.contains(pessoa)) {
            pessoas.add(pessoa);
            pessoa.adicionarGrupo(this);
        }
    }

    public void removerPessoa(Pessoa pessoa) {
        if (pessoas.remove(pessoa)) {
            pessoa.removerGrupo(this);
        }
    }

    // relacionamento 1:N com Mensagem (quem "manda" eh Mensagem.setGrupo)
    public void adicionarMensagem(Mensagem mensagem) {
        if (!mensagens.contains(mensagem)) {
            mensagens.add(mensagem);
            mensagem.setGrupo(this);
        }
    }

    public void removerMensagem(Mensagem mensagem) {
        if (mensagens.remove(mensagem)) {
            mensagem.setGrupo(null);
        }
    }

    @Override
    public String toString() {
        return "Grupo [id=" + id + ", nome=" + nome + "]";
    }

}
