package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

public enum enumConfigData {
	AndroidVersionName  (1),
	API_PRM	(2),
	DomainKalbe   (3),
	ApplicationName   (4),
	TextFooter   (5),
	BackGroundServiceOnline   (6),
	API_EF(7),
	LIVE   (8)
	;
	enumConfigData(int idConfigData) {
		this.idConfigData = idConfigData;
	}
	public int getidConfigData() {
		return this.idConfigData;
	}
	private  final int idConfigData;
}
