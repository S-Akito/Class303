package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.GameMap;

@Mapper
public interface MapsMapper {
	//全件取得
	List<GameMap> getAllMaps();
	//名前をidに変換して１件取得
	GameMap getMapByName(int id);
	//削除（バンが入るとき）(名前をidに変換して受け取って削除）
//	void deleteMapById(int id);
}
