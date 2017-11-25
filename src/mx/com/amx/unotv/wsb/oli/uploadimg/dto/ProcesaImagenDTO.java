package mx.com.amx.unotv.wsb.oli.uploadimg.dto;

public class ProcesaImagenDTO {

	
	private int targetWidthm;
	private int targetHeight;
	private String directorio;
	private int secuencia;
	private String extensionFile;
	private String nombre;
	public int getTargetWidthm() {
		return targetWidthm;
	}
	public void setTargetWidthm(int targetWidthm) {
		this.targetWidthm = targetWidthm;
	}
	public int getTargetHeight() {
		return targetHeight;
	}
	public void setTargetHeight(int targetHeight) {
		this.targetHeight = targetHeight;
	}
	public String getDirectorio() {
		return directorio;
	}
	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}
	public int getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getExtensionFile() {
		return extensionFile;
	}
	public void setExtensionFile(String extensionFile) {
		this.extensionFile = extensionFile;
	}
		
	
}
