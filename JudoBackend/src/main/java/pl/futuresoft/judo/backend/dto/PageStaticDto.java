package pl.futuresoft.judo.backend.dto;

import lombok.Data;

@Data
public class PageStaticDto {

	  private Integer pageStaticId;
	  private String innerName;
	  private boolean display;
	  private Integer sequence;
	  private String title;
	  private String content;
}