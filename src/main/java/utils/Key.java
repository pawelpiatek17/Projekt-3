package utils;

public class Key {
	private int xId;
	private int yId;
	private int zId;
	
	public Key(int xId, int yId, int zId) {
		super();
		this.xId = xId;
		this.yId = yId;
		this.zId = zId;
	}
	public Key(Key other) {
		this.xId = other.getxId();
		this.yId = other.getyId();
		this.zId = other.getzId();
	}
	public int getxId() {
		return xId;
	}

	public int getyId() {
		return yId;
	}

	public int getzId() {
		return zId;
	}
	
	public void setxId(int xId) {
		this.xId = xId;
	}
	public void setyId(int yId) {
		this.yId = yId;
	}
	public void setzId(int zId) {
		this.zId = zId;
	}
	public Key incrementXId() {
		xId++;
		return this;
	}
	public Key decrementXId() {
		xId--;
		return this;
	}
	public Key incrementYId() {
		yId++;
		return this;
	}
	public Key decrementYId() {
		yId--;
		return this;
	}
	public Key incrementZId() {
		zId++;
		return this;
	}
	public Key decrementZId() {
		zId--;
		return this;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xId;
		result = prime * result + yId;
		result = prime * result + zId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		if (xId != other.xId)
			return false;
		if (yId != other.yId)
			return false;
		if (zId != other.zId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Key [xId=" + xId + ", yId=" + yId + ", zId=" + zId + "]";
	}
	
}
