package vo;

public class StationInfoVO {
	private String station;
	private String bus_name;
	private String nowBus;
	private String nextBus;
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getBus_name() {
		return bus_name;
	}
	public void setBus_name(String bus_name) {
		this.bus_name = bus_name;
	}
	public String getNowBus() {
		return nowBus;
	}
	public void setNowBus(String nowBus) {
		this.nowBus = nowBus;
	}
	public String getNextBus() {
		return nextBus;
	}
	public void setNextBus(String nextBus) {
		this.nextBus = nextBus;
	}
}
