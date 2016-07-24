package co.ichongwu.vidser.imports;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ichongwu.vidser.config.Constants;
import co.ichongwu.vidser.crawler.letv.LetvPageProcessor;
import co.ichongwu.vidser.crawler.letv.LetvPageProcessor.LetvVideoCell;
import co.ichongwu.vidser.dao.VideoDao;
import co.ichongwu.vidser.entity.Video;
import co.ichongwu.vidser.utils.ConversionUtil;

@Service
public class LetvImporter {
	
	private static Logger log  = LoggerFactory.getLogger(LetvImporter.class);
	
	@Autowired
	VideoDao videoDao;
	
	
	public void importVideo() {
		try {
			LetvPageProcessor processor = new LetvPageProcessor();
			List<LetvVideoCell> videoCells = processor.getVideoCells();
			
			log.info("VideoCell count:" + videoCells.size());
			
			for(LetvVideoCell cell : videoCells) {
				Video video = new Video();
				video.setTitle(cell.getName());
				video.setDescription(cell.getDescription());
				video.setThumb(cell.getImages().get("300*400"));
				video.setDuration(ConversionUtil.convert(cell.getDuration(), Long.class));
				video.setVid(cell.getVid());
				video.setCreateTime(new Date());
				video.setCreateUser(1L);
				video.setType(Constants.TYPE.VIDEO);
				video.setSource(Constants.VIDEO_SOURCE.LETV);
				videoDao.insert(video);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
