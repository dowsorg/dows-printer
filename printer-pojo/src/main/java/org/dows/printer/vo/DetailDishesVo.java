package org.dows.printer.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailDishesVo {

	private String dishesName;

	private int num;

	private double price;

	//备注
	private String comment;

	//打印机编号
	private String printerNo;

}
