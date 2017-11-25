package mx.com.amx.unotv.wsb.oli.uploadimg.model;

public class ImageRequest {


	private String nameImage;

	private int xPosition;
	private int yPosition;

	

	public ImageRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getNameImage() {
		return nameImage;
	}

	public void setNameImage(String nameImage) {
		this.nameImage = nameImage;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

}
