import java.io.*;
import java.sql.*;

public class SaveService {

    // 取得資料庫連線
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(">> [錯誤] 找不到 SQLite 驅動！");
        }
        // 連線到 hunter.db 檔案
        String url = "jdbc:sqlite:hunter.db";
        return DriverManager.getConnection(url);
    }

    // 初始化資料庫：建立包含 HP 與 Max HP 的表格
    public void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS player_save (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "level INTEGER DEFAULT 1," +
                "exp INTEGER DEFAULT 0," +
                "hp INTEGER DEFAULT 100," +      // 新增目前血量
                "max_hp INTEGER DEFAULT 100," +  // 新增最大血量
                "money INTEGER DEFAULT 0," +
                "small_potions INTEGER DEFAULT 0," +
                "big_potions INTEGER DEFAULT 0," +
                "weapon_name TEXT," +
                "weapon_atk INTEGER" +           // 統一欄位名稱為 weapon_atk
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 存檔邏輯：將所有狀態存入 ID=1 的紀錄
    public void save(Player player) {
        // 包含 id, name, level, exp, hp, max_hp, money, small_potions, big_potions, weapon_name, weapon_atk
        // 扣除自動生成的 id，後面需要 10 個問號
        String sql = "REPLACE INTO player_save (id, name, level, exp, hp, max_hp, money, small_potions, big_potions, weapon_name, weapon_atk) " +
                "VALUES (1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, player.getName());
            pstmt.setInt(2, player.getLevel());
            pstmt.setInt(3, player.getExp());
            pstmt.setInt(4, player.getHp());
            pstmt.setInt(5, player.getMaxHp());
            pstmt.setInt(6, player.getMoney());
            pstmt.setInt(7, player.getSmallPotions());
            pstmt.setInt(8, player.getBigPotions());
            pstmt.setString(9, player.getWeapon().getName());
            pstmt.setInt(10, player.getWeapon().getAttackBonus());

            pstmt.executeUpdate();
            System.out.println(">> [系統] 遊戲進度已安全存入資料庫。");
        } catch (SQLException e) {
            System.err.println(">> [錯誤] 存檔失敗：" + e.getMessage());
        }
    }

    // 讀檔邏輯：從資料庫抓回所有數值
    public boolean load(Player player) {
        String sql = "SELECT * FROM player_save WHERE id=1";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                player.setName(rs.getString("name"));
                player.setLevel(rs.getInt("level"));
                player.setExp(rs.getInt("exp"));
                player.setHp(rs.getInt("hp"));           // 讀取血量
                player.setMaxHp(rs.getInt("max_hp"));     // 讀取最大血量
                player.setMoney(rs.getInt("money"));
                player.setSmallPotions(rs.getInt("small_potions"));
                player.setBigPotions(rs.getInt("big_potions"));

                String wName = rs.getString("weapon_name");
                int wAtk = rs.getInt("weapon_atk");
                player.equipWeapon(new Weapon(wName, wAtk));

                System.out.println(">> [資料庫] 讀檔成功！目前等級: " + player.getLevel() + "，HP: " + player.getHp());
                return true;
            }
        } catch (SQLException e) {
            System.out.println(">> [錯誤] 讀檔失敗：" + e.getMessage());
        }
        return false;
    }
}