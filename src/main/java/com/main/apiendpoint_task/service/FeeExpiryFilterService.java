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

    public Map<String , Object> getDashboard(String search,String sortBy, String direction,int page,int size){
        List<FeeExpiryFilter> records=feeExpiryFilterRepo.getDashboardData(search);

        Comparator<FeeExpiryFilter> comparator=Comparator.comparing(FeeExpiryFilter::getExpiryDate,Comparator.nullsLast(java.time.LocalDate::compareTo));

        if(sortBy!=null){
            switch (sortBy){
                case "clientName":
                    comparator= Comparator.comparing(FeeExpiryFilter::getClientName,Comparator.nullsLast(String::compareToIgnoreCase));
                    break;
                case "feeLabel":
                    comparator=Comparator.comparing(FeeExpiryFilter::getFeeLabel,Comparator.nullsLast(String::compareToIgnoreCase));
                    break;
                case "expiryDate":
                    comparator=Comparator.comparing(FeeExpiryFilter::getExpiryDate,Comparator.nullsLast(java.time.LocalDate::compareTo));
                    break;
            }
        }

        if("desc".equalsIgnoreCase(direction)){
            comparator=comparator.reversed();
        }

        records.sort(comparator);

        int start=page*size;
        int end=Math.min(start+size,records.size());
        List<FeeExpiryFilter> paginatedList;
        if(start>=records.size()) {
            paginatedList = new ArrayList<>();
        } else{
            paginatedList=records.subList(start,end);
        }

        Map<String, List<FeeExpiryFilter>> grouped=new HashMap<>();
        grouped.put("oneMonth",new ArrayList<>());
        grouped.put("threeMonths",new ArrayList<>());
        grouped.put("sixMonths",new ArrayList<>());

        for(FeeExpiryFilter feeExpiryFilter:records){
            switch (feeExpiryFilter.getBucket()){
                case "ONE_MONTH":grouped.get("oneMonth").add(feeExpiryFilter);
                break;
                case "THREE_MONTH":grouped.get("threeMonths").add(feeExpiryFilter);
                break;
                case "SIX_MONTH":grouped.get("sixMonths").add(feeExpiryFilter);
                break;
            }
        }

        Map<String, Object> response=new HashMap<>();
        response.put("summary",Map.of(
                "oneMonth",grouped.get("oneMonth").size(),
                "threeMonths",grouped.get("threeMonths").size(),
                "sixMonths",grouped.get("sixMonths").size()
        ));

        response.put("data",grouped);

        response.put("page",page);
        response.put("size",size);
        response.put("totalElements",records.size());
        response.put("totalPages",(int)Math.ceil((double)records.size()/size));

        return response;
    }
//    public
}
