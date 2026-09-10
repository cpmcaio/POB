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
