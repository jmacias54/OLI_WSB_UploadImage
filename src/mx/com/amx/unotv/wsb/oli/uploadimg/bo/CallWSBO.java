package mx.com.amx.unotv.wsb.oli.uploadimg.bo;


import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import mx.com.amx.unotv.wsb.oli.uploadimg.bo.exception.CallWSBOException;
import mx.com.amx.unotv.wsb.oli.uploadimg.dto.ParametrosDTO;

public class CallWSBO {

	private static Logger LOG = Logger.getLogger(CallWSBO.class);
	private RestTemplate restTemplate;	
	private HttpHeaders headers =  new HttpHeaders();
		
	/*
	 * Constructor
	 * */
	public CallWSBO()
	{		
		try {
				
				restTemplate = new RestTemplate();
				ClientHttpRequestFactory factory = restTemplate.getRequestFactory();

		        if ( factory instanceof SimpleClientHttpRequestFactory)
		        {
		            ((SimpleClientHttpRequestFactory) factory).setConnectTimeout( 15 * 1000 );
		            ((SimpleClientHttpRequestFactory) factory).setReadTimeout( 15 * 1000 );
		            LOG.info("Inicializando rest template");
		        }
		        else if ( factory instanceof HttpComponentsClientHttpRequestFactory)
		        {
		            ((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout( 15 * 1000);
		            ((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout( 15 * 1000);
		            LOG.info("Inicializando rest template");
		        }
		        restTemplate.setRequestFactory( factory );
		        headers.setContentType(MediaType.APPLICATION_JSON);
			
		} catch (Exception e) {
			LOG.error("Exception en Constructor EnviaPushIonicCallWSBO",e);
		}		
		
	}
	
	
	/**
	 * Metodo que regresa la secuencia para el nombre de la imagen
	 * @param nameSeq
	 * @param ParametrosDTO
	 * @return Integer
	 * @throws CallWSBOException
	 * @author Fernando Aviles
	 * */
	public Integer getSecuencia( ParametrosDTO parametrosDTO) throws CallWSBOException
	{
		LOG.debug("Inicia getSecuencia en CallWSBO");

		try {			
			String url_ws = parametrosDTO.getUrl_ws_utils()+parametrosDTO.getMetodo_seq_NextVal();
			LOG.debug("call url_ws: "+url_ws);
			HttpEntity<String> entity = new HttpEntity<String>("Accept=application/json; charset=utf-8", headers);
			return restTemplate.postForObject(url_ws, entity, Integer.class);	
			
		} catch (Exception e) {
			LOG.error("Exception en getSecuencia: ", e);
			throw new CallWSBOException(e.getMessage());
		}	
	}
	
	
}//FIN CLASE
