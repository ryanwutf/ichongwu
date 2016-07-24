package co.ichongwu.vidser.common.dao;


public class PutResult<I> {

	private int affected;

	private I id;

    public PutResult(int affected, I id) {
    	this.affected = affected;
        this.id = id;
    }

	public int getAffected() {
		return affected;
	}

	public void setAffected(int affected) {
		this.affected = affected;
	}

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }


}