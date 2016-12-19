package hu.unideb.inf.nvkshop.rest.vo;

public class PublicCategory {

	private Long categoryId;

	private String name;

	private Long parentId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public PublicCategory(Long categoryId, String name, Long parentId) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.parentId = parentId;
	}

}
