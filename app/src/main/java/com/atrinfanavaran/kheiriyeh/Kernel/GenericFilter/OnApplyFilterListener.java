package com.atrinfanavaran.kheiriyeh.Kernel.GenericFilter;



import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.FilteredDomain;

import java.util.HashMap;

public interface OnApplyFilterListener {
    void onApply(HashMap<Integer, FilteredDomain> domainInfos);
}
