package mx.com.amx.unotv.wsb.oli.uploadimg.bo;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import mx.com.amx.unotv.wsb.oli.uploadimg.bo.exception.UploadGaleriaBOException;
import mx.com.amx.unotv.wsb.oli.uploadimg.dto.ParametrosDTO;
import mx.com.amx.unotv.wsb.oli.uploadimg.model.GalleryResponse;
import mx.com.amx.unotv.wsb.oli.uploadimg.util.CargaProperties;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class UploadGaleriaBO {

	// LOG
	private static Logger LOG = Logger.getLogger(UploadGaleriaBO.class);
	@Autowired
	private CallWSBO callWSBO;

	@Value("${${ambiente}.domain.galeria}")
	private String domain;

	public GalleryResponse procesaFicheros(HttpServletRequest request) throws UploadGaleriaBOException {

		GalleryResponse response = new GalleryResponse();
		LOG.debug("Inicia procesaFicheros en UploadGaleriaBO");
		try {

			// Cargamos archivos de propiedades
			CargaProperties cargaProperties = new CargaProperties();
			ParametrosDTO parametrosDTO = cargaProperties.obtenerPropiedades("ambiente.resources.properties");

			int maxBites = 1024 * parametrosDTO.getMaxKBImagenesGaleria();
			LOG.debug("maxBites: " + maxBites);
			DiskFileUpload fu = new DiskFileUpload();
			fu.setSizeMax(maxBites); // 150KB
			fu.setSizeThreshold(maxBites);
			fu.setRepositoryPath("/tmp");
			List<FileItem> fileItems = fu.parseRequest(request);
			if (fileItems == null) {
				return null;
			}
			Iterator<FileItem> i = fileItems.iterator();
			FileItem actual = null;
			String fileName = "";
			while (i.hasNext()) {
				actual = (FileItem) i.next();
				fileName = actual.getName();
				int uu = 0;
				if (fileName != null && !fileName.equals("")) {
					File fichero = new File(fileName);
					LOG.info("El nombre del fichero es :" + fichero.getName());
					long sizeFile = actual.getSize();
					if (parametrosDTO.getExtFiles() != null && parametrosDTO.getExtFiles().length > 1) {
						String[] ext = fichero.getName().split("\\.");
						for (int hh = 0; hh < parametrosDTO.getExtFiles().length; hh++) {
							if (parametrosDTO.getExtFiles()[hh].trim().equals(ext[ext.length - 1]))
								uu++;
						}
					} else {
						uu = 1;
					}

					if (uu > 0) {
						if (sizeFile <= maxBites) {
							String directorio = parametrosDTO.getPathLocalImagenesGaleria();
							boolean continuar = false;
							Integer secuencia = 0;
							try {

								secuencia = callWSBO.getSecuencia(parametrosDTO);
								// directorio += secuencia + "/";
								continuar = createFolders(directorio);
							} catch (Exception e) {
								continuar = false;
								secuencia = 0;
								LOG.error("Error al crear las carpetas: " + e.getMessage());
								throw new UploadGaleriaBOException("Error al crear las carpetas: " + e.getMessage());
							}
							if (continuar) {
								String extFile = "";
								try {
									String nam[] = actual.getName().split("\\.");
									extFile = nam[nam.length - 1];
								} catch (Exception e) {
									extFile = "jpg";
								}
								fichero = new File(directorio + secuencia + "-GaleriaUno." + extFile);
								actual.write(fichero);
								fileName = fichero.getName();
							} else {
								fileName = "ERROR";
							}

						} else {
							LOG.info("Se intento subir un archivo de tamano mayor al permitido "
									+ parametrosDTO.getMaxMegas() + " MB");
							fileName = "MAXTAM";
							throw new UploadGaleriaBOException(
									"Se intento subir un archivo de tamano mayor al permitido "
											+ parametrosDTO.getMaxMegas() + " MB");
						}
					} else {
						LOG.info("Se intento subir otro tipo de archivo");
						fileName = "TIPOAR";

						throw new UploadGaleriaBOException("Se intento subir otro tipo de archivo");
					}
				}
			}
			LOG.info("Nombre imagen generada: " + fileName);

			response.setNameGalleryImage(fileName);
			response.setPathGallery(domain);
			return response;
		} catch (SizeLimitExceededException le) {
			LOG.error("Se intento subir un archivo de tamano mayor al permitido MB");
			throw new UploadGaleriaBOException(
					"Se intento subir un archivo de tamano mayor al permitido MB " + le.getMessage());
		} catch (Exception e) {
			LOG.error("******************************Error de Aplicacion " + e.getMessage(), e);
			throw new UploadGaleriaBOException(e);

		}
	}

	public boolean createFolders(String directorio) throws UploadGaleriaBOException {
		boolean success = false;
		try {
			File carpetas = new File(directorio);
			if (!carpetas.exists()) {
				success = carpetas.mkdirs();
			} else
				success = true;
		} catch (Exception e) {
			success = false;
			LOG.error("Ocurrio error al crear las carpetas: ", e);
			throw new UploadGaleriaBOException("Ocurrio error al crear las carpetas: " + e.getMessage());
		}
		return success;
	}
	
	
	

}
