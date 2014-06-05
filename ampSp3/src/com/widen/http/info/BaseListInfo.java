package com.widen.http.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;

public class BaseListInfo<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<T> list;
	public boolean hasMore;
	private Map<String, Object> extras;

	/**
	 * 如果不是每次�?0�?则修改此参数
	 */
	public int onceCount = 20;
	
	public boolean contains(Object other){	
		return list.contains(other);
	}
	
	public void addAll(int location,BaseListInfo<T> other) {
		
		if(other !=null && ((BaseListInfo<T>) other).list != null){
			if (list == null) {
				list = new ArrayList<T>();
			}
			list.addAll(location, other.list);
		}
	}

	public void addAll(BaseListInfo<T> other) {
		if (other != null && other.list != null) {
			if (list == null) {
				list = new ArrayList<T>();
			}
			list.addAll(other.list);
			hasMore = other.size() >= onceCount;
		}else if(other == null || other.list == null || other.list.size() == 0){
			hasMore = false;
		}
	}

	@SuppressWarnings("unchecked")
	public void addAll(Object other) {
		if (other != null && ((BaseListInfo<T>) other).list != null) {
			if (list == null) {
				list = new ArrayList<T>();
			}
			list.addAll(((BaseListInfo<T>) other).list);
			hasMore = ((BaseListInfo<T>) other).list.size() >= onceCount;
		}
	}

	public int size() {
		return list == null ? 0 : list.size();
	}

	public T get(int position) {
		return list == null || list.size() <= position ? null : list
				.get(position);
	}

	public void add(T t) {
		if (list == null) {
			list = new ArrayList<T>();
		}
		list.add(t);
	}

	public void remove(int position) {
		if (list != null) {
			list.remove(position);
		}
	}

	public boolean isEmpty() {
		return list == null || list.isEmpty();
	}

	public void putExtraData(String key, Object value) {
		if (extras == null) {
			extras = new HashMap<String, Object>();
		}
		extras.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public void copyExtra(Object other) {
		if (other != null) {
			extras = ((BaseListInfo<T>) other).extras;
		}
	}

	public Object getExtraData(String key) {

		return extras == null ? null : extras.get(key);
	}

	public void remove(Object obj) {
		if (list != null) {
			list.remove(obj);
		}
	}

	public void clear() {
		if (list != null) {
			list.clear();
		}
		if (extras != null) {
			extras.clear();
		}
	}
}
