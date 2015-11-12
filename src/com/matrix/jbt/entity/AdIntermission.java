package com.matrix.jbt.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * ad entity
 * @author JacksonLi
 * @date 2014/3/13
 */
@XmlRootElement
public class AdIntermission {
	private int adId;
	private String path;
	private int showTime;
	// 0:Intermission Ad 1:Banner at the bottom
	private int type;
	// 0: Available 1: Unavailable default 1
	private int usable;

	public AdIntermission(String path, int showTime, int type, int usable) {
		super();
		this.path = path;
		this.showTime = showTime;
		this.type = type;
		this.usable = usable;
	}

	public AdIntermission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getShowTime() {
		return showTime;
	}

	public void setShowTime(int showTime) {
		this.showTime = showTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUsable() {
		return usable;
	}

	public void setUsable(int usable) {
		this.usable = usable;
	}

	@Override
	public String toString() {
		return "AdIntermission [adId=" + adId + ", path=" + path
				+ ", showTime=" + showTime + ", type=" + type + ", usable="
				+ usable + "]";
	}

}
