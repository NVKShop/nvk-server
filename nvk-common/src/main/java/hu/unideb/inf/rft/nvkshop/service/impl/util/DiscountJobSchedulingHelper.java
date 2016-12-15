package hu.unideb.inf.rft.nvkshop.service.impl.util;

import hu.unideb.inf.rft.nvkshop.entities.product.ScheduledDiscount;

public class DiscountJobSchedulingHelper {

	public static final String SCHEDULED_DISCOUNT_ID_PARAM_NAME = "scheduledDiscountId";

	public static String buildDiscountJobName(ScheduledDiscount scheduledDiscount, DiscountJobType type) {
		if (scheduledDiscount.getId() == null) {
			throw new NullPointerException("ScheduledDiscount id must not be null");
		}
		return "ScheduledDiscountJob-" + type.getKey() + "-" + scheduledDiscount.getId();
	}

	public static String buildDiscountGroupName(ScheduledDiscount scheduledDiscount, DiscountJobType type) {
		if (scheduledDiscount.getId() == null) {
			throw new NullPointerException("ScheduledDiscount id must not be null");
		}
		return "ScheduledDiscountGroup-" + type.getKey() + "-" + scheduledDiscount.getId();
	}

	public static String buildDiscountTriggerName(ScheduledDiscount scheduledDiscount, DiscountJobType type) {
		if (scheduledDiscount.getId() == null) {
			throw new NullPointerException("ScheduledDiscount id must not be null");
		}
		return "ScheduledDiscountTrigger-" + type.getKey() + "-" + scheduledDiscount.getId();
	}

}
