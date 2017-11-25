package mx.com.amx.unotv.wsb.oli.uploadimg.bo.exception;

public class UploadImageBOException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UploadImageBOException(String mensaje) {
        super(mensaje);
    }

	public UploadImageBOException(Throwable exception) {
        super(exception);
    }
	
    public UploadImageBOException(String mensaje, Throwable exception) {
        super(mensaje, exception);
    }	
	
	
}
