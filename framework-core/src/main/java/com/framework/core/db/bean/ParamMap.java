package com.framework.core.db.bean;

import java.util.HashMap;
import java.util.Map;

public class ParamMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public ParamMap() {
		super();
	}

	public ParamMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public ParamMap(int initialCapacity) {
		super(initialCapacity);
	}

	public ParamMap(Map<? extends String, ? extends Object> m) {
		super(m);
	}

	@Override
	public ParamMap put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Object originPut(String key, Object value) {
		return super.put(key, value);
	}

	public static ParamMap newOne() {
		return new ParamMap();
	}

	public static ParamMap newOne(String key, Object value) {
		return new ParamMap().put(key, value);
	}

}
