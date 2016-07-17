package co.ichongwu.vidser.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import co.ichongwu.vidser.VidserApplication;
import co.ichongwu.vidser.entity.Video;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VidserApplication.class)
@WebAppConfiguration
public class VideoDaoTest {
	
	@Autowired
	VideoDao videoDao;
	
	@Test
	public void testGet() {
		Video video = videoDao.get(1L);
		System.out.println(video.getTitle());
	}

}
