package Group;

import java.io.IOException;
/**
 * @autors Tatyana Anisova
 * @year 2020
 */
public class Main {
    public static void main(String[] args) throws IOException{
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("\nРабота программы завершена!")));
        String s = System.getenv("FILE");
        Commander commander = new Commander(new CollectionManager(s));
        commander.interactiveMod();
    }
}
