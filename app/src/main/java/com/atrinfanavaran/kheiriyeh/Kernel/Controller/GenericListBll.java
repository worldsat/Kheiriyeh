package com.atrinfanavaran.kheiriyeh.Kernel.Controller;

import android.content.Context;

import com.google.gson.Gson;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.DomainInfo;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.Filter;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Domain.ViewMode;
import com.atrinfanavaran.kheiriyeh.Kernel.Controller.Interface.CallBackSpinner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class GenericListBll {
    private final Controller controller;

    public GenericListBll(Context context) {
        this.controller = new Controller(context);
    }

    public void populateSpinner(String id, ArrayList<Filter> filter, String viewType, String apiAddress, CallBackSpinner callBackSpinner) {
        Gson gson = new Gson();
        String filterStr = "";
        if (filter != null && !filter.isEmpty()) {
            filterStr = gson.toJson(filter);
        }

        controller.PopulateFilterSpinner(id, filterStr, null, viewType, apiAddress, callBackSpinner);
    }

    public void populateSpinner(String id, ArrayList<Filter> filter, DomainInfo item, String viewType, String apiAddress, CallBackSpinner callBackSpinner) {
        Gson gson = new Gson();
        String filterStr = "";
        if (filter != null && !filter.isEmpty()) {
            filterStr = gson.toJson(filter);
        }

        controller.PopulateFilterSpinner(id, filterStr, item, viewType, apiAddress, callBackSpinner);
    }

    public void populateSpinner(String id, String viewType, String apiAddress, CallBackSpinner callBackSpinner) {
        controller.PopulateFilterSpinner(id, null, null, viewType, apiAddress, callBackSpinner);
    }

    public ArrayList<DomainInfo> getDomainInfo(Class domain, Object object) {
        return controller.GetDomainInfo(domain, object);
    }

    public ArrayList<DomainInfo> getDomainInfo(Class domain) {
        return controller.GetDomainInfo(domain);
    }

    public HashMap<String, String> parseObject(Object instance, ArrayList<DomainInfo> metaData) {
        HashMap<String, String> result = new HashMap<>();

        try {
            Class<?> aClass = instance.getClass();
            Method[] declaredMethods = aClass.getDeclaredMethods();

            for (int i = 0; i < declaredMethods.length; i++) {
                if (declaredMethods[i].getName().contains("get")) {

                    Object value = declaredMethods[i].invoke(instance);
                    String methodName = declaredMethods[i].getName();
                    String field = methodName.substring(3);
                    for (int j = 0; j < metaData.size(); j++) {
                        if (field.equalsIgnoreCase(metaData.get(j).getId())) {
                            result.put(field, value.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<DomainInfo> getEditDomains(ArrayList<DomainInfo> metaData) {
        ArrayList<DomainInfo> filteredByMode = new ArrayList<>();
        for (DomainInfo item : metaData) {
            String viewMode = item.getViewMode();
            if (viewMode != null) {
                if (viewMode.equals(ViewMode.EDIT.name()) ||
                        viewMode.equals(ViewMode.ALL.name()) ||
                        viewMode.equals(ViewMode.CHART_EDIT.name()) ||
                        viewMode.equals(ViewMode.FILTER_EDIT.name())
                ) {
                    filteredByMode.add(item);
                }
            }
        }
        return filteredByMode;
    }


    public HashMap<String, String> getIds(Object instance, ArrayList<DomainInfo> metaData) {
        HashMap<String, String> result = new HashMap<>();

        try {
            Class<?> aClass = instance.getClass();
            Method[] declaredMethods = aClass.getDeclaredMethods();

            for (int i = 0; i < declaredMethods.length; i++) {
                if (declaredMethods[i].getName().contains("get")) {

                    Object value = declaredMethods[i].invoke(instance);
                    String methodName = declaredMethods[i].getName();
                    String field = methodName.substring(3);
                    for (int j = 0; j < metaData.size(); j++) {
                        if (field.equalsIgnoreCase(metaData.get(j).getId())) {

//                            if(field.equals("ParentId")){
//                                InspectionTiming inspectionTiming = (InspectionTiming)instance;
//                                result.put("ParentId", inspectionTiming.getInspectionTimingName());
//                            }else {

                            if (value == null) {
                                result.put(field, "");

                            } else {
                                result.put(field, value.toString());
                            }
//                            }

                            break;
                        }
                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public ArrayList<DomainInfo> getFilterDomains(ArrayList<DomainInfo> metaData) {
        ArrayList<DomainInfo> filteredByMode = new ArrayList<>();
        for (DomainInfo item : metaData) {
            String viewMode = item.getViewMode();
            if (viewMode != null) {
                if (viewMode.equals(ViewMode.FILTER.name()) ||
                        viewMode.equals(ViewMode.ALL.name()) ||
                        viewMode.equals(ViewMode.FILTER_EDIT.name()) ||
                        viewMode.equals(ViewMode.CHART_FILTER.name())
                ) {
                    filteredByMode.add(item);
                }
            }
        }
        return filteredByMode;
    }

    public HashMap<HashMap<String, String>, Boolean> getRequired(Object instance, ArrayList<DomainInfo> metaData) {
        HashMap<HashMap<String, String>, Boolean> result = new HashMap<>();

        try {
            Class<?> aClass = instance.getClass();
            Method[] declaredMethods = aClass.getDeclaredMethods();

            for (int i = 0; i < declaredMethods.length; i++) {
                if (declaredMethods[i].getName().contains("get")) {

                    Object value = declaredMethods[i].invoke(instance);
                    String methodName = declaredMethods[i].getName();
                    String field = methodName.substring(3);
                    for (int j = 0; j < metaData.size(); j++) {
                        if (field.equalsIgnoreCase(metaData.get(j).getId())) {

                            Boolean isRequired = metaData.get(j).isRequired();
                            String Title = metaData.get(j).getTitle();

                            HashMap<String, String> idTitle = new HashMap<>();
                            idTitle.put(field, Title);
//                            if(field.equals("ParentId")){
//                                InspectionTiming inspectionTiming = (InspectionTiming)instance;
//                                result.put("ParentId", inspectionTiming.getInspectionTimingName());
//                            }else {
                            result.put(idTitle, isRequired);
//                            }

                            break;
                        }
                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
