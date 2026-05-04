import java.io.*;
import java.sql.*;// 確保路徑正確

public class SaveService {
    // 保留檔名，萬一資料庫連不上可以當備案
    private final String FILE_NAME = "save.txt";

    // 資料庫連線設定
    public Connection getConnection() throws SQLException {
        try {
            // 強制 JVM 載入 MySQL 驅動程式類別
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(">> [嚴重錯誤] 找不到 MySQL 驅動程式類別，請檢查 JAR 導入！");
            e.printStackTrace();
        }

        // 這裡維持原樣，但確保 URL 字串沒有打錯
        String url = "jdbc:mysql://localhost:3306/monster_hunter_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "";

        return DriverManager.getConnection(url, user, password);
    }

    // 存檔邏輯 (將玩家數據更新到資料庫 ID=1 的紀錄)
    public void save(Player player) {
        String sql = "UPDATE player_save SET level=?, exp=?, money=?, small_potions=?, big_potions=?, weapon_name=?, weapon_atk=? WHERE id=1";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, player.getLevel());
            pstmt.setInt(2, player.getExp());
            pstmt.setInt(3, player.getMoney());
            pstmt.setInt(4, player.getSmallPotions());
            pstmt.setInt(5, player.getBigPotions());
            pstmt.setString(6, player.getWeapon().getName());
            pstmt.setInt(7, player.getWeapon().getAttackBonus());

            int rowsAffected = pstmt.executeUpdate();

            // 如果更新失敗（例如 ID=1 不存在），就執行插入
            if (rowsAffected == 0) {
                insertNewPlayer(player);
            } else {
                System.out.println(">> [資料庫] 存檔更新成功！");
            }
        } catch (SQLException e) {
            System.out.println(">> [錯誤] 資料庫存檔失敗：" + e.getMessage());
        }
    }

    // 當資料庫是空的時候，插入第一筆資料
    private void insertNewPlayer(Player player) {
        String sql = "INSERT INTO player_save (id, level, exp, money, small_potions, big_potions, weapon_name, weapon_atk) VALUES (1, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, player.getLevel());
            pstmt.setInt(2, player.getExp());
            pstmt.setInt(3, player.getMoney());
            pstmt.setInt(4, player.getSmallPotions());
            pstmt.setInt(5, player.getBigPotions());
            pstmt.setString(6, player.getWeapon().getName());
            pstmt.setInt(7, player.getWeapon().getAttackBonus());
            pstmt.executeUpdate();
            System.out.println(">> [資料庫] 已建立全新存檔紀錄！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 讀檔邏輯
    public void load(Player player) {
        String sql = "SELECT * FROM player_save WHERE id=1";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                player.setLevel(rs.getInt("level"));
                player.setExp(rs.getInt("exp"));
                player.setMoney(rs.getInt("money"));
                player.setSmallPotions(rs.getInt("small_potions"));
                player.setBigPotions(rs.getInt("big_potions"));

                // 重新裝備武器
                String wName = rs.getString("weapon_name");
                int wAtk = rs.getInt("weapon_atk");
                player.equipWeapon(new Weapon(wName, wAtk));

                System.out.println(">> [資料庫] 讀檔成功！目前等級: " + player.getLevel());
            } else {
                System.out.println(">> [提醒] 資料庫查無存檔，請先進行遊戲並存檔。");
            }
        } catch (SQLException e) {
            System.out.println(">> [錯誤] 讀檔失敗：" + e.getMessage());
        }
    }
}