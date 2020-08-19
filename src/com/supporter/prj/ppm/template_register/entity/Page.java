package com.supporter.prj.ppm.template_register.entity;

import java.util.List;

/**
 * jqGrid 对应的实体类
 * <pre>
 *  用于装载jqGrid分页数据的实体类
 *  适用于jqGrid JS - v5.1.0
 * </pre>
 * 
 * @author duancunming
 * @createDate 2016-11-14
 * @since 无
 * @modifier duancunming
 * @modifiedDate 2016-11-14
 */
public class Page<T> implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public static final String PAGE = "page";
	public static final String ROWS = "rows";
	public static final String SIDX = "sidx";
	public static final String SORD = "sord";
	
	private String records;
	private int page;
	private Long total;
	private int fromIndex;
	private int toIndex;
	private List<T> rows;

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * Constructor for creation of table model.
	 * 
	 * @param pageNumber the current page number selected by user
	 * @param rowsAmountLimit the amount rows that we want to see
	 * @param recordsTotalAmount the total amount of all records
	 * @param sortedColumnIndex the index of column which we are going to sort by
	 * @param sortOrder the sort order 
	 */
	public Page(int pageNumber, int rowsAmountLimit, Long recordsTotalAmount, String sortedColumnIndex, String sortOrder) {
		this.records = String.valueOf(recordsTotalAmount);
		// calculate the total pages for the query
		if (recordsTotalAmount > 0 && rowsAmountLimit > 0) {
			this.total = ((Double) Math.ceil((double) recordsTotalAmount / rowsAmountLimit)).longValue();
		}
		this.page = pageNumber;

		// if for some reasons the requested page is greater than the total
		// set the requested page to total page
		if (total != null && this.page > total) {
			this.page = total.intValue();
		} else if(total == null)  {
			this.page = 0;
		}

		// calculate the starting position of the rows
		this.fromIndex = (this.page - 1) * rowsAmountLimit;
		// if for some reasons start position is negative set it to 0
		// typical case is that the user type 0 for the requested page
		if (this.fromIndex < 0) {
			this.fromIndex = 0;
		}
		this.toIndex = this.fromIndex + rowsAmountLimit;

		if (this.toIndex > recordsTotalAmount) {
			this.toIndex = recordsTotalAmount.intValue();
		}
	}

	/**
	 * Get start row index.
	 * 
	 * @return the index
	 */
	public int getFromIndex() {
		return fromIndex;
	}

	/**
	 * Get end row index.
	 * 
	 * @return the index
	 */
	public int getToIndex() {
		return toIndex;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
