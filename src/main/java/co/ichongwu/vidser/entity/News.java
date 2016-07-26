package co.ichongwu.vidser.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import co.ichongwu.vidser.common.entity.AbstractEntity;

@Entity
public class News extends AbstractEntity{

	@Id
	private Long id;
	
	
}
