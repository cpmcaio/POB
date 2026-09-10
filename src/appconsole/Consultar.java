package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import Util.Util;
import modelo.Mensagem;
import modelo.Pessoa;

public class Consultar {

    public static void main(String[] args) {
        Util.conectar();
        ObjectContainer manager = Util.getManager();

        try {
            consultarMensagensPorTexto(manager, "db4o");
            consultarMensagensPorPessoaEGrupo(manager, "Ana Souza", "Turma POB");
            consultarPessoasComNGrupos(manager, 2);
        } catch (Exception e) {
            System.out.println("Erro ao consultar: " + e.getMessage());
        } finally {
            Util.desconectar();
        }
    }

    // 1) Quais as mensagens contendo o texto X
    private static void consultarMensagensPorTexto(ObjectContainer manager, String texto) {
        System.out.println("\n--- Mensagens contendo o texto \"" + texto + "\" ---");

        Query consulta = manager.query();
        consulta.constrain(Mensagem.class);
        consulta.descend("texto").constrain(texto).contains();

        imprimir(consulta.execute());
    }

    // 2) Quais as mensagens da pessoa X no grupo X
    private static void consultarMensagensPorPessoaEGrupo(ObjectContainer manager,
            String nomePessoa, String nomeGrupo) {
        System.out.println("\n--- Mensagens de \"" + nomePessoa + "\" no grupo \"" + nomeGrupo + "\" ---");

        Query consulta = manager.query();
        consulta.constrain(Mensagem.class);
        consulta.descend("pessoa").descend("nome").constrain(nomePessoa);
        consulta.descend("grupo").descend("nome").constrain(nomeGrupo);

        imprimir(consulta.execute());
    }

    // 3) Quais as pessoas com N grupos
    // Predicate eh usado aqui porque o SODA (Query/descend/constrain) nao
    // consegue comparar o TAMANHO de uma lista, apenas o valor de um atributo.
    private static void consultarPessoasComNGrupos(ObjectContainer manager, final int n) {
        System.out.println("\n--- Pessoas que participam de " + n + " grupo(s) ---");

        List<Pessoa> resultado = manager.query(new Predicate<Pessoa>() {
            public boolean match(Pessoa pessoa) {
                return pessoa.getGrupos().size() == n;
            }
        });

        imprimir(resultado);
    }

    private static void imprimir(List<?> resultado) {
        if (resultado.isEmpty()) {
            System.out.println("Nenhum resultado encontrado.");
            return;
        }
        for (Object item : resultado) {
            System.out.println(item);
        }
    }
}
