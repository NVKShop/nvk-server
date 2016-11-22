package hu.unideb.inf.rft.nvkshop.jobs;

public enum QuartzJobParameter {

	USER_REGISTRATION_REQUEST_ID("userRegistratinRequestId");

	private String paramName;

	public String getParamName() {
		return paramName;
	}

	private QuartzJobParameter(String paramName) {
		this.paramName = paramName;
	};

}
