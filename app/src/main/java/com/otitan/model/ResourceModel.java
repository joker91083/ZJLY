package com.otitan.model;

import java.io.Serializable;

/**
 * 资源管理
 */
public class ResourceModel implements Serializable {

    /**
     * 林地面积
     */
    public class LDMJModel implements Serializable {
        //地区编码
        private String Code;
        //地区名称
        private String Name;
        //林地总面积
        private String Area;
        //乔木林地
        private String QMLD;
        //竹林地
        private String ZLD;
        //疏林地
        private String SLD;
        //	特殊灌木林地
        private String TSGMLD;
        //	一般灌木林地
        private String YBGMLD;
        //	未成林造林地
        private String WCLZLD;
        //宜林地
        private String YLD;
        //	其它
        private String Other;

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String area) {
            Area = area;
        }

        public String getQMLD() {
            return QMLD;
        }

        public void setQMLD(String QMLD) {
            this.QMLD = QMLD;
        }

        public String getZLD() {
            return ZLD;
        }

        public void setZLD(String ZLD) {
            this.ZLD = ZLD;
        }

        public String getSLD() {
            return SLD;
        }

        public void setSLD(String SLD) {
            this.SLD = SLD;
        }

        public String getTSGMLD() {
            return TSGMLD;
        }

        public void setTSGMLD(String TSGMLD) {
            this.TSGMLD = TSGMLD;
        }

        public String getYBGMLD() {
            return YBGMLD;
        }

        public void setYBGMLD(String YBGMLD) {
            this.YBGMLD = YBGMLD;
        }

        public String getWCLZLD() {
            return WCLZLD;
        }

        public void setWCLZLD(String WCLZLD) {
            this.WCLZLD = WCLZLD;
        }

        public String getYLD() {
            return YLD;
        }

        public void setYLD(String YLD) {
            this.YLD = YLD;
        }

        public String getOther() {
            return Other;
        }

        public void setOther(String other) {
            Other = other;
        }
    }

    /**
     * 森林面积
     */
    public class SLMJModel implements Serializable {
        //地区编码
        private String Code;
        //地区名称
        private String Name;
        //林地总面积
        private String Area;

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String area) {
            Area = area;
        }
    }

    /**
     * 公益林面积
     */
    public class GYLMJModel implements Serializable {
        //地区编码
        private String Code;
        //地区名称
        private String Name;
        //国家级公益林
        private String CountryArea;
        //地方公益林
        private String LocalArea;
        //合计
        private String Area;

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getCountryArea() {
            return CountryArea;
        }

        public void setCountryArea(String countryArea) {
            CountryArea = countryArea;
        }

        public String getLocalArea() {
            return LocalArea;
        }

        public void setLocalArea(String localArea) {
            LocalArea = localArea;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String area) {
            Area = area;
        }
    }

    /**
     * 森林覆盖率
     */
    public class SLFGLModel implements Serializable {
        //地区编码
        private String Code;
        //地区名称
        private String Name;
        //森林覆盖率
        private double Area;

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public double getArea() {
            return Area;
        }

        public void setArea(double area) {
            Area = area;
        }
    }

    /**
     *  活立木蓄积
     */
    public class HLMXJModel implements Serializable {
        //地区编码
        private String Code;
        //地区名称
        private String Name;
        //活立木蓄积
        private double Area;

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public double getArea() {
            return Area;
        }

        public void setArea(double area) {
            Area = area;
        }
    }
}
