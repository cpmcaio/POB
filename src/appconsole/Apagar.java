package appconsole;

import java.util.ArrayList;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import Util.Util;
import modelo.Grupo;
import modelo.Mensagem;
import modelo.Pessoa;

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


            List<Pessoa> membros = new ArrayList<>(grupo.getPessoas());
            for (Pessoa pessoa : membros) {
                grupo.removerPessoa(pessoa);
                manager.store(pessoa);
            }


            List<Mensagem> mensagensDoGrupo = new ArrayList<>(grupo.getMensagens());
            for (Mensagem mensagem : mensagensDoGrupo) {
                Pessoa autor = mensagem.getPessoa();
                if (autor != null) {
                    autor.removerMensagem(mensagem);
                    manager.store(autor);
                }
                manager.delete(mensagem);
            }


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
