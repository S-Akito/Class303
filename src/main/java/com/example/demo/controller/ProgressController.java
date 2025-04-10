package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.GameMap;
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
		return "teamNameInput";
	}

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

	
	
	
	
	
//	// バンマップを受け取る
//	public String BanMapPick(HttpSession session, Model model, @RequestParam("mapId") int mapId) {
//	    // セッションからbanMapを取得
//	    ArrayList<GameMap> banMap = (ArrayList<GameMap>) session.getAttribute("banMap");
//
//	    // banMapがセッションにない場合は新しくリストを作成
//	    if (banMap == null) {
//	        banMap = new ArrayList<GameMap>();
//	    }
//
//	    // エンティティから選ばれたマップを受け取る
//	    GameMap selectedMap = mapsMapper.getMapByName(mapId); // mapsMapperはマップをDBから取得するマッパー
//
//	    if (selectedMap != null) {
//	        // そのマップをbanMapに追加
//	        banMap.add(selectedMap);
//	        // 更新されたbanMapをセッションに保存
//	        session.setAttribute("banMap", banMap);
//	    }
//
//	    return "mapPick";
//	}
	
	
	@Controller
	public class MapPickController {

	    // マップ情報を取得するためのマッパー（例：MyBatis等でDBと接続）
	    @Autowired
	    private MapsMapper mapsMapper;

	    // バンマップを受け取る
	    @PostMapping("/mapPick")
	    public String BanMapPick(HttpSession session, Model model, @RequestParam("mapId") int mapId) {
	        // セッションからbanMapを取得
	        ArrayList<GameMap> banMap = (ArrayList<GameMap>) session.getAttribute("banMap");

	        // banMapがセッションにない場合は新しくリストを作成
	        if (banMap == null) {
	        	banMap = new ArrayList<>();
	        }

	        // mapIdを使ってエンティティから選ばれたマップをDBから取得
	        GameMap selectedMap = mapsMapper.getMapByName(mapId);  // mapIdでマップを取得

	        if (selectedMap != null) {
	            // そのマップをbanMapに追加
	            banMap.add(selectedMap);
	            // 更新されたbanMapをセッションに保存
	            session.setAttribute("banMap", banMap);
	        }
	        System.out.println("\n選んだマップ" + selectedMap);
	        System.out.println("現在のBANマップ" + banMap);
	        
	        // 処理後、必要に応じてビューを返す
	        return "mapPick"; // 次のビューに遷移する、例えばマップピック画面など
	    }
	}
	
	
	
	
	
	


	// マップ選択画面へ
	@GetMapping("mapPick")
	public String mapPick(HttpSession session) {
		//二週目以降に作るけど、一周目からないと、banMapを探してエラーになるから、空で作っておく。
        ArrayList<GameMap> banMap = new ArrayList<GameMap>();

		
	    // セッションから 'team' を取得
	    Team team = (Team) session.getAttribute("team");

	    // もし 'team' が null ならば、リダイレクトまたはエラーメッセージ
	    if (team == null) {
	        return "redirect:/teamNameInput"; // フォームに戻る
	    }

	    // マップ情報をデータベースから取得
	    List<GameMap> maps = mapsMapper.getAllMaps();

	    // マップが取得できなかった場合の処理
	    if (maps == null || maps.isEmpty()) {
	        // セッションにエラーメッセージを保存
	        session.setAttribute("error", "マップ情報が取得できませんでした。");
	        return "errorPage"; // エラーページに遷移（例）
	    }

	    // セッションに 'team' と 'maps' を保存
	    session.setAttribute("banMap", banMap);
	    session.setAttribute("team", team); // 'team' をセッションに保存
	    session.setAttribute("maps", maps); // 'maps' をセッションに保存
	    
	    return "mapPick"; // mapPick画面を表示
	}
	
	
}
