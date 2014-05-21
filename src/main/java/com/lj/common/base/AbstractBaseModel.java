package com.lj.common.base;

import java.io.Serializable;

import com.lj.log.Loggers;

/**
 * @author lscm
 * 
 */
public class AbstractBaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3374265856878484500L;

	protected Loggers logger = new Loggers(AbstractBaseModel.class.getName());

	public AbstractBaseModel() {
		logger = new Loggers(this.getClass().getName());
	}

}
