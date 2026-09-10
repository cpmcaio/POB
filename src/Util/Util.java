package Util;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import modelo.Grupo;
import modelo.Mensagem;
import modelo.Pessoa;

public class Util {
	private static ObjectContainer manager;
	private static final String ARQUIVO_BANCO = "banco.db4o";

    public static void conectar() {
        EmbeddedConfiguration config =
                Db4oEmbedded.newConfiguration();

        // ----- Configuracao de cascata do CRUD -----
        // cascadeOnUpdate(true): ao salvar um objeto (ex.: Pessoa), as listas
        // de objetos relacionados (grupos, mensagens) sao atualizadas junto,
        // sem precisar salvar cada objeto relacionado manualmente.
        //
        // cascadeOnDelete(false): NAO apagamos em cascata. Pessoa <-> Grupo eh
        // um relacionamento N:N (um Grupo pode ter varias Pessoas e uma Pessoa
        // pode estar em varios Grupos), entao apagar uma Pessoa nao pode apagar
        // um Grupo (ele pode ter outros membros) e vice-versa. Mensagem tambem
        // nao deve apagar Pessoa/Grupo. Cada aplicacao de Apagar.java cuida
        // manualmente de desfazer os relacionamentos antes de remover o objeto,
        // para nao deixar objetos orfaos no banco.
        config.common().objectClass(Pessoa.class).cascadeOnUpdate(true);
        config.common().objectClass(Pessoa.class).cascadeOnDelete(false);
        config.common().objectClass(Pessoa.class).cascadeOnActivate(true);

        config.common().objectClass(Grupo.class).cascadeOnUpdate(true);
        config.common().objectClass(Grupo.class).cascadeOnDelete(false);
        config.common().objectClass(Grupo.class).cascadeOnActivate(true);

        config.common().objectClass(Mensagem.class).cascadeOnUpdate(true);
        config.common().objectClass(Mensagem.class).cascadeOnDelete(false);
        config.common().objectClass(Mensagem.class).cascadeOnActivate(true);

        manager = Db4oEmbedded.openFile(
                config,
                ARQUIVO_BANCO
        );
    }

    public static ObjectContainer getManager() {
        return manager;
    }

    public static void desconectar() {
        if (manager != null) {
            manager.close();
        }
    }
}
