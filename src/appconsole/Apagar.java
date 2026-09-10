package appconsole;

import java.util.ArrayList;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import Util.Util;
import modelo.Grupo;
import modelo.Mensagem;
import modelo.Pessoa;

// Apaga um objeto que possui relacionamentos: o Grupo "Projeto Final", que
// tem membros (Pessoa <-> Grupo, N:N) e mensagens vinculadas.
//
// OBJETOS ORFAOS - se o grupo fosse apagado direto (manager.delete(grupo)):
// 1) cada Pessoa membro ficaria com uma referencia, em pessoa.getGrupos(),
//    para um Grupo que nao existe mais no banco;
// 2) cada Mensagem do grupo ficaria com mensagem.getGrupo() apontando para
//    um objeto inexistente, e o autor ficaria com uma Mensagem "fantasma"
//    em pessoa.getMensagens().
// Por isso cascadeOnDelete esta desligado em Util.java e os relacionamentos
// sao desfeitos manualmente antes de cada delete.
public class Apagar {

    public static void main(String[] args) {
        Util.conectar();
        ObjectContainer manager = Util.getManager();

        try {
            System.out.println("=== APAGANDO OBJETO COM RELACIONAMENTOS ===\n");

            Query consulta = manager.query();
            consulta.constrain(Grupo.class);
            consulta.descend("nome").constrain("Projeto Final");
            List<Grupo> resultado = consulta.execute();

            if (resultado.isEmpty()) {
                System.out.println("Grupo 'Projeto Final' nao encontrado. Execute o Cadastrar antes.");
                return;
            }

            Grupo grupo = resultado.get(0);
            System.out.println("Grupo a ser apagado: " + grupo);
            System.out.println("Membros: " + grupo.getPessoas().size() + " | Mensagens: " + grupo.getMensagens().size());

            // 1) Desfazer o vinculo N:N com cada membro
            List<Pessoa> membros = new ArrayList<>(grupo.getPessoas());
            for (Pessoa pessoa : membros) {
                grupo.removerPessoa(pessoa);
                manager.store(pessoa);
            }

            // 2) Apagar as mensagens do grupo e remove-las tambem do autor
            List<Mensagem> mensagensDoGrupo = new ArrayList<>(grupo.getMensagens());
            for (Mensagem mensagem : mensagensDoGrupo) {
                Pessoa autor = mensagem.getPessoa();
                if (autor != null) {
                    autor.removerMensagem(mensagem);
                    manager.store(autor);
                }
                manager.delete(mensagem);
            }

            // 3) Agora o grupo pode ser apagado sem deixar objetos orfaos
            manager.delete(grupo);
            manager.commit();

            System.out.println("\nGrupo, vinculos e mensagens relacionadas apagados com sucesso.");

        } catch (Exception e) {
            System.out.println("Erro ao apagar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Util.desconectar();
        }
    }
}
