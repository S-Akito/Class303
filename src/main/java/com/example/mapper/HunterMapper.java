package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.hunter;



@Mapper
public interface HunterMapper {
	//全件取得
	List<hunter> getAllhunters();
	//名前で１件取得
	hunter getSurvivorByName(int id);
	//削除（バンが入るとき）(名前を受け取って削除）
//	void deleteSurvivorById(int id);
}
