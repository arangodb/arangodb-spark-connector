package com.arangodb.spark.java;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

@SuppressWarnings("serial")
public class TestPhoneEntity implements Serializable {

	private String key;
	private PhoneAddr phone;
	
	

	public TestPhoneEntity() {
		super();
		
		key = RandomStringUtils.random(5);
		
		phone = new PhoneAddr();
		phone.random();
		
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public PhoneAddr getPhone() {
		return phone;
	}

	public void setPhone(PhoneAddr phone) {
		this.phone = phone;
	}

	public class PhoneAddr implements Serializable {

		private String PartyAddr_PARTY_ID;
		private String PartyAddr_ADDR_ID;
		private String PartyAddr_ADDR_PURP_TYP_ID;
		private String PartyAddr_ADDR_TYP_ID;
		private String PartyAddr_CONF_ADDR_IN;
		private Date PartyAddr_AD_EFF_DT;
		private Date PartyAddr_AD_END_DT;
		private String PartyAddr_AD_CMNT_TX;
		private String PartyAddr_MAIL_ADDR_IN;
		private String PartyAddr_PREF_ADDR_IN;
		private String PartyAddr_CALLBACK_IN;
		private String PartyAddr_ENTP_PRIM_ADDR_IN;
		private String PartyAddr_TAX_JUR_CD;
		private String PartyAddr_ADD_USR_ID;
		private Date PartyAddr_ADD_TS;
		private String PartyAddr_UPD_USR_ID;
		private Date PartyAddr_UPD_TS;
		private String PartyAddr_ENTP_AD_IN;
		private String PartyAddr_SRC_APPL_CD;
		private String ADDR_TYP_ID;
		private String ADDR_TYP_NM;
		private String ADDR_PURP_TYP_ID;
		private String ADDR_PURP_TYP_DESC_TX;
		private String PhoneAddr_ADDR_ID;
		private String PhoneAddr_UNFRMTTED_PHN_NO_AD;
		private String PhoneAddr_FRMTTED_PHN_NO_AD;
		private String PhoneAddr_INTL_DIALING_CD;
		private String PhoneAddr_CTRY_ISO2_CD;
		private String PhoneAddr_PHN_EXT_AD;
		private String PhoneAddr_NON_NANP_IN;
		private String PhoneAddr_UNCNVRTD_PHN_NO_AD;
		private String PhoneAddr_ADD_USR_ID;
		private Date PhoneAddr_ADD_TS;
		private String PhoneAddr_UPD_USR_ID;
		private Date PhoneAddr_UPD_TS;

		public String getPartyAddr_PARTY_ID() {
			return PartyAddr_PARTY_ID;
		}

		public void setPartyAddr_PARTY_ID(String partyAddr_PARTY_ID) {
			PartyAddr_PARTY_ID = partyAddr_PARTY_ID;
		}

		public String getPartyAddr_ADDR_ID() {
			return PartyAddr_ADDR_ID;
		}

		public void setPartyAddr_ADDR_ID(String partyAddr_ADDR_ID) {
			PartyAddr_ADDR_ID = partyAddr_ADDR_ID;
		}

		public String getPartyAddr_ADDR_PURP_TYP_ID() {
			return PartyAddr_ADDR_PURP_TYP_ID;
		}

		public void setPartyAddr_ADDR_PURP_TYP_ID(String partyAddr_ADDR_PURP_TYP_ID) {
			PartyAddr_ADDR_PURP_TYP_ID = partyAddr_ADDR_PURP_TYP_ID;
		}

		public String getPartyAddr_ADDR_TYP_ID() {
			return PartyAddr_ADDR_TYP_ID;
		}

		public void setPartyAddr_ADDR_TYP_ID(String partyAddr_ADDR_TYP_ID) {
			PartyAddr_ADDR_TYP_ID = partyAddr_ADDR_TYP_ID;
		}

		public String getPartyAddr_CONF_ADDR_IN() {
			return PartyAddr_CONF_ADDR_IN;
		}

		public void setPartyAddr_CONF_ADDR_IN(String partyAddr_CONF_ADDR_IN) {
			PartyAddr_CONF_ADDR_IN = partyAddr_CONF_ADDR_IN;
		}

		public Date getPartyAddr_AD_EFF_DT() {
			return PartyAddr_AD_EFF_DT;
		}

		public void setPartyAddr_AD_EFF_DT(Date partyAddr_AD_EFF_DT) {
			PartyAddr_AD_EFF_DT = partyAddr_AD_EFF_DT;
		}

		public Date getPartyAddr_AD_END_DT() {
			return PartyAddr_AD_END_DT;
		}

		public void setPartyAddr_AD_END_DT(Date partyAddr_AD_END_DT) {
			PartyAddr_AD_END_DT = partyAddr_AD_END_DT;
		}

		public String getPartyAddr_AD_CMNT_TX() {
			return PartyAddr_AD_CMNT_TX;
		}

		public void setPartyAddr_AD_CMNT_TX(String partyAddr_AD_CMNT_TX) {
			PartyAddr_AD_CMNT_TX = partyAddr_AD_CMNT_TX;
		}

		public String getPartyAddr_MAIL_ADDR_IN() {
			return PartyAddr_MAIL_ADDR_IN;
		}

		public void setPartyAddr_MAIL_ADDR_IN(String partyAddr_MAIL_ADDR_IN) {
			PartyAddr_MAIL_ADDR_IN = partyAddr_MAIL_ADDR_IN;
		}

		public String getPartyAddr_PREF_ADDR_IN() {
			return PartyAddr_PREF_ADDR_IN;
		}

		public void setPartyAddr_PREF_ADDR_IN(String partyAddr_PREF_ADDR_IN) {
			PartyAddr_PREF_ADDR_IN = partyAddr_PREF_ADDR_IN;
		}

		public String getPartyAddr_CALLBACK_IN() {
			return PartyAddr_CALLBACK_IN;
		}

		public void setPartyAddr_CALLBACK_IN(String partyAddr_CALLBACK_IN) {
			PartyAddr_CALLBACK_IN = partyAddr_CALLBACK_IN;
		}

		public String getPartyAddr_ENTP_PRIM_ADDR_IN() {
			return PartyAddr_ENTP_PRIM_ADDR_IN;
		}

		public void setPartyAddr_ENTP_PRIM_ADDR_IN(String partyAddr_ENTP_PRIM_ADDR_IN) {
			PartyAddr_ENTP_PRIM_ADDR_IN = partyAddr_ENTP_PRIM_ADDR_IN;
		}

		public String getPartyAddr_TAX_JUR_CD() {
			return PartyAddr_TAX_JUR_CD;
		}

		public void setPartyAddr_TAX_JUR_CD(String partyAddr_TAX_JUR_CD) {
			PartyAddr_TAX_JUR_CD = partyAddr_TAX_JUR_CD;
		}

		public String getPartyAddr_ADD_USR_ID() {
			return PartyAddr_ADD_USR_ID;
		}

		public void setPartyAddr_ADD_USR_ID(String partyAddr_ADD_USR_ID) {
			PartyAddr_ADD_USR_ID = partyAddr_ADD_USR_ID;
		}

		public Date getPartyAddr_ADD_TS() {
			return PartyAddr_ADD_TS;
		}

		public void setPartyAddr_ADD_TS(Date partyAddr_ADD_TS) {
			PartyAddr_ADD_TS = partyAddr_ADD_TS;
		}

		public String getPartyAddr_UPD_USR_ID() {
			return PartyAddr_UPD_USR_ID;
		}

		public void setPartyAddr_UPD_USR_ID(String partyAddr_UPD_USR_ID) {
			PartyAddr_UPD_USR_ID = partyAddr_UPD_USR_ID;
		}

		public Date getPartyAddr_UPD_TS() {
			return PartyAddr_UPD_TS;
		}

		public void setPartyAddr_UPD_TS(Date partyAddr_UPD_TS) {
			PartyAddr_UPD_TS = partyAddr_UPD_TS;
		}

		public String getPartyAddr_ENTP_AD_IN() {
			return PartyAddr_ENTP_AD_IN;
		}

		public void setPartyAddr_ENTP_AD_IN(String partyAddr_ENTP_AD_IN) {
			PartyAddr_ENTP_AD_IN = partyAddr_ENTP_AD_IN;
		}

		public String getPartyAddr_SRC_APPL_CD() {
			return PartyAddr_SRC_APPL_CD;
		}

		public void setPartyAddr_SRC_APPL_CD(String partyAddr_SRC_APPL_CD) {
			PartyAddr_SRC_APPL_CD = partyAddr_SRC_APPL_CD;
		}

		public String getADDR_TYP_ID() {
			return ADDR_TYP_ID;
		}

		public void setADDR_TYP_ID(String aDDR_TYP_ID) {
			ADDR_TYP_ID = aDDR_TYP_ID;
		}

		public String getADDR_TYP_NM() {
			return ADDR_TYP_NM;
		}

		public void setADDR_TYP_NM(String aDDR_TYP_NM) {
			ADDR_TYP_NM = aDDR_TYP_NM;
		}

		public String getADDR_PURP_TYP_ID() {
			return ADDR_PURP_TYP_ID;
		}

		public void setADDR_PURP_TYP_ID(String aDDR_PURP_TYP_ID) {
			ADDR_PURP_TYP_ID = aDDR_PURP_TYP_ID;
		}

		public String getADDR_PURP_TYP_DESC_TX() {
			return ADDR_PURP_TYP_DESC_TX;
		}

		public void setADDR_PURP_TYP_DESC_TX(String aDDR_PURP_TYP_DESC_TX) {
			ADDR_PURP_TYP_DESC_TX = aDDR_PURP_TYP_DESC_TX;
		}

		public String getPhoneAddr_ADDR_ID() {
			return PhoneAddr_ADDR_ID;
		}

		public void setPhoneAddr_ADDR_ID(String phoneAddr_ADDR_ID) {
			PhoneAddr_ADDR_ID = phoneAddr_ADDR_ID;
		}

		public String getPhoneAddr_UNFRMTTED_PHN_NO_AD() {
			return PhoneAddr_UNFRMTTED_PHN_NO_AD;
		}

		public void setPhoneAddr_UNFRMTTED_PHN_NO_AD(String phoneAddr_UNFRMTTED_PHN_NO_AD) {
			PhoneAddr_UNFRMTTED_PHN_NO_AD = phoneAddr_UNFRMTTED_PHN_NO_AD;
		}

		public String getPhoneAddr_FRMTTED_PHN_NO_AD() {
			return PhoneAddr_FRMTTED_PHN_NO_AD;
		}

		public void setPhoneAddr_FRMTTED_PHN_NO_AD(String phoneAddr_FRMTTED_PHN_NO_AD) {
			PhoneAddr_FRMTTED_PHN_NO_AD = phoneAddr_FRMTTED_PHN_NO_AD;
		}

		public String getPhoneAddr_INTL_DIALING_CD() {
			return PhoneAddr_INTL_DIALING_CD;
		}

		public void setPhoneAddr_INTL_DIALING_CD(String phoneAddr_INTL_DIALING_CD) {
			PhoneAddr_INTL_DIALING_CD = phoneAddr_INTL_DIALING_CD;
		}

		public String getPhoneAddr_CTRY_ISO2_CD() {
			return PhoneAddr_CTRY_ISO2_CD;
		}

		public void setPhoneAddr_CTRY_ISO2_CD(String phoneAddr_CTRY_ISO2_CD) {
			PhoneAddr_CTRY_ISO2_CD = phoneAddr_CTRY_ISO2_CD;
		}

		public String getPhoneAddr_PHN_EXT_AD() {
			return PhoneAddr_PHN_EXT_AD;
		}

		public void setPhoneAddr_PHN_EXT_AD(String phoneAddr_PHN_EXT_AD) {
			PhoneAddr_PHN_EXT_AD = phoneAddr_PHN_EXT_AD;
		}

		public String getPhoneAddr_NON_NANP_IN() {
			return PhoneAddr_NON_NANP_IN;
		}

		public void setPhoneAddr_NON_NANP_IN(String phoneAddr_NON_NANP_IN) {
			PhoneAddr_NON_NANP_IN = phoneAddr_NON_NANP_IN;
		}

		public String getPhoneAddr_UNCNVRTD_PHN_NO_AD() {
			return PhoneAddr_UNCNVRTD_PHN_NO_AD;
		}

		public void setPhoneAddr_UNCNVRTD_PHN_NO_AD(String phoneAddr_UNCNVRTD_PHN_NO_AD) {
			PhoneAddr_UNCNVRTD_PHN_NO_AD = phoneAddr_UNCNVRTD_PHN_NO_AD;
		}

		public String getPhoneAddr_ADD_USR_ID() {
			return PhoneAddr_ADD_USR_ID;
		}

		public void setPhoneAddr_ADD_USR_ID(String phoneAddr_ADD_USR_ID) {
			PhoneAddr_ADD_USR_ID = phoneAddr_ADD_USR_ID;
		}

		public Date getPhoneAddr_ADD_TS() {
			return PhoneAddr_ADD_TS;
		}

		public void setPhoneAddr_ADD_TS(Date phoneAddr_ADD_TS) {
			PhoneAddr_ADD_TS = phoneAddr_ADD_TS;
		}

		public String getPhoneAddr_UPD_USR_ID() {
			return PhoneAddr_UPD_USR_ID;
		}

		public void setPhoneAddr_UPD_USR_ID(String phoneAddr_UPD_USR_ID) {
			PhoneAddr_UPD_USR_ID = phoneAddr_UPD_USR_ID;
		}

		public Date getPhoneAddr_UPD_TS() {
			return PhoneAddr_UPD_TS;
		}

		public void setPhoneAddr_UPD_TS(Date phoneAddr_UPD_TS) {
			PhoneAddr_UPD_TS = phoneAddr_UPD_TS;
		}

		public void random() {
			
			PartyAddr_PARTY_ID = null;
			PartyAddr_ADDR_ID = null;
			PartyAddr_ADDR_PURP_TYP_ID = null;
			PartyAddr_ADDR_TYP_ID = RandomStringUtils.random(15);
			PartyAddr_CONF_ADDR_IN = RandomStringUtils.random(15);
			PartyAddr_AD_EFF_DT = new Date();
			PartyAddr_AD_END_DT = new Date();
			PartyAddr_AD_CMNT_TX = RandomStringUtils.random(15);
			PartyAddr_MAIL_ADDR_IN = RandomStringUtils.random(15);
			PartyAddr_PREF_ADDR_IN = RandomStringUtils.random(15);
			PartyAddr_CALLBACK_IN = RandomStringUtils.random(15);
			PartyAddr_ENTP_PRIM_ADDR_IN = RandomStringUtils.random(15);
			PartyAddr_TAX_JUR_CD = RandomStringUtils.random(15);
			PartyAddr_ADD_USR_ID = RandomStringUtils.random(15);
			PartyAddr_ADD_TS = new Date();
			PartyAddr_UPD_USR_ID = RandomStringUtils.random(15);
			PartyAddr_UPD_TS = new Date();
			PartyAddr_ENTP_AD_IN = RandomStringUtils.random(15);
			PartyAddr_SRC_APPL_CD = RandomStringUtils.random(15);
			ADDR_TYP_ID = RandomStringUtils.random(15);
			ADDR_TYP_NM = RandomStringUtils.random(15);
			ADDR_PURP_TYP_ID = RandomStringUtils.random(15);
			ADDR_PURP_TYP_DESC_TX = RandomStringUtils.random(15);
			PhoneAddr_ADDR_ID = RandomStringUtils.random(15);
			PhoneAddr_UNFRMTTED_PHN_NO_AD = RandomStringUtils.random(15);
			PhoneAddr_FRMTTED_PHN_NO_AD = RandomStringUtils.random(15);
			PhoneAddr_INTL_DIALING_CD = RandomStringUtils.random(15);
			PhoneAddr_CTRY_ISO2_CD = RandomStringUtils.random(15);
			PhoneAddr_PHN_EXT_AD = RandomStringUtils.random(15);
			PhoneAddr_NON_NANP_IN = RandomStringUtils.random(15);
			PhoneAddr_UNCNVRTD_PHN_NO_AD = RandomStringUtils.random(15);
			PhoneAddr_ADD_USR_ID = RandomStringUtils.random(15);
			PhoneAddr_ADD_TS = new Date();
			PhoneAddr_UPD_USR_ID = RandomStringUtils.random(15);
			PhoneAddr_UPD_TS = new Date();
			
		}

	}

}
