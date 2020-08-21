package com.varun;

import com.varun.controller.HomeSceneController;
import com.varun.fxmlmodels.CourseTableElem;
import com.varun.fxmlmodels.InstallmentTableElem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Pair;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static Stack<Pair<String, Scene>> sceneStack = new Stack<>();
    static ObservableList<CourseTableElem> courses = null;
    static ObservableList<InstallmentTableElem> installmentTableElems = null;

    public static ObservableList<CourseTableElem> getTestCourseList() {
        if (courses == null) {
            courses = FXCollections.observableArrayList();
            courses.add(new CourseTableElem(1, "course 1", "desc 1", 2500.0));
            courses.add(new CourseTableElem(2, "course 2", "desc 2", 3500.0));
            courses.add(new CourseTableElem(3, "course 3", "desc 3", 1500.0));
            courses.add(new CourseTableElem(4, "course 4", "desc 4", 2000.0));
            courses.add(new CourseTableElem(5, "course 5", "desc 5", 1000.0));
            courses.add(new CourseTableElem(6, "course 6", "desc 6", 2300.0));
        }
        return courses;
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

//    public static void insertToSceneStack(Scene scene) {
//        sceneStack.push(scene);
//    }
//
//    public static Scene removeFromSceneStack() {
//        return sceneStack.pop();
//    }

    public static void backButtonFunctionality() {
        Pair pair = Utils.sceneStack.pop();
        App.setScene((Scene)pair.getValue());
    }

    public static void homeButtonFunctionality() throws IOException{
        Utils.sceneStack.clear();
        HomeSceneController.display();
    }
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
    public static final LocalDate getLocalDateFromString (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
    public static final Date getDateFromLocalDate (LocalDate localDate){
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        return date;
    }
    public static final java.sql.Date getSqlDateFromLocalDate (LocalDate localDate){
        java.util.Date utilDate = Utils.getDateFromLocalDate(localDate);
        java.sql.Date sd = new java.sql.Date(utilDate.getTime());
        return sd;
    }
    public static final LocalDate getLocalDateFromDate(Date date){
        LocalDate ld = new java.sql.Date(date.getTime()).toLocalDate();
        return ld;
    }

    public static boolean showsConfirmDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            return true;
        }else
            return false;
    }
    public static void showErrorDialog(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }


    public static boolean regexMatch(String input, String regex, boolean isCaseInSensitive){
        Pattern p;
        if(isCaseInSensitive)
            p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        else
            p = Pattern.compile(regex);

        Matcher m = p.matcher(input);
        return m.matches();
    }
}
