/**
 * 
 */
package mx.com.amx.unotv.wsb.oli.uploadimg.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.com.amx.unotv.wsb.oli.uploadimg.bo.CropImageBO;
import mx.com.amx.unotv.wsb.oli.uploadimg.bo.UploadImageBO;
import mx.com.amx.unotv.wsb.oli.uploadimg.controller.HomeController;
import mx.com.amx.unotv.wsb.oli.uploadimg.controller.exception.ControllerException;
import mx.com.amx.unotv.wsb.oli.uploadimg.model.ImageResponse;

/**
 * @author Jesus A. Macias Benitez
 *
 */
@Controller
@RequestMapping("imageServices")
public class HomeController {

	private static Logger logger = Logger.getLogger(HomeController.class);
	
	
	@Autowired
	private UploadImageBO uploadImageBO;

	@Autowired
	private CropImageBO cropImageBO;

	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8")
	@ResponseBody
	public ImageResponse uploadImage(HttpServletRequest request) throws ControllerException {

		ImageResponse response = null;

		logger.info(" -- imageServices Controller  --");
		logger.info(" -- METHOD uploadImage --");
		logger.info(" -- HttpServletRequest : " + request);

		logger.info("** Inicia uploadImage **");
		try {
			response = uploadImageBO.uploadImage(request);
		} catch (Exception e) {
			logger.error(" -- Error   cropImage [Controller]:", e);
			throw new ControllerException(e.getMessage());
		}
		return response;

	}


}
