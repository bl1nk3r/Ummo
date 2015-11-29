package com.example.barnes.ummo.ummoAPI;

/**
 * Created by barnes on 10/4/15.
 */
public class QUserInfo
{
    String qcategoryid;
    String qcategoryname;
    String qserviceproviderid;
    String qserviceprovidername;
    String qserviceid;
    String qservicename;
    String userCellNumber;

    public QUserInfo(String qcategoryid, String qcategoryname, String qserviceproviderid,
                     String qserviceprovidername, String qserviceid, String qservicename, String userCellNumber)
    {
        this.setQcategoryid(qcategoryid);
        this.setQcategoryname(qcategoryname);
        this.setQServiceproviderid(qserviceproviderid);
        this.setQServiceprovidername(qserviceprovidername);
        this.setQServiceid(qserviceid);
        this.setQServicename(qservicename);
        this.setUserCellNumber(userCellNumber);
    }

    public void setQcategoryid(String qcategoryid)
    {
        this.qcategoryid = qcategoryid;
    }

    public void setQcategoryname(String qcategoryname)
    {
        this.qcategoryname = qcategoryname;
    }

    public void setQServiceproviderid(String qserviceproviderid)
    {
        this.qserviceproviderid = qserviceproviderid;
    }

    public void setQServiceprovidername(String qserviceprovidername)
    {
        this.qserviceprovidername = qserviceprovidername;
    }

    public void setQServiceid(String qserviceid)
    {
        this.qserviceid = qserviceid;
    }

    public void setQServicename(String qservicename)
    {
        this.qservicename = qservicename;
    }

    public void setUserCellNumber(String userCellNumber)
    {
        this.userCellNumber = userCellNumber;
    }

    public String getQcategoryid()
    {
        return qcategoryid;
    }

    public String getQcategoryname()
    {
        return qcategoryname;
    }

    public String getQServiceProviderid()
    {
        return qserviceproviderid;
    }

    public String getQServiceProvidername()
    {
        return qserviceprovidername;
    }

    public String getQserviceid()
    {
        return qserviceid;
    }

    public String getQservicename()
    {
        return qservicename;
    }

    public String getUserCellNumber()
    {
        return userCellNumber;
    }
}