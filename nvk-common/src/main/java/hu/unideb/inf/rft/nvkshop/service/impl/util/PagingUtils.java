package hu.unideb.inf.rft.nvkshop.service.impl.util;

public class PagingUtils {

	public static int calculateFirstItem(int pageSize,int pageNumber){
		return (pageNumber-1)*pageSize;
	}
	
}
