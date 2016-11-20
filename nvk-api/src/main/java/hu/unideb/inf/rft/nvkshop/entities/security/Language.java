package hu.unideb.inf.rft.nvkshop.entities.security;

public enum Language {
	EN("en"), HU("hu");

	private String lang;

	private Language(String s) {
		lang = s;
	}

	@Override
	public String toString() {
		return this.lang;
	}
}