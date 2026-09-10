package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;

import Util.Util;
import modelo.Grupo;
import modelo.Mensagem;
import modelo.Pessoa;

public class Listar {
	public static void main(String[] args) {
		Util.conectar();
		ObjectContainer manager = Util.getManager();

		System.out.println("--- PESSOAS ---");
		List<Pessoa> pessoas = manager.query(Pessoa.class);
		for (Pessoa pessoa : pessoas) {
			System.out.println(pessoa);
			System.out.println("  grupos: " + pessoa.getGrupos());
			System.out.println("  mensagens: " + pessoa.getMensagens());
		}

		System.out.println("\n--- GRUPOS ---");
		List<Grupo> grupos = manager.query(Grupo.class);
		for (Grupo grupo : grupos) {
			System.out.println(grupo);
			System.out.println("  membros: " + grupo.getPessoas());
		}

		System.out.println("\n--- MENSAGENS ---");
		List<Mensagem> mensagens = manager.query(Mensagem.class);
		for (Mensagem mensagem : mensagens) {
			System.out.println(mensagem);
		}
		Util.desconectar();
	}
}
