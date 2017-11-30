package mx.com.amx.unotv.wsb.oli.uploadimg.model;

public class ImageResponse {

	private String status;
	private String message;
	private String pathImages;
	private String nameImagePrincipal;
	private String nameImageSquare;

	

	public ImageResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
	}




	public String getPathImages() {
		return pathImages;
	}

	public void setPathImages(String pathImages) {
		this.pathImages = pathImages;
	}

	public String getNameImagePrincipal() {
		return nameImagePrincipal;
	}

	public void setNameImagePrincipal(String nameImagePrincipal) {
		this.nameImagePrincipal = nameImagePrincipal;
	}

	public String getNameImageSquare() {
		return nameImageSquare;
	}

	public void setNameImageSquare(String nameImageSquare) {
		this.nameImageSquare = nameImageSquare;
	}
	
	

}
