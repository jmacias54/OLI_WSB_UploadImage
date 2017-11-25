package mx.com.amx.unotv.wsb.oli.uploadimg.bo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import mx.com.amx.unotv.wsb.oli.uploadimg.bo.exception.CropImageBOException;
import mx.com.amx.unotv.wsb.oli.uploadimg.dto.ParametrosDTO;
import mx.com.amx.unotv.wsb.oli.uploadimg.dto.ProcesaImagenDTO;
import mx.com.amx.unotv.wsb.oli.uploadimg.model.ImageRequest;
import mx.com.amx.unotv.wsb.oli.uploadimg.util.CargaProperties;
import mx.com.amx.unotv.wsb.oli.uploadimg.util.Scalr;

public class CropImageBO {

	private static Logger LOG = Logger.getLogger(CropImageBO.class);

	@Value("${${ambiente}.domain}")
	private String domain;

	public String cropImage(ImageRequest request) throws CropImageBOException {

		
		CargaProperties cargaProperties = new CargaProperties();
		ParametrosDTO parametrosDTO = cargaProperties.obtenerPropiedades("ambiente.resources.properties");

		BufferedImage image = null;
		File sourceimage = null;
	
		String nameImageSquare="";

		try {

			sourceimage = new File(parametrosDTO.getPathLocalImagenes() + request.getNameImage());
			image = ImageIO.read(sourceimage);

			String[] arrayNombreFile = request.getNameImage().split("\\.");
			String extensionFile = arrayNombreFile[arrayNombreFile.length - 1];

			
			String[] arrayConfImgCuadrada = parametrosDTO.getConfiguracionImgCuadrada().split("\\|");

			String[] arraySecuencia = arrayNombreFile[0].split("\\-");
			int secuencia = Integer.valueOf(arraySecuencia[0]);

			

				ProcesaImagenDTO procesaImagenDTOCuadrada = new ProcesaImagenDTO();
				procesaImagenDTOCuadrada.setDirectorio(parametrosDTO.getPathLocalImagenes());
				procesaImagenDTOCuadrada.setExtensionFile(extensionFile);
				procesaImagenDTOCuadrada.setNombre(arrayConfImgCuadrada[2]);
				procesaImagenDTOCuadrada.setSecuencia(secuencia);
				procesaImagenDTOCuadrada.setTargetWidthm(Integer.parseInt(arrayConfImgCuadrada[0]));
				procesaImagenDTOCuadrada.setTargetHeight(Integer.parseInt(arrayConfImgCuadrada[1]));

				procesaImagenCuadrada(image, procesaImagenDTOCuadrada);

				

				nameImageSquare = procesaImagenDTOCuadrada.getSecuencia() + procesaImagenDTOCuadrada.getNombre()
						+ procesaImagenDTOCuadrada.getExtensionFile();
				
			


		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(" Error cropImage [BO] ", e);

			throw new CropImageBOException(e.getMessage());
		}catch (Exception e) {
			LOG.error("Exception en cropImage ", e);
			throw new CropImageBOException(e.getMessage());
		}

		return nameImageSquare;
	}



	private void procesaImagenCuadrada(BufferedImage bufferedImage, ProcesaImagenDTO procesaImagenDTO )
			throws CropImageBOException {
		LOG.debug("Inicia procesaImagen Cuadrada en CropdImageBO");
		try {

			BufferedImage img = Scalr.crop(bufferedImage,procesaImagenDTO.getTargetWidthm(),
					procesaImagenDTO.getTargetHeight(), Scalr.OP_ANTIALIAS);

			ByteArrayOutputStream baosM = new ByteArrayOutputStream();
			ImageIO.write(img, procesaImagenDTO.getExtensionFile(), baosM);
			baosM.flush();
			byte[] imageMInByte = baosM.toByteArray();
			baosM.close();
			FileOutputStream fosM = new FileOutputStream(
					procesaImagenDTO.getDirectorio() + procesaImagenDTO.getSecuencia() + procesaImagenDTO.getNombre()
							+ procesaImagenDTO.getExtensionFile());
			fosM.write(imageMInByte);
			fosM.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(" Error cropImage [BO] ", e);

			throw new CropImageBOException(e.getMessage());
		} catch (Exception e) {
			LOG.error("Exception en procesaImagen ", e);
			throw new CropImageBOException(e.getMessage());
		}
	}

}
