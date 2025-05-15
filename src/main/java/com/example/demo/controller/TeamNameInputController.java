package com.example.demo.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.Team;

import jakarta.servlet.http.HttpSession;

@Controller
public class TeamNameInputController {
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

		//--------------次の画面に移る前に、初期値で必要な変数をここに定義----------------

		//何回戦目かを管理するcount
		int roundCount = 1;
		session.setAttribute("roundCount", roundCount);

		//マップのバンorピックを管理するcount
		int mapPickCount = 1;
		session.setAttribute("mapPickCount", mapPickCount);

		//--------------------------------------------------------------------------------

		// 次の画面へ遷移
		return "SelectionMapPick"; // フォワードで次の画面に遷移
	}
}
