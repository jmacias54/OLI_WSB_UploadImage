package mx.com.amx.unotv.wsb.oli.uploadimg.util;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;

import mx.com.amx.unotv.wsb.oli.uploadimg.dto.ParametrosDTO;



public class CargaProperties {
	
	private Logger logger=Logger.getLogger(CargaProperties.class);
			
	public ParametrosDTO obtenerPropiedades(String properties) {
		ParametrosDTO parametros = new ParametrosDTO();
		try {
			Properties propsTmp = new Properties();
			propsTmp.load(this.getClass().getResourceAsStream( "/general.properties" ));
			String ambiente = propsTmp.getProperty("ambiente");
			String rutaProperties = propsTmp.getProperty(properties.replace("ambiente", ambiente));			
			Properties props = new Properties();
			props.load(new FileInputStream(new File(rutaProperties)));				
			
			parametros.setPathLocalImagenes(props.getProperty("pathLocalImagenes"));
			parametros.setPathLocalImagenesGaleria(props.getProperty("pathLocalImagenesGaleria"));
			parametros.setPathLocalImagenesInfografia(props.getProperty("pathLocalImagenesInfografia"));
			parametros.setPathLocalImagenesClaroIdeas(props.getProperty("pathLocalImagenesClaroIdeas"));
			
			
			parametros.setExtFiles(props.getProperty("extFiles") == null? null: props.getProperty("extFiles").split(","));
			parametros.setMaxMegas(Integer.parseInt(props.getProperty("maxMegas")));
			parametros.setURL_WS_BASE(props.getProperty("URL_WS_BASE"));
			parametros.setAmbiente(props.getProperty("ambiente"));
			
			parametros.setMaxKBImagenes(Integer.parseInt(props.getProperty("maxKBImagenes")));
			parametros.setMaxKBImagenesGaleria(Integer.parseInt(props.getProperty("maxKBImagenesGaleria")));
			parametros.setMaxKBImagenesInfografia(Integer.parseInt(props.getProperty("maxKBImagenesInfografia")));
			
			parametros.setPathLocalBanners(props.getProperty("pathLocalBanners"));

			parametros.setPathLocalBannersJSON(props.getProperty("pathLocalBannersJSON"));
			parametros.setNameBannersJSON(props.getProperty("nameBannersJSON"));
			
			parametros.setConfiguracionImgPrincipal(props.getProperty("configuracionImgPrincipal"));
			parametros.setConfiguracionImgCuadrada(props.getProperty("configuracionImgCuadrada"));
			parametros.setConfiguracionImgFB(props.getProperty("configuracionImgFB"));
			
			
			parametros.setUrl_ws_utils(props.getProperty("URL_WS_Utils"));
			parametros.setMetodo_seq_NextVal(props.getProperty("metodo_seq_NextVal"));
			parametros.setSecuenciaImagenes(props.getProperty("secuenciaImagenes"));
			
			
			
			
		} catch (Exception ex) {
			parametros = new ParametrosDTO();
			logger.error("No se encontro el Archivo de propiedades: ", ex);
		}
		return parametros;
    }
	
}
