package com.stockmarketservice.dto;

public class CompanyDto {
		
		private String id;
		private String companyCode;
		private String companyName;
		private String ceo;
		private Long companyTurnover;
		private String companyWebsite;
		private String stockExchangeName;
		private String sectorId;
		private String companyDescription;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
		public String getCompanyCode() {
			return companyCode;
		}
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getCeo() {
			return ceo;
		}
		public void setCeo(String ceo) {
			this.ceo = ceo;
		}
		public Long getCompanyTurnover() {
			return companyTurnover;
		}
		public void setCompanyTurnover(Long companyTurnover) {
			this.companyTurnover = companyTurnover;
		}
		public String getCompanyWebsite() {
			return companyWebsite;
		}
		public void setCompanyWebsite(String companyWebsite) {
			this.companyWebsite = companyWebsite;
		}
		public String getStockExchangeName() {
			return stockExchangeName;
		}
		public void setStockExchangeName(String stockExchangeName) {
			this.stockExchangeName = stockExchangeName;
		}
		public String getSectorId() {
			return sectorId;
		}
		public void setSectorId(String sectorId) {
			this.sectorId = sectorId;
		}
		public String getCompanyDescription() {
			return companyDescription;
		}
		public void setCompanyDescription(String companyDescription) {
			this.companyDescription = companyDescription;
		}

}
