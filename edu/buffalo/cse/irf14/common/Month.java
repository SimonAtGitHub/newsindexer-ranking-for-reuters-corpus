package edu.buffalo.cse.irf14.common;

public enum Month {
	// Enum types for Month
	JAN(1), JANUARY(1), FEB(2), FEBRUARY(2), MAR(3), MARCH(3), APR(4), APRIL(4), MAY(
			5), JUN(6), JUNE(6), JUL(7), JULY(7), AUG(8), AUGUST(8), SEP(9), SEPTEMBER(
			9), OCT(10), OCTOBER(10), NOV(11), NOVEMBER(11), DEC(12), DECEMBER(
			12);

	private int value;

	private Month(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String valueOfDesc(String potentialMonth) {
		potentialMonth = potentialMonth.toUpperCase();
		String formattedMonthValue = null;
		for (Month validMonthValue : Month.values()) {
			if (validMonthValue.name().equals(potentialMonth)) {
				int intMonth = validMonthValue.getValue();
				formattedMonthValue = String.format("%02d", intMonth);
			}
		}
		return formattedMonthValue;
	}
}
