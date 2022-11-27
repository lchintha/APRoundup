package com.vrctech.aproundup.activities.epaper;

import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.Globals;
import com.vrctech.aproundup.JSH;
import com.vrctech.aproundup.R;

import org.json.JSONObject;

import java.util.ArrayList;

class NewsPapers {

    static ArrayList<Epaper> getPapersList(String latestDate, JSONObject response){
        String formattedDate = GlobalMethods.getFormattedDate(latestDate);
        ArrayList<Epaper> epapers = new ArrayList<>();
        epapers.add(new Epaper(
                R.drawable.logo_eenadu,
                R.string.eenadu,
                formattedDate,
                getEenaduEditions(latestDate, response)
        ));
        return epapers;
    }

    private static ArrayList<Edition> getEenaduEditions(String originalDate, JSONObject response){
        ArrayList<Edition> editions = new ArrayList<>();
        editions.add(new Edition(
                R.string.andhrapradesh,
                Globals.CODE_EENADU_AP,
                R.drawable.ic_ap,
                originalDate,
                getPages(response, Globals.CODE_EENADU_AP, originalDate)));
        editions.add(new Edition(
                R.string.hyderabad,
                Globals.CODE_EENADU_HYD,
                R.drawable.ic_charminar,
                originalDate,
                getPages(response, Globals.CODE_EENADU_HYD, originalDate)));
        editions.add(new Edition(
                R.string.telangana,
                Globals.CODE_EENADU_TS,
                R.drawable.ic_telangana,
                originalDate,
                getPages(response, Globals.CODE_EENADU_TS, originalDate)));
        return editions;
    }

    private static ArrayList<String> getPages(JSONObject response, String code, String originalDate){
        JSONObject datesObject = JSH.getJSONObject(response, code);
        return JSH.getListOfStrings(datesObject, originalDate);
    }

}
