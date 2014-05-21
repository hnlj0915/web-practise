package com.lj.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lj.common.base.AbstractBaseController;
import com.lj.common.message.Msg;
import com.lj.common.param.QueryParam;
import com.lj.common.utils.json.JsonpCallbackView;

/**
 * @Title: TestQueryController.java
 * @Package com.b5m.test.controller
 * @Description: TODO(测试参数访问控制层)
 * @author jia.liu
 * @date 2014-5-16 上午10:47:07
 * @version V1.0
 */
@Controller
@RequestMapping("/param/spirng")
public class QuerySpringValidateController extends AbstractBaseController {

	@Resource(name = "paramValidator")
	private Validator validator;

	@Resource
	private MessageSource messageSource;

	@RequestMapping("/validate")
	public ModelAndView querySetBaseBySpringValidate(QueryParam param, BindingResult result, HttpServletResponse response) {
		param.setParams(new String[] { "userId" });
		validator.validate(param, result);

		if (result.hasErrors()) {
			String message = messageSource.getMessage(result.getFieldError().getCode(), null, null);
			System.out.println(message);
			return JsonpCallbackView.Render(new Msg(false, message), param.getJsonpCallback(), response);
		}

		return JsonpCallbackView.Render(new Msg(100, true, "----ss----"), param.getJsonpCallback(), response);
	}

}
