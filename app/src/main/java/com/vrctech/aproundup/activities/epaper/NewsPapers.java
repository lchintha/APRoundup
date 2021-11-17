package com.vrctech.aproundup.activities.epaper;

import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.Globals;
import com.vrctech.aproundup.R;

import java.util.ArrayList;

class NewsPapers {

    static ArrayList<Epaper> getPapersList(String latestDate){
        String formattedDate = GlobalMethods.getFormattedDate(latestDate);
        ArrayList<Epaper> epapers = new ArrayList<>();
        /*epapers.add(new Epaper(R.drawable.logo_andhrajyothi,
                R.string.andhrajyothy,
                formattedDate,
                getAndhrajyothiEditions(formattedDate)));*/
        epapers.add(new Epaper(R.drawable.logo_sakshi,
                R.string.sakshi,
                formattedDate,
                getSakshiEditions(formattedDate)));
        epapers.add(new Epaper(R.drawable.logo_vaartha,
                R.string.vaartha,
                formattedDate,
                getVaarthaEditions(formattedDate)));
        epapers.add(new Epaper(R.drawable.logo_surya,
                R.string.surya,
                formattedDate,
                getSuryaEditions(formattedDate)));
        epapers.add(new Epaper(R.drawable.logo_prabha,
                R.string.andhraprabha,
                formattedDate,
                getPrabhaEditions(formattedDate)));
        epapers.add(new Epaper(R.drawable.logo_prajasakti,
                R.string.prajasakti,
                formattedDate,
                getPrajasaktiEditions(formattedDate)));
        return epapers;
    }

    private static ArrayList<Edition> getAndhrajyothiEditions(String date){
        ArrayList<Edition> editions = new ArrayList<>();
        editions.add(new Edition(R.string.andhrapradesh, Globals.CODE_ANDHRAJYOTHI_AP, R.drawable.ic_ap, date));
        editions.add(new Edition(R.string.hyderabad, Globals.CODE_ANDHRAJYOTHI_HYD, R.drawable.ic_charminar, date));
        editions.add(new Edition(R.string.telangana, Globals.CODE_ANDHRAJYOTHI_TS, R.drawable.ic_telangana, date));
        return editions;
    }

    private static ArrayList<Edition> getSakshiEditions(String date){
        ArrayList<Edition> editions = new ArrayList<>();
        editions.add(new Edition(R.string.andhrapradesh, Globals.CODE_SAKSHI_AP, R.drawable.ic_ap, date));
        editions.add(new Edition(R.string.hyderabad, Globals.CODE_SAKSHI_HYD, R.drawable.ic_charminar, date));
        editions.add(new Edition(R.string.telangana, Globals.CODE_SAKSHI_TS, R.drawable.ic_telangana, date));
        return editions;
    }

    private static ArrayList<Edition> getVaarthaEditions(String date){
        ArrayList<Edition> editions = new ArrayList<>();
        editions.add(new Edition(R.string.andhrapradesh, Globals.CODE_VAARTHA_AP, R.drawable.ic_ap, date));
        editions.add(new Edition(R.string.hyderabad, Globals.CODE_VAARTHA_HYD, R.drawable.ic_charminar, date));
        editions.add(new Edition(R.string.telangana, Globals.CODE_VAARTHA_TS, R.drawable.ic_telangana, date));
        return editions;
    }

    private static ArrayList<Edition> getSuryaEditions(String date){
        ArrayList<Edition> editions = new ArrayList<>();
        editions.add(new Edition(R.string.andhrapradesh, Globals.CODE_SURYA_AP, R.drawable.ic_ap, date));
        editions.add(new Edition(R.string.telangana, Globals.CODE_SURYA_TS, R.drawable.ic_telangana, date));
        return editions;
    }

    private static ArrayList<Edition> getPrabhaEditions(String date){
        ArrayList<Edition> editions = new ArrayList<>();
        editions.add(new Edition(R.string.andhrapradesh, Globals.CODE_PRABHA_AP, R.drawable.ic_ap, date));
        editions.add(new Edition(R.string.hyderabad, Globals.CODE_PRABHA_HYD, R.drawable.ic_charminar, date));
        editions.add(new Edition(R.string.telangana, Globals.CODE_PRABHA_TS, R.drawable.ic_telangana, date));
        return editions;
    }

    private static ArrayList<Edition> getPrajasaktiEditions(String date){
        ArrayList<Edition> editions = new ArrayList<>();
        editions.add(new Edition(R.string.andhrapradesh, Globals.CODE_PRAJASAKTI_AP, R.drawable.ic_ap, date));
        editions.add(new Edition(R.string.telangana, Globals.CODE_PRAJASAKTI_TS, R.drawable.ic_telangana, date));
        return editions;
    }

}
