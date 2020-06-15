package com.deposit.crs.eiconsumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {

	@JsonProperty("ID")
	private Integer iD;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("PageCount")
	private Integer pageCount;
	@JsonProperty("Excerpt")
	private String excerpt;
	@JsonProperty("PublishDate")
	private String publishDate;
}
