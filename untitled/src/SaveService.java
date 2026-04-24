import java.io.*;

public class SaveService {

    public void save(Player player) {
        try (BufferedWriter bw =
                     new BufferedWriter(new FileWriter("save.txt"))) {

            bw.write(player.getHp() + "");
            bw.newLine();

            System.out.println("Saved!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}