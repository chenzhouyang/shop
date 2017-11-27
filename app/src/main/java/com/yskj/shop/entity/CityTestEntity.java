package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YSKJ-JH on 2017/2/11.
 */

public class CityTestEntity {

    /**
     * region_id : 1
     * local_name : 北京市
     * region_grade : 1
     * p_region_id : 0
     * childnum : 1
     * zipcode : 110000
     * cod : 0
     */

    @SerializedName("region_id")
    public int regionId;
    @SerializedName("local_name")
    public String localName;
    @SerializedName("region_grade")
    public int regionGrade;
    @SerializedName("p_region_id")
    public int pRegionId;
    @SerializedName("childnum")
    public int childnum;
    @SerializedName("zipcode")
    public String zipcode;
    @SerializedName("cod")
    public String cod;
}
