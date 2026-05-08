package com.monster.service;

import com.monster.dao.PlayerDAO;
import com.monster.model.Player;

public class SaveService {
    private PlayerDAO playerDAO = new PlayerDAO();

    // 初始化資料庫表格
    public void initDatabase() {
        playerDAO.initTable();
    }

    // 執行存檔業務
    public void save(Player player) {
        playerDAO.saveOrUpdate(player);
        System.out.println(">> [系統] 遊戲進度已安全存入 PostgreSQL 資料庫。");
    }

    // 執行讀檔業務
    public boolean load(Player player) {
        if (playerDAO.load(player)) {
            System.out.println(">> [資料庫] 讀檔成功！歡迎回來，" + player.getName());
            return true;
        }
        return false;
    }
}