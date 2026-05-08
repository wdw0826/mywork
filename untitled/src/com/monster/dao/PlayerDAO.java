package com.monster.dao;

import com.monster.model.Player;
import com.monster.model.Weapon;
import com.monster.config.DatabaseConfig;
import java.sql.*;

/**
 * PlayerDAO 負責所有與玩家資料相關的資料庫存取 (CRUD)
 */
public class PlayerDAO {

    /**
     * 初始化資料表，確保所有期末專案要求的欄位都存在
     */
    public void initTable() {
        String sql = "CREATE TABLE IF NOT EXISTS player_save (" +
                "id INTEGER PRIMARY KEY," +
                "name VARCHAR(50) NOT NULL," +
                "level INTEGER DEFAULT 1," +
                "exp INTEGER DEFAULT 0," +          // 補齊 exp 欄位
                "hp INTEGER DEFAULT 100," +         // 補齊 hp 欄位
                "max_hp INTEGER DEFAULT 100," +
                "money INTEGER DEFAULT 0," +
                "small_potions INTEGER DEFAULT 0," +
                "big_potions INTEGER DEFAULT 0," +
                "weapon_name VARCHAR(50)," +
                "weapon_atk INTEGER" +              // 統一欄位名稱
                ");";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println(">> [錯誤] 初始化資料表失敗: " + e.getMessage());
        }
    }

    /**
     * 儲存或更新玩家資料 (使用 PostgreSQL Upsert 語法)
     */
    public void saveOrUpdate(Player player) {
        String sql = "INSERT INTO player_save (id, name, level, exp, hp, max_hp, money, small_potions, big_potions, weapon_name, weapon_atk) " +
                "VALUES (1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "name=EXCLUDED.name, level=EXCLUDED.level, exp=EXCLUDED.exp, hp=EXCLUDED.hp, " +
                "max_hp=EXCLUDED.max_hp, money=EXCLUDED.money, small_potions=EXCLUDED.small_potions, " +
                "big_potions=EXCLUDED.big_potions, weapon_name=EXCLUDED.weapon_name, weapon_atk=EXCLUDED.weapon_atk;";

        try (Connection conn = DatabaseConfig.getConnection();
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
        } catch (SQLException e) {
            System.err.println(">> [錯誤] 存檔至資料庫失敗: " + e.getMessage());
        }
    }

    /**
     * 讀取玩家資料並填充至 Player 物件
     * @return boolean 是否成功讀取資料
     */
    public boolean load(Player player) {
        String sql = "SELECT * FROM player_save WHERE id = 1";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                player.setName(rs.getString("name"));
                player.setLevel(rs.getInt("level"));
                player.setExp(rs.getInt("exp"));
                player.setHp(rs.getInt("hp"));
                player.setMaxHp(rs.getInt("max_hp"));
                player.setMoney(rs.getInt("money"));
                player.setSmallPotions(rs.getInt("small_potions"));
                player.setBigPotions(rs.getInt("big_potions"));

                // 讀取武器資訊並裝備
                String wName = rs.getString("weapon_name");
                int wAtk = rs.getInt("weapon_atk");
                player.equipWeapon(new Weapon(wName, wAtk));

                return true; // 讀取成功
            }
        } catch (SQLException e) {
            System.err.println(">> [錯誤] 讀取資料失敗: " + e.getMessage());
        }
        return false; // 讀取失敗或查無資料
    }
}