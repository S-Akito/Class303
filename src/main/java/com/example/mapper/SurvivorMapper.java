package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.survivor;

@Mapper
public interface SurvivorMapper {
	//全件取得
	List<survivor> getAllSurvivors();
	
	//名前で１件取得
	survivor getSurvivorByName(int id);
	
	//削除（バンが入るとき）(名前を受け取って削除）
//	void deleteSurvivorById(int id);
	//今回はjavaでバン対象を管理してif、forで管理
}
