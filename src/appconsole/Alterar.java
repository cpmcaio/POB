package appconsole;

import java.util.List;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import Util.*;
import modelo.*;


public class Alterar {

    public static void main(String[] args) {
        Util.conectar();
        ObjectContainer manager = Util.getManager();

        try {
            Query consultaPessoa = manager.query();
            consultaPessoa.constrain(Pessoa.class);
            consultaPessoa.descend("nome").constrain("Bruno Lima");
            List<Pessoa> resultadoPessoa = consultaPessoa.execute();

            Query consultaGrupo = manager.query();
            consultaGrupo.constrain(Grupo.class);
            consultaGrupo.descend("nome").constrain("Turma POB");
            List<Grupo> resultadoGrupo = consultaGrupo.execute();

            if (resultadoPessoa.isEmpty() || resultadoGrupo.isEmpty()) {
                System.out.println("Pessoa/Grupo nao encontrados. Execute o Cadastrar antes.");
                return;
            }

            Pessoa pessoa = resultadoPessoa.get(0);
            Grupo grupo = resultadoGrupo.get(0);

            System.out.println("=== ANTES DA ALTERACAO ===");
            System.out.println(pessoa.getNome() + " participa de " + pessoa.getGrupos().size() + " grupo(s)");
            System.out.println(grupo.getNome() + " tem " + grupo.getPessoas().size() + " membro(s)");

            grupo.removerPessoa(pessoa);

            manager.store(pessoa);
            manager.store(grupo);
            manager.commit();

            System.out.println("\n=== DEPOIS DA ALTERACAO ===");
            System.out.println(pessoa.getNome() + " participa de " + pessoa.getGrupos().size() + " grupo(s)");
            System.out.println(grupo.getNome() + " tem " + grupo.getPessoas().size() + " membro(s)");

        } catch (Exception e) {
            System.out.println("Erro ao alterar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Util.desconectar();
        }
    }
}
