package product;

public class Product {
	private int productId;
	private String productName;
	private int productPrice;
	private String productCont;
	private String productImg;
	private int likeit;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductCont() {
		return productCont;
	}

	public void setProductCont(String productCont) {
		this.productCont = productCont;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public int getLikeit() {
		return likeit;
	}

	public void setLikeit(int likeit) {
		this.likeit = likeit;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productPrice=" + productPrice
				+ ", productCont=" + productCont + ", productImg=" + productImg + ", likeit=" + likeit + "]";
	}

}