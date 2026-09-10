package appconsole;

import com.db4o.ObjectContainer;

import Util.GeradorID;
import Util.Util;
import modelo.Grupo;
import modelo.Mensagem;
import modelo.Pessoa;

public class Cadastrar {

    public static void main(String[] args) {
        Util.conectar();
        ObjectContainer manager = Util.getManager();

        try {
            System.out.println("=== CADASTRANDO OBJETOS COM RELACIONAMENTOS ===\n");

            // ---------- Pessoas ----------
            Pessoa ana = new Pessoa(GeradorID.gerarId(manager, Pessoa.class), "Ana Souza");
            manager.store(ana);
            Pessoa bruno = new Pessoa(GeradorID.gerarId(manager, Pessoa.class), "Bruno Lima");
            manager.store(bruno);
            Pessoa carla = new Pessoa(GeradorID.gerarId(manager, Pessoa.class), "Carla Dias");
            manager.store(carla);

            // ---------- Grupos ----------
            Grupo turma = new Grupo(GeradorID.gerarId(manager, Grupo.class), "Turma POB");
            manager.store(turma);
            Grupo projeto = new Grupo(GeradorID.gerarId(manager, Grupo.class), "Projeto Final");
            manager.store(projeto);

            // ---------- Relacionamento N:N Pessoa <-> Grupo ----------
            ana.adicionarGrupo(turma);
            bruno.adicionarGrupo(turma);
            ana.adicionarGrupo(projeto);
            carla.adicionarGrupo(projeto);

            manager.store(ana);
            manager.store(bruno);
            manager.store(carla);
            manager.store(turma);
            manager.store(projeto);

            // ---------- Mensagens ----------
            Mensagem m1 = new Mensagem(GeradorID.gerarId(manager, Mensagem.class),
                    "09/09/2026 10:00", "Bom dia turma, aula comeca as 10h", ana, turma);
            manager.store(m1);

            Mensagem m2 = new Mensagem(GeradorID.gerarId(manager, Mensagem.class),
                    "09/09/2026 10:05", "Cheguei!", bruno, turma);
            manager.store(m2);

            Mensagem m3 = new Mensagem(GeradorID.gerarId(manager, Mensagem.class),
                    "09/09/2026 14:00", "Vamos revisar o uso do db4o no projeto final", ana, projeto);
            manager.store(m3);

            Mensagem m4 = new Mensagem(GeradorID.gerarId(manager, Mensagem.class),
                    "09/09/2026 14:10", "Combinado, foco no db4o", carla, projeto);
            manager.store(m4);

            manager.store(ana);
            manager.store(bruno);
            manager.store(carla);
            manager.store(turma);
            manager.store(projeto);

            manager.commit();

            System.out.println("Cadastro concluido: 3 pessoas, 2 grupos e 4 mensagens.");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Util.desconectar();
        }
    }
}
