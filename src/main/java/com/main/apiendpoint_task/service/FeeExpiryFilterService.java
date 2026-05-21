package com.main.apiendpoint_task.service;

import com.main.apiendpoint_task.entity.FeeExpiryFilter;
import com.main.apiendpoint_task.repo.FeeExpiryFilterRepo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FeeExpiryFilterService {

    private final FeeExpiryFilterRepo feeExpiryFilterRepo;

    public  FeeExpiryFilterService(FeeExpiryFilterRepo feeExpiryFilterRepo) {
        this.feeExpiryFilterRepo = feeExpiryFilterRepo;
    }

    public Map<String , Object> getDashboard(String search,String sortBy, String direction,int page,int size,String bucket){
        List<FeeExpiryFilter> records=feeExpiryFilterRepo.getDashboardData(search);

        Comparator<FeeExpiryFilter> comparator=Comparator.comparing(FeeExpiryFilter::getExpiryDate,Comparator.nullsLast(java.time.LocalDate::compareTo));

//        if(sortBy!=null){
//            switch (sortBy){
//                case "clientName":
//                    comparator= Comparator.comparing(FeeExpiryFilter::getClientName,Comparator.nullsLast(String::compareToIgnoreCase));
//                    break;
//                case "feeLabel":
//                    comparator=Comparator.comparing(FeeExpiryFilter::getFeeLabel,Comparator.nullsLast(String::compareToIgnoreCase));
//                    break;
//                case "expiryDate":
//                    comparator=Comparator.comparing(FeeExpiryFilter::getExpiryDate,Comparator.nullsLast(java.time.LocalDate::compareTo));
//                    break;
//            }
//        }

        if ("clientName".equals(sortBy)) {
            comparator = Comparator.comparing(FeeExpiryFilter::getClientName,
                    Comparator.nullsLast(String::compareToIgnoreCase));
        } else if ("feeLabel".equals(sortBy)) {
            comparator = Comparator.comparing(FeeExpiryFilter::getFeeLabel,
                    Comparator.nullsLast(String::compareToIgnoreCase));
        }

        if("desc".equalsIgnoreCase(direction)){
            comparator=comparator.reversed();
        }

        records.sort(comparator);

        Map<String, List<FeeExpiryFilter>> grouped=new HashMap<>();
        grouped.put("ONE_MONTH",new ArrayList<>());
        grouped.put("THREE_MONTH",new ArrayList<>());
        grouped.put("SIX_MONTH",new ArrayList<>());

        for(FeeExpiryFilter feeExpiryFilter:records){
            if(feeExpiryFilter.getBucket()!=null){
                grouped.get(feeExpiryFilter.getBucket()).add(feeExpiryFilter);
            }
        }

        Map<String, Object> summary=Map.of(
                "oneMonth",grouped.get("ONE_MONTH").size(),
                "threeMonths",grouped.get("THREE_MONTH").size(),
                "sixMonths",grouped.get("SIX_MONTH").size()
        );

        List<FeeExpiryFilter> selectedList;

        if(bucket==null){
            selectedList=records;
        } else{
            selectedList=grouped.getOrDefault(bucket,new ArrayList<>());
        }

        int start=page*size;
        int end=Math.min(start+size,selectedList.size());
        List<FeeExpiryFilter> paginatedList;
        if(start>=selectedList.size()) {
            paginatedList = new ArrayList<>();
        } else{
            paginatedList=selectedList.subList(start,end);
        }

//        for(FeeExpiryFilter feeExpiryFilter:records){
//            switch (feeExpiryFilter.getBucket()){
//                case "ONE_MONTH":grouped.get("oneMonth").add(feeExpiryFilter);
//                break;
//                case "THREE_MONTH":grouped.get("threeMonths").add(feeExpiryFilter);
//                break;
//                case "SIX_MONTH":grouped.get("sixMonths").add(feeExpiryFilter);
//                break;
//            }
//        }

        Map<String, Object> response=new HashMap<>();
//        response.put("summary",Map.of(
//                "oneMonth",grouped.get("ONE_MONTH").size(),
//                "threeMonths",grouped.get("THREE_MONTH").size(),
//                "sixMonths",grouped.get("SIX_MONTH").size()
//        ));
        response.put("summary",summary);
        response.put("data",paginatedList);

        response.put("page",page);
        response.put("size",size);
        response.put("totalElements",records.size());
        response.put("totalPages",(int)Math.ceil((double)records.size()/size));

        return response;
    }
//    public
}
