package com.jkpark.study.global.dto;

import com.jkpark.study.global.domain.Data;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataDTO {
	private Date created_time;

	private int val;

	public Data toEntity() {
		return new Data(val);
	}
}
