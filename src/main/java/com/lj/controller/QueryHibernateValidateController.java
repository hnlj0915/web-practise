package com.lj.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lj.common.base.AbstractBaseController;
import com.lj.common.message.Msg;
import com.lj.common.param.QueryParams;
import com.lj.common.utils.json.JsonpCallbackView;
import com.lj.common.validate.group.CheckQueryParamGroup;
import com.lj.common.validate.utils.ValidatorMessageUtil;

/**
 * @Title: TestQueryController.java
 * @Package com.b5m.test.controller
 * @Description: TODO(测试参数访问控制层)
 * @author jia.liu
 * @date 2014-5-16 上午10:47:07
 * @version V1.0
 */
@Controller
@RequestMapping("/param/hibernate")
public class QueryHibernateValidateController extends AbstractBaseController {

	@Resource(name = "hValidator")
	private Validator validator;

	@RequestMapping("/validate")
	public ModelAndView querySetBaseByHibernateValidate(QueryParams param, HttpServletResponse response) {
		try {
			Class<?>[] groups = { CheckQueryParamGroup.class };
			Msg msg = ValidatorMessageUtil.getValidateErrorMessage(validator, param, groups);
			logger.logInfoMsg("error message:" + msg.getData());
			return JsonpCallbackView.Render(msg, param.getJsonpCallback(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonpCallbackView.Render(new Msg(false, "error"), param.getJsonpCallback(), response);
	}
}
