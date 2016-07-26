package co.ichongwu.vidser.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import co.ichongwu.vidser.common.dao.EntityDao;
import co.ichongwu.vidser.common.dao.SqlParams;
import co.ichongwu.vidser.entity.Video;


@Repository
public class VideoDao extends EntityDao<Video, Long>{

	public Video get(String vid, Integer source) {
		   String sql = "select " + selectColumn + " from " + table + " where " + "vid = :vid and " + " source = :source ";
		   Map<String, Object> params = new HashMap<String, Object>();
		   params.put("vid", vid);
		   params.put("source", source);
	       return get(sql, params, entityClass);
	}
	
	public void addCount(Long id) {
		String sql = "update " + table + " set `count` = `count` + 1 where id = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		update(sql, params);
	}
}
