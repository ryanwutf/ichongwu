package co.ichongwu.vidser.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import co.ichongwu.vidser.VidserApplication;
import co.ichongwu.vidser.common.vo.Page;
import co.ichongwu.vidser.entity.Video;
import co.ichongwu.vidser.service.VideoService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VidserApplication.class)
@WebAppConfiguration
public class VideoServiceTest {
	
	@Autowired
	VideoService videoService;
	
	@Test
	public void testList() {
		Page<Video> page= new Page<>();
		videoService.list(page);
		System.out.println(page.getResult().get(0).getDescription());
	}
	

}
