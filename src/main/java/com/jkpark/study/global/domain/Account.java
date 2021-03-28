package com.jkpark.study.global.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

// JPA 에서 사용하기 위해서 반드시 기본 생성자가 필요한데,
// 접근 권한이 최소화되도록 Protected 가 가장 적당한 듯 하다
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ACCOUNT")
@Entity
public class Account {
	// means public key
	@Id
	// public key 생성 전략 설정
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	// dbms 종류마다 달라질 수 있음으로 길이 fix
	@Column(name = "uuid", columnDefinition = "BINARY(16)")
	private UUID uuid;

	@Column(name = "ID", nullable = false, unique = true)
	private String id;

	@Column(name = "PASSWORD", nullable = false)
	private String pw;

	// enum 을 사용하는 case
	@Column(name = "ROLE", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Role role;

	// TODO : @Builder 를 사용해서 얻는 장점은?
	// 밖에서 new 로 생성하는 것과의 차이 찾아보기
	public Account(String id, String pw, Role role) {
		this.uuid = UUID.randomUUID();
		this.id = id;
		this.pw = pw;
		this.role = role;
	}
}
