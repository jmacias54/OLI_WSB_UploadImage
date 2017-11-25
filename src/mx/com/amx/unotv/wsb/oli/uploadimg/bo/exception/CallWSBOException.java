package mx.com.amx.unotv.wsb.oli.uploadimg.bo.exception;

public class CallWSBOException extends Exception 
{
	private static final long serialVersionUID = 1L;
	
	public CallWSBOException(String mensaje) {
        super(mensaje);
    }

	public CallWSBOException(Throwable exception) {
        super(exception);
    }
	
    public CallWSBOException(String mensaje, Throwable exception) {
        super(mensaje, exception);
    }	
}
