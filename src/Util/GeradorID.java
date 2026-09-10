package Util;
import java.lang.reflect.Method;
import java.util.List;
import com.db4o.ObjectContainer;

public class GeradorID {

    public static int gerarId(ObjectContainer manager, Class<?> classe) {
        int maiorId = 0;

        List<?> objetos = manager.query(classe);

        try {
            Method getId = classe.getMethod("getId");
            for (Object objeto : objetos) {
                int id = (Integer) getId.invoke(objeto);
                if (id > maiorId) {
                    maiorId = id;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar id para " + classe.getSimpleName(), e);
        }

        return maiorId + 1;
    }
}
