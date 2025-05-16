package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

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



	//★マップピックに移る画面（二周目以降）★
	@Controller
	public class MapPickController {
		

		// マップ情報を取得するためのマッパー（例：MyBatis等でDBと接続）
		@Autowired
		private MapsMapper mapsMapper;

		// バンマップを受け取る
		@PostMapping("/mapPick")
		public String BanMapPick(HttpSession session, Model model, @RequestParam("mapId") int mapId) {
			
			
			//HTMLファイルのラウンド表示
			String roundText = "GAME " + session.getAttribute("roundCount");
			model.addAttribute("roundText",roundText);
			
			
			// セッションからbanMapを取得
			ArrayList<GameMap> banMap = (ArrayList<GameMap>) session.getAttribute("banMap");

			// banMapがセッションにない場合は新しくリストを作成
			if (banMap == null) {
				banMap = new ArrayList<>();
			}

			// mapIdを使ってエンティティから選ばれたマップをDBから取得
			GameMap selectedMap = mapsMapper.getMapByName(mapId); // mapIdでマップを取得
			
			
			if (selectedMap != null) {
				// 選択マップをbanMapに追加
				banMap.add(selectedMap);
				// 更新されたbanMapをセッションに保存
				session.setAttribute("banMap", banMap);
			}
			
			session.setAttribute("selectedMap", selectedMap);

			System.out.println("\n選んだマップ" + selectedMap);
			System.out.println("現在のBANマップ" + banMap);

			if ((Integer) session.getAttribute("mapPickCount") == 1) {
				//mappickCountをインクリメントして再度セッションに保存
				Integer mapPickCount = (Integer) session.getAttribute("mapPickCount");
				mapPickCount++;
				session.setAttribute("mapPickCount", mapPickCount);
				return "mapPick"; // 次のビューに遷移する、例えばマップピック画面など
			} else {
				return "mapResult";
			}
		}
	}

	//★マップピックに移る画面（一周目）★
	// マップ選択画面へ
	@GetMapping("mapPick")
	public String mapPick(HttpSession session, Model model) {
		//二週目以降に作るけど、一周目からないと、banMapを探してエラーになるから、空で作っておく。
		ArrayList<GameMap> banMap = new ArrayList<GameMap>();

		//二回戦のbanMap初期化処理
		if ((Integer) session.getAttribute("roundCount") == 2) {
			//round、mapPick変数をインクリメント/初期化
			Integer roundCount = (Integer) session.getAttribute("roundCount");
			roundCount++;
			session.setAttribute("roundCount", roundCount);

			Integer mapPickCount = (Integer) session.getAttribute("mapPickCount");
			mapPickCount = 1;
			session.setAttribute("mapPickCount", mapPickCount);
			
			//HTMLファイルのラウンド表示
			String roundText = "GAME 2";
			model.addAttribute("roundText",roundText);

			//banMapの最後の要素（ピックされたマップ）を取りだす
			GameMap pickMap = banMap.get(banMap.size());
			//banMapをクリア
			banMap.clear();
			//banMap格納
			banMap.add(pickMap);
		}

		//三回戦のbanMap初期化処理
		if ((Integer) session.getAttribute("roundCount") == 3) {
			//round、mapPick変数をインクリメント/初期化
			Integer roundCount = (Integer) session.getAttribute("roundCount");
			roundCount++;
			session.setAttribute("roundCount", roundCount);

			Integer mapPickCount = (Integer) session.getAttribute("mapPickCount");
			mapPickCount = 1;
			session.setAttribute("mapPickCount", mapPickCount);

			//HTMLファイルのラウンド表示
			String roundText = "GAME 3";
			model.addAttribute("roundText",roundText);
			
			//banMapの最初（一回戦のピック）と最後（二回戦のピック）を取り出す
			GameMap pickMap1 = banMap.get(0);
			GameMap pickMap2 = banMap.get(banMap.size());
			//banMapをクリア
			banMap.clear();
			//banMap格納
			banMap.add(pickMap1);
			banMap.add(pickMap2);

		}

		//★一回戦目の処理★

		if ((Integer) session.getAttribute("roundCount") == 1) {
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
			
			//HTMLファイルのラウンド表示
			String roundText = "GAME 1";
			model.addAttribute("roundText",roundText);

			// セッションに 'team' と 'maps' を保存
			session.setAttribute("banMap", banMap);
			session.setAttribute("team", team); // 'team' をセッションに保存
			session.setAttribute("maps", maps); // 'maps' をセッションに保存
		}

		//第二回戦以降の場合のmapPickCountの初期化の役割も含む
		Integer mapPickCount = (Integer) session.getAttribute("mapPickCount");
		if (mapPickCount != 1) {
			mapPickCount = 1;
			session.setAttribute("mapPickCount", mapPickCount);
		}
		return "mapPick"; // mapPick画面を表示
	}

}
