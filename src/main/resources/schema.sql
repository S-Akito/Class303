-- テーブルが存在したら削除する
DROP TABLE IF EXISTS match_results CASCADE; --CASCADEで依存先のテーブルも一緒に削除する
DROP TABLE IF EXISTS maps;
DROP TABLE IF EXISTS survivors;
DROP TABLE IF EXISTS hunters;

-- マップを登録するテーブルの作成
CREATE TABLE maps(
    -- MAPid(主キー)
    id serial PRIMARY KEY,
    -- MAP登録
    maps_name varchar(255) NOT NULL
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

-- ハンター陣営とサバイバー陣営の勝ち負けの結果を登録するテーブル
CREATE TABLE match_results(
    match_id serial PRIMARY KEY,
    map_id INT REFERENCES maps(id),  -- マップを参照
    hunter_id INT REFERENCES hunters(hunter_id),  -- ハンターを参照
    winner VARCHAR(50) CHECK (winner IN ('survivor', 'hunter')),  -- 勝者がサバイバーかハンターか
    match_date TIMESTAMP
);