package com.jkpark.study.global.util.enums;

public class EnumUtil {
	public static<E extends Enum<E>> E getEnumValue(Class<E> type, String name) {
		return Enum.valueOf(type, name);
	}

	public static<E extends Enum<E>> E getEnumName(Class<E> type, Object value) {
		for (var e : type.getEnumConstants()) {
			if(e.equals(value)) {
				return e;
			}
		}

		return null;
	}
}
