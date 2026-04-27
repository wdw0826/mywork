import java.io.*;

public class SaveService {
    private final String FILE_NAME = "save.txt";

    // 存檔邏輯
    public void save(Player player) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            // 格式：等級,經驗,金錢,小藥水,大藥水,當前血量,武器名,武器攻擊力
            String data = String.format("%d,%d,%d,%d,%d,%d,%s,%d",
                    player.getLevel(),
                    player.getExp(),
                    player.getMoney(),
                    player.getSmallPotions(),
                    player.getBigPotions(),
                    player.getHp(),
                    (player.getWeapon() != null ? player.getWeapon().getName() : "None"),
                    (player.getWeapon() != null ? player.getWeapon().getAttackBonus() : 0)
            );
            bw.write(data);
            System.out.println(">> [系統] 遊戲進度儲存成功！");
        } catch (IOException e) {
            System.out.println(">> [錯誤] 存檔失敗：" + e.getMessage());
        }
    }

    // 讀檔邏輯
    public void load(Player player) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println(">> [系統] 找不到存檔紀錄，請開始新遊戲。");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = br.readLine();
            if (line != null) {
                String[] d = line.split(",");

                // 還原數值 (需在 Player 類別建立對應的 setter)
                player.setLevel(Integer.parseInt(d[0]));
                player.setExp(Integer.parseInt(d[1]));
                player.setMoney(Integer.parseInt(d[2]));
                player.setSmallPotions(Integer.parseInt(d[3]));
                player.setBigPotions(Integer.parseInt(d[4]));
                player.setHp(Integer.parseInt(d[5]));

                // 還原武器
                if (!d[6].equals("None")) {
                    player.equipWeapon(new Weapon(d[6], Integer.parseInt(d[7])));
                }

                System.out.println(">> [系統] 存檔載入完畢！歡迎回來，獵人。");
            }
        } catch (Exception e) {
            System.out.println(">> [錯誤] 讀檔出錯，檔案可能損毀：" + e.getMessage());
        }
    }
}