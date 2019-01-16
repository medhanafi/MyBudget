package com.kmsoft.budget.model;

import java.beans.Transient;
import java.io.Serializable;

public abstract class AbstractEntity<K extends Serializable> implements Serializable {

	private static final long serialVersionUID = -1468203776652816108L;

	@Transient
	public abstract K primaryKey();

}
