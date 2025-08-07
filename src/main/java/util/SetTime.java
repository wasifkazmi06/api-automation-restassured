package util;

//import org.joda.time.*;
import org.joda.time.DateTimeUtils;
import org.testng.annotations.Test;

import java.time.*;

public class SetTime {

       //static Clock clock;
        public static void SetTimeinDaysMethod(long NoOfDays){

                /*Clock baseClock = Clock.systemDefaultZone();
                clock = Clock.offset(baseClock, Duration.ofDays(NoOfDays));
                System.out.println(clock.instant());*/
                DateTimeUtils.setCurrentMillisFixed(NoOfDays);
        }

        }
