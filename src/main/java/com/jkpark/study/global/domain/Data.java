package com.jkpark.study.global.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "DATA")
@Entity
// 여기있는 모델들은 각 서비스에서 사용할 base 모델이 되어야 할 것 같다
public class Data {
	// 데이터가 짱많은 놈은 index 를 어떻게 하지?
	@Id
	private long id;

	@Column(name = "CREATED_TIME", nullable = false)
	private Date created_time;

	@Column(name = "VAL", nullable = false)
	private int val;

	public Data(int val) {
		// 현재시간?
		this.created_time = new Date();
		this.val = val;
	}
}
