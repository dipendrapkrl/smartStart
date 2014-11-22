smartStart
==========

This is an application that should be placed in the startup of the system.
 Configure this application by going to config.properties to suit your need.
  It will help you set up a list of programs that should be started depending on whether it is office time or home time (or neither [only applicable during holidays]).
Sample config.properties file
```
#allTimePrograms are always started regardless of the time the program is started.
allTimePrograms =skype,gedit

#officeTimePrograms are started if it is not holiday and lies between officeStartTime and officeEndTime.
officeTimePrograms =google-chrome

#homeTimePrograms are started if time lies between homeStartTime and homeEndTime. These programs are also started if stayHomeDuringHolidays is true and starting time is holiday
homeTimePrograms =nautilus

#office first hour
officeStartTime =10:00

#office last hour
officeEndTime =18:00


#home start hour
homeStartTime =18:00

#home end hour
homeEndTime =10:00


#holidays. These are the days during which you do not go to office(It doesn't mean that you will stay in home however.
# Set stayHomeDuringHolidays to true/false whichever preferred
holidays =SATURDAY,SUNDAY

#not staying home during holidays means neither in home nor in office.
#If it is true homeTimePrograms are started during holidays regardless of the time  but only allTimePrograms are started if it is false
stayHomeDuringHolidays=true
```

