package software.magnes.solutions;

import java.util.List;

public interface LeitorDados {
    List<String[]> ler(String caminho) throws Exception;
}