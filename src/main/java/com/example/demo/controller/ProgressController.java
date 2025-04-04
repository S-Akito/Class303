package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.mapper.HunterMapper;
import com.example.mapper.MapsMapper;
import com.example.mapper.SurvivorMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProgressController {
	// DI (多サービス層を経由せずに済んで楽だからここで定義してる)
	private final SurvivorMapper survivorsMapper;
	private final HunterMapper huntersMapper;
	private final MapsMapper mapsMapper;
	
	//ホーム画面を表示する
	@GetMapping("/")
	public String home() {
	return "home";
	}
	
	//マップバン画面へ（全マップを表示し、選択）
	@GetMapping("mapPick")
	public String mapPick() {
		return "mapPick";
	}
	
}
