package np.pro.dipendra.smartstart;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by bajra on 11/22/14.
 */
public class SmartStart {


    private static final String ALL_TIME_PROGRAMS = "allTimePrograms";
    private static final String HOME_TIME_PROGRAMS = "homeTimePrograms";
    private static final String OFFICE_TIME_PROGRAMS = "officeTimePrograms";
    private static final String OFFICE_START_TIME = "officeStartTime";
    private static final String OFFICE_END_TIME = "officeEndTime";
    private static final String HOME_START_TIME = "homeStartTime";
    private static final String HOME_END_TIME = "homeEndTime";
    private static final String HOLIDAYS = "holidays";
    private static final String STAY_HOME_DURING_HOLIDAY = "stayHomeDuringHolidays";


    private List<Integer> listHolidays;
    private LocalTime officeStartTime, officeEndTime, homeStartTime, homeEndTime;


    public void startPrograms() throws IOException {
        setHolidays();
        setTimes();
        setPrograms();


    }

    private void setTimes() {
        officeStartTime = DateTimeFormat.forPattern("HH:mm").parseLocalTime(Config.getConf().getString(OFFICE_START_TIME));
        officeEndTime = DateTimeFormat.forPattern("HH:mm").parseLocalTime(Config.getConf().getString(OFFICE_END_TIME));
        homeStartTime = DateTimeFormat.forPattern("HH:mm").parseLocalTime(Config.getConf().getString(HOME_START_TIME));
        homeEndTime = DateTimeFormat.forPattern("HH:mm").parseLocalTime(Config.getConf().getString(HOME_END_TIME));

        print("office start time " + officeStartTime);
        print("office end time " + officeEndTime);
        print("home start time " + homeStartTime);
        print("home end time " + homeEndTime);


    }

    private void print(String string) {
        System.out.println(string);

    }

    private void setPrograms() throws IOException {
        LocalDateTime now = new LocalDateTime();
        LocalTime timeNow = now.toLocalTime();
        boolean isTodayHoliday = false;
        for (Integer holiday : listHolidays) {
            if (holiday.intValue() == now.getDayOfWeek()) {
                isTodayHoliday = true;
                break;
            }

        }


        List<String> programs = (List<String>) (List<?>) Config.getConf().getList(ALL_TIME_PROGRAMS);
        print("list of all time programs=" + programs.toString());

        boolean stayHomeDuringHolidays = Config.getConf().getBoolean(STAY_HOME_DURING_HOLIDAY);
        if (isTodayHoliday) {
            print("today is holiday.");


            if (stayHomeDuringHolidays) {
                programs.addAll((List<String>) (List<?>) Config.getConf().getList(HOME_TIME_PROGRAMS));
            }

            print("list of all time programs " + (stayHomeDuringHolidays ? "plus home time programs=" : "") + programs.toString());


            executePrograms(programs);

        } else {
            if (officeEndTime.isBefore(officeStartTime)) {
                //lets swap their values so that we can calculate time in between.
                LocalTime tmpTime = officeEndTime;
                officeEndTime = officeStartTime;
                officeStartTime = tmpTime;
            }

            if (homeEndTime.isBefore(homeStartTime)) {
                //lets swap their values so that we can calculate time in between.
                LocalTime tmpTime = homeEndTime;
                homeEndTime = homeStartTime;
                homeStartTime = tmpTime;
            }


            print(timeNow.toString());
            if (timeNow.isAfter(officeStartTime) && timeNow.isBefore(officeEndTime)) {
                //Is between office time so execute office programs
                print("office time");

                programs.addAll((List<String>) (List<?>) Config.getConf().getList(OFFICE_TIME_PROGRAMS));
                executePrograms(programs);

            } else if (timeNow.isAfter(homeStartTime) && timeNow.isBefore(homeEndTime)) {
                //is between home time. But consider that
                print("home time");

                programs.addAll((List<String>) (List<?>) Config.getConf().getList(HOME_TIME_PROGRAMS));
                executePrograms(programs);
            } else {
                //only execute all time programs because it doesn't lie in between
                print("neither home nor office time");
                executePrograms(programs);

            }


        }


    }

    private void executePrograms(List<String> programs) throws IOException {

        //Remove duplicate programs
        HashSet hs = new HashSet();
        hs.addAll(programs);
        programs.clear();
        programs.addAll(hs);
        for (String string : programs) {
            if (string == null || string.trim().isEmpty()) continue;
            print(string);
            Runtime.getRuntime().exec(string);
        }


        //Execute Programs
//        Runtime.getRuntime().exec(programs.toArray(new String[programs.size()]));


    }

    private void setHolidays() {
        List<String> days = (List<String>) (List<?>) Config.getConf().getList(HOLIDAYS);

        listHolidays = new ArrayList<Integer>(days.size());
        for (Object day : days) {
            String d = (String) day;
            if (d.equalsIgnoreCase("monday")) {
                listHolidays.add(1);
                continue;
            }
            if (d.equalsIgnoreCase("tuesday")) {
                listHolidays.add(2);
                continue;
            }
            if (d.equalsIgnoreCase("wednesday")) {
                listHolidays.add(3);
                continue;
            }
            if (d.equalsIgnoreCase("thursday")) {
                listHolidays.add(4);
                continue;
            }
            if (d.equalsIgnoreCase("friday")) {
                listHolidays.add(5);
                continue;
            }
            if (d.equalsIgnoreCase("saturday")) {
                listHolidays.add(6);
                continue;
            }
            if (d.equalsIgnoreCase("sunday")) {
                listHolidays.add(7);
                continue;
            }

        }
        print("list of holidays =" + listHolidays);
    }


}
