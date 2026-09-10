package modelo;
import java.util.ArrayList;
import java.util.List;

public class Pessoa {

    private int id;
    private String nome;
    private List<Grupo> grupos = new ArrayList<>();
    private List<Mensagem> mensagens = new ArrayList<>();

    public Pessoa() {
    }

    public Pessoa(int id, String nome) {
        this.id = id;
        this.nome = nome;
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

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void adicionarGrupo(Grupo grupo) {
        if (!grupos.contains(grupo)) {
            grupos.add(grupo);
            grupo.adicionarPessoa(this);
        }
    }

    public void removerGrupo(Grupo grupo) {
        if (grupos.remove(grupo)) {
            grupo.removerPessoa(this);
        }
    }

    public void adicionarMensagem(Mensagem mensagem) {
        if (!mensagens.contains(mensagem)) {
            mensagens.add(mensagem);
            mensagem.setPessoa(this);
        }
    }

    public void removerMensagem(Mensagem mensagem) {
        if (mensagens.remove(mensagem)) {
            mensagem.setPessoa(null);
        }
    }

    @Override
    public String toString() {
        return "Pessoa [id=" + id + ", nome=" + nome + "]";
    }
}
