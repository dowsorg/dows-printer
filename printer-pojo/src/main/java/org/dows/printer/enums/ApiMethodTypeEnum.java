package org.dows.printer.enums;

public enum ApiMethodTypeEnum {
	POST(1, "POST"), GET(2, "GET"),PUT(3, "PUT"), DELETE(4, "DELETE"),
	HEAD(5, "HEAD"), PATCH(6, "PATCH"),OPTIONS(7, "OPTIONS"), TRACE(8, "TRACE"),;

	private int codeValue;

	private String codeDesc;

	private ApiMethodTypeEnum(int codeValue, String codeDesc) {
		this.codeValue = codeValue;
		this.codeDesc = codeDesc;
	}

	public int getCodeValue() {
		return this.codeValue;
	}

	public String getCodeDesc() {
		return this.codeDesc;
	}

	// 根据codeValue获取枚举
	public static ApiMethodTypeEnum parseFromCodeValue(int codeValue) {
		for (ApiMethodTypeEnum e : ApiMethodTypeEnum.values()) {
			if (e.codeValue == codeValue) {
				return e;
			}
		}
		return null;
	}

	// 根据codeValue获取描述
	public static String getCodeDescByCodeBalue(int codeValue) {
		ApiMethodTypeEnum enumItem = parseFromCodeValue(codeValue);
		return enumItem == null ? "" : enumItem.getCodeDesc();
	}

	// 验证codeValue是否有效
	public static boolean validateCodeValue(int codeValue) {
		return parseFromCodeValue(codeValue) != null;
	}

	// 列出所有值字符串
	public static String getString() {
		StringBuffer buffer = new StringBuffer();
		for (ApiMethodTypeEnum e : ApiMethodTypeEnum.values()) {
			buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
		}
		buffer.deleteCharAt(buffer.lastIndexOf(","));
		return buffer.toString().trim();
	}


}
