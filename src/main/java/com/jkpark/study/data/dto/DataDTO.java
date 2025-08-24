package com.jkpark.study.data.dto;

import com.jkpark.study.data.dao.Data;
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
