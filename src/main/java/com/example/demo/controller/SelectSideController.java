package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SelectSideController {

	 // 1. 陣営選択画面の表示
    @GetMapping("/selectSide")
    public String showSideSelection(HttpSession session, Model model) {
        model.addAttribute("teamName2", session.getAttribute("teamName2"));
        model.addAttribute("selectedMap", session.getAttribute("selectedMap"));
        return "selectSide"; // selectSide.html
    }

    // 2. 陣営の選択を受け取る
    @PostMapping("/inputSelect")
    public String inputSelect(@RequestParam("side") String side, HttpSession session) {
        System.out.println("選択された陣営: " + side);

    	// 1 = サバイバー、2 = ハンター
    	String teamName1role = null;
    	String teamName2role = null;

    	//ラウンド１，３の時（teamName2が陣営選択をしたとき）
    	if((Integer) session.getAttribute("roundCount") == 1 ||(Integer) session.getAttribute("roundCount") == 3){
        if ("1".equals(side)) {
        	teamName1role = "hunter";
            teamName2role = "survivor";
        } else if ("2".equals(side)) {
        	teamName1role = "survivor";
            teamName2role = "hunter";
        }
    	}else {//ラウンド２のとき（teamName1が陣営選択をしたとき）
    		if ("1".equals(side)) {
            	teamName1role = "survivor";
                teamName2role = "hunter";
            } else if ("2".equals(side)) {
            	teamName1role = "hunter";
                teamName2role = "survivor";
            }
    	}
    	
    	System.out.println("teamName1role:" + teamName1role);
    	System.out.println("teamName2role:" + teamName2role);

        // セッションに保存
        session.setAttribute("teamName1role", teamName1role);
        session.setAttribute("teamName2role", teamName2role);

        // キャラクター選択画面へ遷移
        return "characterPhase";
    }

    
}