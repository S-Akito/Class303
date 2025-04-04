package com.example.demo.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.Team;
import com.example.mapper.HunterMapper;
import com.example.mapper.MapsMapper;
import com.example.mapper.SurvivorMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProgressController {
	
	
	// DI (他のサービス層を経由せずに済んで楽だからここで定義してる)
	private final SurvivorMapper survivorsMapper;
	private final HunterMapper huntersMapper;
	private final MapsMapper mapsMapper;
	
	//ホーム画面を表示する
	@GetMapping("/")
	public String home() {
	return "home";
	}
	
	//チーム名入力
	@GetMapping("teamNameInput")
	public String teamNameInput() {
		System.out.println("aaa");
		return "teamNameInput";
	}
	
	//チーム名をFormに格納
	@PostMapping("submitTeams")
	public String submitTeams(@RequestParam String teamName1, @RequestParam String teamName2, Model model) {
		// フォームから受け取ったチーム名をFormで格納
		Team team = new Team(); //インスタンスをクラス内で保持
		
		//受け取ったチームをランダムにteamName1,teamName2に格納
		Random rand = new Random();
		int a = rand.nextInt(2);
		if(a == 0) {
			team.setTeamName1(teamName1);
			team.setTeamName2(teamName2);

		}else {
			team.setTeamName1(teamName2);
			team.setTeamName2(teamName1);
		}
		
		//Modelに格納する
		model.addAttribute("team", team);
		
		return  "SelectionMapPick";
	}
	
	//マップバン画面へ
	@GetMapping("mapPick")
	public String mapPick(@RequestParam String teamName1, @RequestParam String teamName2, Model model) {
	    // リクエストパラメータから受け取った teamName1 と teamName2 を使って Team オブジェクトを作成
		System.out.println(teamName1);
		System.out.println(teamName2);
	    Team team = new Team();
	    
	    // Team オブジェクトを model に追加
	    model.addAttribute("team", team);

	    // mapPick.html に遷移
	    return "mapPick";
	}

	
	
}
