package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

public enum enumStatusMenuStart {
	 FormLogin  (1),
	 UserActiveLogin  (2),  
	 PushDataAbsenHRMobile (3),
	 PushDataSalesForce (4);
	
	enumStatusMenuStart(int id) {
			this.idConfigData = id;
	}
	public int getidData() {
        return this.idConfigData;
    }
	private  final int idConfigData;
}
