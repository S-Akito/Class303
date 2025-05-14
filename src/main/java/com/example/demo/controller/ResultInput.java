package com.example.demo.controller;

import java.util.ArrayList;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultInput {
	
	@GetMapping("result")
	public String resultInput(HttpSession session) {
		//チーム１のポイントをセッションに保存
		ArrayList<Integer> team1Point = new ArrayList<Integer>();
		session.setAttribute("team1point", team1Point);
		
		//チーム２のポイントをセッションに保存
		ArrayList<Integer> team2Point = new ArrayList<Integer>();
		session.setAttribute("team2point", team2Point);
		
		return "home";
	}
}
