package com.atrinfanavaran.kheiriyeh.Domain;

import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.BaseDomain;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewMode;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewType;

import java.util.ArrayList;

public class ContributionApi extends BaseDomain {
    public ContributionApi() {
        ArrayList<DomainInfo> domainInfoList = new ArrayList<>();
//        domainInfoList.add(new DomainInfo(
//                ViewMode.FILTER.name(),
//                "name",
//                "حامی ",
//                "",
//                ViewType.EDIT_TEXT.name())
//        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "price",
                "مبلغ",
                "",
                ViewType.EDIT_TEXT.name())
        );
        domainInfoList.add(new DomainInfo(
                ViewMode.FILTER.name(),
                "description",
                "توضیحات",
                "",
                ViewType.EDIT_TEXT.name())
        );
        setDomainInfo(domainInfoList);
    }
}
