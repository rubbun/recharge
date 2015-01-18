package com.example.bean;

public class HistoryBean {

	public String order_id, operator, service_no, amount, status, date_time,
			profit, optId, detail;

	public HistoryBean(String order_id, String operator, String service_no,
			String amount, String status, String date_time, String profit,
			String optId, String detail) {
		super();
		this.order_id = order_id;
		this.operator = operator;
		this.service_no = service_no;
		this.amount = amount;
		this.status = status;
		this.date_time = date_time;
		this.profit = profit;
		this.optId = optId;
		this.detail = detail;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getService_no() {
		return service_no;
	}

	public void setService_no(String service_no) {
		this.service_no = service_no;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getOptId() {
		return optId;
	}

	public void setOptId(String optId) {
		this.optId = optId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
