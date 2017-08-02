package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

public enum enumConfigData {
	AndroidVersionName  (1),
	API_LOGIN	(2),
	API_VERSION   (3),
	DomainKalbe   (4),
	ApplicationName   (5),
	TextFooter   (6),
	BackGroundServiceOnline   (7),
	API_GETLASTLOCATION(8),
	API_PUSHDATA(9),
	LIVE   (10)
	;
	enumConfigData(int idConfigData) {
		this.idConfigData = idConfigData;
	}
	public int getidConfigData() {
		return this.idConfigData;
	}
	private  final int idConfigData;
}
