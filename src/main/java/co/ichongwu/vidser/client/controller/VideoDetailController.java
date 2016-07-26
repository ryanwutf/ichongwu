package co.ichongwu.vidser.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.ichongwu.vidser.entity.Video;
import co.ichongwu.vidser.service.VideoService;

@Controller
public class VideoDetailController {
	
	@Autowired
	VideoService videoService;

	@RequestMapping("/video/detail")
	public String detail(
			@RequestParam Long id,
			Model model) {
		Video video = videoService.detail(id);
		model.addAttribute("video", video);
		return "video-detail";
	}
}
