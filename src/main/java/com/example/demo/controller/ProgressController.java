package com.example.demo.controller;

import java.util.Random;

import jakarta.servlet.http.HttpSession;

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
	
//	//チーム名をFormに格納
//	@PostMapping("submitTeams")
//	public String submitTeams(@RequestParam("teamName1") String teamName1, @RequestParam("teamName2") String teamName2, Model model) {
//		// フォームから受け取ったチーム名をFormで格納
//		Team team = new Team(); //インスタンスをクラス内で保持
//		Team[] teamList = new Team[1];
//		//受け取ったチームをランダムにteamName1,teamName2に格納
//		Random rand = new Random();
//		int a = rand.nextInt(2);
//		if(a == 0) {
//			team.setTeamName1(teamName1);
//			team.setTeamName2(teamName2);
//
//		}else {
//			team.setTeamName1(teamName2);
//			team.setTeamName2(teamName1);
//		}
//		teamList[0] = team;
//		
//		
//		//teamオブジェクトをModelに格納
//		model.addAttribute("team",team);
//		
//		//次の画面へ。
//		return  "SelectionMapPick";
//	}
//	
//	// マップバン画面へ
//	@GetMapping("mapPick")
//	public String mapPick(Model model) {
//		Team teams = submitTeams.teamList[0].getTeamName1();
//	    // 'team' をモデルから取得
//	    Team team = (Team) model.getAttribute("team");
//	    
//	    // もし 'team' が null ならば、リダイレクトまたはエラーメッセージ
//	    if (team == null) {
//	        return "redirect:/teamNameInput";  // 例えば、フォームに戻る
//	    }
//
//	    // mapPick.html に遷移
//	    return "mapPick";
//	}
	// チーム名をFormに格納
	@PostMapping("submitTeams")
	public String submitTeams(@RequestParam("teamName1") String teamName1, 
	                          @RequestParam("teamName2") String teamName2, 
	                          HttpSession session) {
	    Team team = new Team(); // インスタンスをクラス内で保持

	    // 受け取ったチームをランダムにteamName1, teamName2に格納
	    Random rand = new Random();
	    int a = rand.nextInt(2);
	    if (a == 0) {
	        team.setTeamName1(teamName1);
	        team.setTeamName2(teamName2);
	    } else {
	        team.setTeamName1(teamName2);
	        team.setTeamName2(teamName1);
	    }
	    
	    // セッションに team を保存
	    session.setAttribute("team", team);
	    
	    // 次の画面へ遷移
	    return "SelectionMapPick"; // フォワードで次の画面に遷移
	}

	// マップバン画面へ
	@GetMapping("mapPick")
	public String mapPick(HttpSession session, Model model) {
	    // セッションから 'team' を取得
	    Team team = (Team) session.getAttribute("team");

	    // もし 'team' が null ならば、リダイレクトまたはエラーメッセージ
	    if (team == null) {
	        return "redirect:/teamNameInput";  // 例えば、フォームに戻る
	    }

	    // mapPick.html に遷移
	    model.addAttribute("team", team);  // model に 'team' を再度追加
	    return "mapPick";  // mapPick画面を表示
	}

	
	
}
