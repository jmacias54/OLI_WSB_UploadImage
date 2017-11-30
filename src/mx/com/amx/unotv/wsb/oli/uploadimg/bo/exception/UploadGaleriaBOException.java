/**
 * 
 */
package mx.com.amx.unotv.wsb.oli.uploadimg.bo.exception;

/**
 * @author Jesus A. Macias Benitez
 *
 */
public class UploadGaleriaBOException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UploadGaleriaBOException(String mensaje) {
        super(mensaje);
    }

	public UploadGaleriaBOException(Throwable exception) {
        super(exception);
    }
	
    public UploadGaleriaBOException(String mensaje, Throwable exception) {
        super(mensaje, exception);
    }	
	

}
