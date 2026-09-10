package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import Util.Util;
import modelo.Mensagem;
import modelo.Pessoa;

public class Consultar {
    // 1) Quais as mensagens contendo o texto X
    private static void consultarMensagensPorTexto(ObjectContainer manager, String texto) {
        System.out.println("\n--- Mensagens contendo o texto \"" + texto + "\" ---");

        Query consulta = manager.query();
        consulta.constrain(Mensagem.class);
        consulta.descend("texto").constrain(texto).contains();

        List<Mensagem> resultado = consulta.execute();
        for (Mensagem mensagem : resultado) {
            System.out.println(mensagem);
        }
    }

    // 2) Quais as mensagens da pessoa X no grupo X
    private static void consultarMensagensPorPessoaEGrupo(ObjectContainer manager, String nomePessoa, String nomeGrupo) {
        System.out.println("\n--- Mensagens de \"" + nomePessoa + "\" no grupo \"" + nomeGrupo + "\" ---");

        Query consulta = manager.query();
        consulta.constrain(Mensagem.class);
        consulta.descend("pessoa").descend("nome").constrain(nomePessoa);
        consulta.descend("grupo").descend("nome").constrain(nomeGrupo);

        List<Mensagem> resultado = consulta.execute();
        for (Mensagem mensagem : resultado) {
            System.out.println(mensagem + " | Autor: " + mensagem.getPessoa().getNome() + " | Grupo: " + mensagem.getGrupo().getNome());
        }
    }

    // 3) Quais as pessoas com N grupos
    private static void consultarPessoasComNGrupos(ObjectContainer manager, final int n) {
        System.out.println("\n--- Pessoas que participam de " + n + " grupo(s) ---");

        List<Pessoa> resultado = manager.query(new Predicate<Pessoa>() {
            public boolean match(Pessoa pessoa) {
                return pessoa.getGrupos().size() == n;
            }
        });
        for (Pessoa pessoa : resultado) {
            System.out.println(pessoa);
        }
    }
    public static void main(String[] args) {
        Util.conectar();
        ObjectContainer manager = Util.getManager();
        consultarMensagensPorTexto(manager, "db4o");
        consultarMensagensPorPessoaEGrupo(manager, "Ana Souza", "Turma POB");
        consultarPessoasComNGrupos(manager, 2);
        
        Util.desconectar();
    }
}
    
