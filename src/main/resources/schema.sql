-- テーブルが存在したら削除する
DROP TABLE IF EXISTS maps;
DROP TABLE IF EXISTS survivors;
DROP TABLE IF EXISTS hunters;

-- マップを登録するテーブルの作成
CREATE TABLE maps(
    -- MAPid(主キー)
    id serial PRIMARY KEY,
    -- MAP登録
    maps_name VARCHAR(255) NOT NULL
);

-- サバイバー（キャラクター）を登録するテーブル
CREATE TABLE survivors(
    survivor_id serial PRIMARY KEY,
    survivor_name VARCHAR(100) NOT NULL
);

-- ハンター（キャラクター）を登録するテーブル
CREATE TABLE hunters(
    hunter_id serial PRIMARY KEY,
    hunter_name VARCHAR(100) NOT NULL
);

