package mx.com.amx.unotv.wsb.oli.uploadimg.bo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import mx.com.amx.unotv.wsb.oli.uploadimg.bo.exception.UploadGaleriaBOException;
import mx.com.amx.unotv.wsb.oli.uploadimg.bo.exception.UploadImageBOException;
import mx.com.amx.unotv.wsb.oli.uploadimg.dto.ParametrosDTO;
import mx.com.amx.unotv.wsb.oli.uploadimg.dto.ProcesaImagenDTO;
import mx.com.amx.unotv.wsb.oli.uploadimg.model.ImageResponse;
import mx.com.amx.unotv.wsb.oli.uploadimg.util.CargaProperties;
import mx.com.amx.unotv.wsb.oli.uploadimg.util.Scalr;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class UploadImageBO {

	private static Logger LOG = Logger.getLogger(UploadImageBO.class);

	@Autowired
	private CallWSBO callWSBO;

	@Value("${${ambiente}.domain}")
	private String domain;

	/**
	 * Metodo que carga y trata las imagenes de UnoTV
	 * 
	 */
	public ImageResponse uploadImage(HttpServletRequest request) throws UploadImageBOException {
		LOG.debug("Inicia uploadImage en UploadImageBO");
		try {

			ImageResponse response = new ImageResponse();
		
			// Cargamos archivos de propiedades
			CargaProperties cargaProperties = new CargaProperties();
			ParametrosDTO parametrosDTO = cargaProperties.obtenerPropiedades("ambiente.resources.properties");

			int maxBites = 1024 * parametrosDTO.getMaxKBImagenes();
			LOG.debug("maxBites: " + maxBites);
			// Cargamos la imagen que viene en el request
			DiskFileUpload fu = new DiskFileUpload();
			fu.setSizeMax(maxBites); // 80 KB
			fu.setSizeThreshold(maxBites);
			fu.setRepositoryPath("/var");
			List<FileItem> fileItems = fu.parseRequest(request);

			Iterator<FileItem> i = fileItems.iterator();
			FileItem fileItemActual = null;
			String fileItemName = "";
			String nameImgPrincipal = "";
			String nameImgRezise = "";

			while (i.hasNext()) {

				fileItemActual = (FileItem) i.next();
				fileItemName = fileItemActual.getName();
				if (fileItemName != null && !fileItemName.equals("")) {

					File fichero = new File(fileItemName);

					LOG.info("Imagen seleccionada:" + fichero.getName());
					long sizeFile = fileItemActual.getSize();

					// Validamos la extesion del fichero
					String[] arrayNombreFile = fichero.getName().split("\\.");
					String extensionFile = arrayNombreFile[arrayNombreFile.length - 1];

					boolean isValidExtension = false;

					// Recorremos las extensiones validas
					for (String strExtensionesValidas : parametrosDTO.getExtFiles()) {
						if (strExtensionesValidas.equals(extensionFile))
							isValidExtension = true;
					}

					// Tiene una extension valida
					if (isValidExtension) {
						// No supera el tamaño permitido
						if (sizeFile <= maxBites) {

							boolean continuar = false;
							String directorio = parametrosDTO.getPathLocalImagenes();
							Integer secuencia = 0;

							continuar = createFolders(directorio);

							if (continuar) {

								// Obtenemos la secuencia
								secuencia = callWSBO.getSecuencia(parametrosDTO);
								LOG.debug("secuencia: " + secuencia);

								// Imagen principal
								InputStream insInputStreamImg = new ByteArrayInputStream(fileItemActual.get());
								BufferedImage bImageFromConvert = ImageIO.read(insInputStreamImg);

								// Imagen principal
								// Configuracion del archivo de propiedades
								String[] arrayConfImgPrincipal = parametrosDTO.getConfiguracionImgPrincipal()
										.split("\\|");
								
								// String[] arrayConfImgResize = parametrosDTO.getConfiguracionImgCuadrada()
								// 		.split("\\|");

								// Nombre de la imagen principal
								nameImgPrincipal = secuencia + arrayConfImgPrincipal[2] + extensionFile;
								
								// Nombre de la imagen resize
								// nameImgRezise = secuencia + arrayConfImgResize[2] + extensionFile;

								try {

									// Llenamos DTO
									ProcesaImagenDTO procesaImagenDTO = new ProcesaImagenDTO();
									procesaImagenDTO.setDirectorio(parametrosDTO.getPathLocalImagenes());
									procesaImagenDTO.setExtensionFile(extensionFile);
									procesaImagenDTO.setNombre(arrayConfImgPrincipal[2]);
									procesaImagenDTO.setSecuencia(secuencia);
									procesaImagenDTO.setTargetWidthm(Integer.parseInt(arrayConfImgPrincipal[0]));
									procesaImagenDTO.setTargetHeight(Integer.parseInt(arrayConfImgPrincipal[1]));
									procesaImagen(bImageFromConvert, procesaImagenDTO);
									LOG.info("Genera imagen principal: OK");

									
									/*
									// Llenamos DTO
									ProcesaImagenDTO procesaImagenResize = new ProcesaImagenDTO();
									procesaImagenResize.setDirectorio(parametrosDTO.getPathLocalImagenes());
									procesaImagenResize.setExtensionFile(extensionFile);
									procesaImagenResize.setNombre(arrayConfImgResize[2]);
									procesaImagenResize.setSecuencia(secuencia);
									procesaImagenResize.setTargetWidthm(Integer.parseInt(arrayConfImgResize[0]));
									procesaImagenResize.setTargetHeight(Integer.parseInt(arrayConfImgResize[1]));
									procesaImagen(bImageFromConvert, procesaImagenResize);
									LOG.info("Genera imagen Rezise : OK");
									
								*/
									
						

								} catch (Exception e) {
									LOG.error("Error uploadImage" + e);
									throw new UploadImageBOException(e);
								}

							}// end if continuar 

						} else {
							LOG.info("Se intento subir un archivo de mayor tamaño " + sizeFile + " MB");
							nameImgPrincipal = "MAXTAM";
							throw new UploadImageBOException(
									"Se intento subir un archivo de mayor tamaño " + sizeFile + " MB");
						}
					} else {
						LOG.info("Extencion incorrecta");
						nameImgPrincipal = "TIPOAR";
						throw new UploadImageBOException("Extencion incorrecta");
					}
				} // FIN IF fileName
			} // FIN WHILE
			LOG.info("Nombre imagen generada: " + nameImgPrincipal);
			response.setNameImagePrincipal(nameImgPrincipal);
			response.setNameImageSquare(nameImgRezise);
			response.setPathImages(domain);

			return response;
		} catch (SizeLimitExceededException le) {
			LOG.error("SizeLimitExceededException", le);
			throw new UploadImageBOException(le);
		} catch (IOException e) {
			LOG.error("Exception en uploadImage ", e);
			throw new UploadImageBOException(e);
		} catch (Exception e) {
			LOG.error("Exception en uploadImage ", e);
			throw new UploadImageBOException(e);
		}
	}

	public boolean createFolders(String directorio) throws UploadGaleriaBOException {
		boolean success = false;
		try {
			File carpetas = new File(directorio);
			if (!carpetas.exists()) {
				success = carpetas.mkdirs();
			} else {
				success = true;
			}
		} catch (Exception e) {
			success = false;
			LOG.error("Ocurrio error al crear las carpetas: ", e);
			throw new UploadGaleriaBOException("Ocurrio error al crear las carpetas: " + e.getMessage());
		}
		return success;
	}

	/**
	 * Metodo que hace el resize y renombra la imagen. @param BufferedImage @param
	 * ProcesaImagenDTO @return @throws
	 *
	 */
	private void procesaImagen(BufferedImage bufferedImage, ProcesaImagenDTO procesaImagenDTO) throws UploadImageBOException
	{
		LOG.debug("Inicia procesaImagen en UploadImageBO");
		try {								
			BufferedImage img = Scalr.resize(bufferedImage, Scalr.Method.SPEED, Scalr.Mode.FIT_EXACT, procesaImagenDTO.getTargetWidthm(), procesaImagenDTO.getTargetHeight(), Scalr.OP_ANTIALIAS);								
			ByteArrayOutputStream baosM = new ByteArrayOutputStream();
			ImageIO.write( img, procesaImagenDTO.getExtensionFile(), baosM );
			baosM.flush();
			byte[] imageMInByte = baosM.toByteArray();
			baosM.close();
			FileOutputStream fosM = new FileOutputStream(procesaImagenDTO.getDirectorio() + procesaImagenDTO.getSecuencia() + procesaImagenDTO.getNombre() + procesaImagenDTO.getExtensionFile());
			fosM.write(imageMInByte);
			fosM.close();			
		} catch (Exception e) {
			LOG.error("Exception en procesaImagen ",e);
			throw new UploadImageBOException(e.getMessage());
		} 	 
	}
	


}
