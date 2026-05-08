CREATE TABLE IF NOT EXISTS player_save (
    id INTEGER PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    level INTEGER DEFAULT 1,
    exp INTEGER DEFAULT 0,
    hp INTEGER DEFAULT 100,
    max_hp INTEGER DEFAULT 100,
    money INTEGER DEFAULT 0,
    small_potions INTEGER DEFAULT 0,
    big_potions INTEGER DEFAULT 0,
    weapon_name VARCHAR(50),
    weapon_atk INTEGER
    );

-- 插入 5 筆初始測試資料 (評分規範要求)
INSERT INTO player_save (id, name, level, exp, hp, money) VALUES (1, '初始獵人', 1, 0, 100, 500) ON CONFLICT DO NOTHING;
INSERT INTO player_save (id, name, level, exp, hp, money) VALUES (2, '蒼藍星', 10, 500, 150, 2000) ON CONFLICT DO NOTHING;
INSERT INTO player_save (id, name, level, exp, hp, money) VALUES (3, '傳說騎士', 50, 9999, 300, 50000) ON CONFLICT DO NOTHING;
INSERT INTO player_save (id, name, level, exp, hp, money) VALUES (4, '新手路人', 1, 10, 80, 50) ON CONFLICT DO NOTHING;
INSERT INTO player_save (id, name, level, exp, hp, money) VALUES (5, '測試帳號', 5, 100, 120, 1000) ON CONFLICT DO NOTHING;