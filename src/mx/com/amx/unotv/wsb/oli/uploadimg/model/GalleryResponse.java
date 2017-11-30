


/**
 * 
 */
package mx.com.amx.unotv.wsb.oli.uploadimg.model;

/**
 * @author Jesus A. Macias Benitez
 *
 */
public class GalleryResponse {

	private String status;
	private String message;
	private String pathGallery;
	private String nameGalleryImage;
	/**
	 * 
	 */
	public GalleryResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPathGallery() {
		return pathGallery;
	}
	public void setPathGallery(String pathGallery) {
		this.pathGallery = pathGallery;
	}
	public String getNameGalleryImage() {
		return nameGalleryImage;
	}
	public void setNameGalleryImage(String nameGalleryImage) {
		this.nameGalleryImage = nameGalleryImage;
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
	
	
	
	
	
	
}
